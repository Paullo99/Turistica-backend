package pl.turistica.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.turistica.dto.ChangePasswordDTO;
import pl.turistica.dto.UserForAdminDTO;
import pl.turistica.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://turistica.herokuapp.com"})
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/change-password")
    public ResponseEntity<?> changeUserPassword(@RequestHeader("Authorization") String authorizationHeader,
                                                @RequestBody ChangePasswordDTO changePasswordDTO) {
        return userService.changeUserPassword(authorizationHeader, changePasswordDTO);
    }

    @PutMapping("/edit-user")
    public ResponseEntity<?> editUser(@RequestBody UserForAdminDTO user) {
        return userService.editUser(user);
    }

    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        return userService.deleteUser(userId);
    }

    @GetMapping("/user-list")
    public List<UserForAdminDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addNewGuideOrAdmin(@RequestBody UserForAdminDTO user) {
        return userService.addNewGuideOrAdmin(user);
    }
}
