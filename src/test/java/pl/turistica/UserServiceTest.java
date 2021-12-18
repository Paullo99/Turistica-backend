package pl.turistica;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.turistica.dto.ChangePasswordDTO;
import pl.turistica.dto.UserForAdminDTO;
import pl.turistica.model.Role;
import pl.turistica.model.User;
import pl.turistica.repository.RoleRepository;
import pl.turistica.repository.UserRepository;
import pl.turistica.service.TokenService;
import pl.turistica.service.UserService;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenService tokenService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldChangeUserPassword() {
        //given
        String authorizationHeader = "token";
        String email = "example@com";
        String newEncodedPassword = "NewEnCoDeDpAsSwOrD";
        ChangePasswordDTO changePasswordDTO = new ChangePasswordDTO();
        User user = new User();

        //when
        when(tokenService.getEmailFromAuthorizationHeader(authorizationHeader)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(changePasswordDTO.getNewPassword())).thenReturn(newEncodedPassword);

        ResponseEntity<?> responseEntity = userService.changeUserPassword(authorizationHeader, changePasswordDTO);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).save(user);
    }

    @Test
    void shouldEditUser() {
        //given
        Role role = new Role();
        User user = new User();
        UserForAdminDTO userForAdminDTO = new UserForAdminDTO();
        //when
        when(userRepository.findUserById(userForAdminDTO.getId())).thenReturn(user);
        when(roleRepository.findByName(userForAdminDTO.getRole())).thenReturn(role);
        ResponseEntity<?> responseEntity = userService.editUser(userForAdminDTO);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).save(user);
    }

    @Test
    void shouldDeleteUser() {
        //given
        int userId = 1;
        User user = new User();
        //when
        when(userRepository.findUserById(userId)).thenReturn(user);
        ResponseEntity<?> responseEntity = userService.deleteUser(userId);

        //then
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userRepository).delete(user);
    }


}
