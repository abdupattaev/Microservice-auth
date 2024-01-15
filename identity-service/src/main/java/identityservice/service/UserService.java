package identityservice.service;

import identityservice.domain.model.Role;
import identityservice.domain.model.User;
import identityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * Save a user
     *
     * @return the saved user
     */
    public User save(User user) {
        return repository.save(user);
    }


    /**
     * Create a user
     *
     * @return the created user
     */
    public User create(User user) {
        if (repository.existsByUsername(user.getUsername())) {
            // Replace with your own exceptions
            throw new RuntimeException("A user with this username already exists");
        }

        if (repository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("A user with this email already exists");
        }

        return save(user);
    }

    /**
     * Get a user by username
     *
     * @return the user
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    }

    /**
     * Get a user by username
     * <p>
     * Needed for Spring Security
     *
     * @return the user
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Get the current user
     *
     * @return the current user
     */
    public User getCurrentUser() {
        // Get the username from the Spring Security context
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Grant administrator rights to the current user
     * <p>
     * Deprecated for demonstration purposes
     */
    public void setAdmin() {
        var user = getCurrentUser();
        user.setRole(Role.ADMIN);
        save(user);
    }
}
