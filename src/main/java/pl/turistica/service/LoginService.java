package pl.turistica.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.turistica.model.User;
import pl.turistica.repository.UserRepository;

import java.util.HashMap;

@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public HashMap<String, String> login(User user){
        HashMap<String, String> responseJson = new HashMap<>();
        User u = userRepository.findByEmail(user.getEmail());
        if(u != null){
            responseJson.put("isValid", String.valueOf(passwordEncoder.matches(user.getPassword(), u.getPassword())));
            responseJson.put("role", u.getRole().getName());
            return responseJson;
        }
        responseJson.put("isValid", "false");
        return responseJson;
    }
}
