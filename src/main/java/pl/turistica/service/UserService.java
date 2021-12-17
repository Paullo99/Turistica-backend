package pl.turistica.service;

import freemarker.template.TemplateException;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.turistica.dto.ChangePasswordDTO;
import pl.turistica.dto.UserForAdminDTO;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity<?> changeUserPassword(String authorizationHeader, ChangePasswordDTO changePasswordDTO) {

        String email = tokenService.getEmailFromAuthorizationHeader(authorizationHeader);
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> editUser(UserForAdminDTO user) {

        User userToUpdate = userRepository.findUserById(user.getId());
        if (userToUpdate != null) {
            userToUpdate.setEmail(user.getEmail());
            userToUpdate.setRole(roleRepository.findByName(user.getRole()));
            userToUpdate.setName(user.getName());
            userToUpdate.setLastName(user.getLastName());
            userToUpdate.setPhoneNumber(user.getPhoneNumber());
            userRepository.save(userToUpdate);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    public ResponseEntity<?> deleteUser(int userId) {

        User user = userRepository.findUserById(userId);
        if (user != null)
            userRepository.delete(user);
        else
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        return new ResponseEntity<>(HttpStatus.OK);

    }

    public List<UserForAdminDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserForAdminDTO> usersToSend = new ArrayList<>();
        for (User u : users)
            if (u.getRole().getId() == 2 || u.getRole().getId() == 3)
                usersToSend.add(new UserForAdminDTO(u));
        return usersToSend;
    }

    public ResponseEntity<?> addNewGuideOrAdmin(UserForAdminDTO user) {
        if (userRepository.existsUserByEmail(user.getEmail()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        String generatedPassword = RandomString.make(12);
        String encodedPassword = DigestUtils.sha256Hex(generatedPassword);
        User userToInsert = new User(user.getEmail(), passwordEncoder.encode(encodedPassword),
                user.getName(), user.getLastName(), user.getPhoneNumber(), roleRepository.findByName(user.getRole()));
        userRepository.save(userToInsert);
        try {
            HashMap<String, Object> details = new HashMap<>();
            if (userToInsert.getRole().getId() == 2)
                details.put("role", "przewodnika");
            else
                details.put("role", "administratora");
            details.put("firstName", user.getName());
            details.put("lastName", user.getLastName());
            details.put("password", generatedPassword);
            emailService.sendMessage(
                    user.getEmail(),
                    "Utworzenie konta przez administratora",
                    details,
                    "adduser-email-template.ftl");
        } catch (MessagingException | IOException | TemplateException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
