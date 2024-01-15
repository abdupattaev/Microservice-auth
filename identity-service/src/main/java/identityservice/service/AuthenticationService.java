package identityservice.service;

import identityservice.domain.dto.JwtAuthenticationResponse;
import identityservice.domain.dto.SignInRequest;
import identityservice.domain.dto.SignUpRequest;
import identityservice.domain.model.Role;
import identityservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    /**
     * User registration
     *
     * @param request user data
     * @return token
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        Role assignedRole = Optional.ofNullable(request.getRole())
                .orElse(Role.USER);

        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(assignedRole)
                .build();

        userService.create(user);

        var jwtToken = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(request.getUsername(), jwtToken);
    }

    /**
     * User authentication
     *
     * @param request user data
     * @return token
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        if (user == null) {
            // If the user is not found, throw UsernameNotFoundException
            throw new UsernameNotFoundException("User not found with username: " + request.getUsername());
        }

        var jwtToken = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(request.getUsername(), jwtToken);
    }
}
