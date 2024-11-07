package recipes.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import recipes.persistence.UserRepository;
import recipes.presentation.UserDTO;

import java.util.Optional;

@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = repository;
    }

    public boolean registerUser(UserDTO userDTO) {
        Optional<User> existingUser = userRepository.findUserByUsername(userDTO.getEmail());

        if (existingUser.isEmpty()) {
            User user = new User();
            user.setUsername(userDTO.getEmail());
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            user.setAuthority("ROLE_USER");
            userRepository.save(user);
            return true;
        }
        return false;
    }


}
