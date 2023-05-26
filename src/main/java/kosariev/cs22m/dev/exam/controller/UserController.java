package kosariev.cs22m.dev.exam.controller;

import kosariev.cs22m.dev.exam.model.User;
import kosariev.cs22m.dev.exam.model.UserDTO;
import kosariev.cs22m.dev.exam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @PostMapping("/user")
    public ResponseEntity<User> saveUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.saveUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public boolean authUser(@RequestBody UserDTO userDTO) {
        if (userService.authUser(userDTO.getLogin(), userDTO.getPassword())) {
            return true;
        }
        return false;
    }
}
