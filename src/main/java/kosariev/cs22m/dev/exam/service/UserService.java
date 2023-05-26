package kosariev.cs22m.dev.exam.service;

import jakarta.transaction.Transactional;
import kosariev.cs22m.dev.exam.model.User;
import kosariev.cs22m.dev.exam.model.UserDTO;
import kosariev.cs22m.dev.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(UserDTO userDTO) {
        if (checkLogin(userDTO.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Login already exists.");
        }
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setPassword(userDTO.getPassword());
        userRepository.saveAndFlush(user);
        return user;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public boolean authUser(String login, String password) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Login not found.");
        }
        if (!optionalUser.get().getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Incorrect password.");
        }
        return true;
    }

    public boolean checkLogin(String login) {
        Optional<User> optionalUser = userRepository.findByLogin(login);
        if (optionalUser.isPresent()) {
            return true;
        }
        return false;
    }
}
