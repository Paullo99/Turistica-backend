package pl.turistica;

import freemarker.template.TemplateException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.turistica.model.Role;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;
import pl.turistica.service.EmailService;
import pl.turistica.service.RegisterService;

import javax.mail.MessagingException;
import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegisterServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private RegisterService registerService;

    @Test
    void shouldRegisterNewUser() throws MessagingException, TemplateException, IOException {
        //given
        final int roleUserId = 1;
        final String encodedPassword = "encodedPassword";
        final String roleName = "ROLE_USER";
        Role role = new Role();
        role.setName(roleName);
        User userToRegister = new User();

        when(roleRepository.getById(roleUserId)).thenReturn(role);
        when(passwordEncoder.encode(userToRegister.getPassword())).thenReturn(encodedPassword);
        when(userRepository.existsUserByEmail(userToRegister.getEmail())).thenReturn(false);

        //when
        ResponseEntity<?> responseEntity = registerService.registerNewUser(userToRegister);

        //then
        Assertions.assertThat(userToRegister.getRole()).isEqualTo(role);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).save(userToRegister);
        verify(emailService).sendRegistrationMessage(userToRegister.getEmail());

    }

    @Test
    void shouldNotRegisterNewUser() throws MessagingException, TemplateException, IOException {
        //given
        String encodedPassword = "encodedPassword";
        Role role = new Role();
        role.setName("ROLE_USER");
        User userToRegister = new User();

        //when
        when(roleRepository.getById(1)).thenReturn(role);
        when(passwordEncoder.encode(userToRegister.getPassword())).thenReturn(encodedPassword);
        when(userRepository.existsUserByEmail(userToRegister.getEmail())).thenReturn(true);

        ResponseEntity<?> responseEntity = registerService.registerNewUser(userToRegister);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        verify(userRepository, never()).save(userToRegister);
        verify(emailService, never()).sendRegistrationMessage(userToRegister.getEmail());
    }

}
