package com.Inventory.management.Inventory.Management.System.controller;
import com.Inventory.management.Inventory.Management.System.model.ERole;
import com.Inventory.management.Inventory.Management.System.model.Role;
import com.Inventory.management.Inventory.Management.System.model.User;
import com.Inventory.management.Inventory.Management.System.repository.RoleRepo;
import com.Inventory.management.Inventory.Management.System.repository.UserRepo;
import com.Inventory.management.Inventory.Management.System.security.jwt.JwtUtils;
import com.Inventory.management.Inventory.Management.System.security.services.UserDetailsImpl;
import com.Inventory.management.Inventory.Management.System.payload.request.LoginRequest;
import com.Inventory.management.Inventory.Management.System.payload.request.SignupRequest;
import com.Inventory.management.Inventory.Management.System.payload.response.JwtResponse;
import com.Inventory.management.Inventory.Management.System.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepo userRepository;

    @Autowired
    RoleRepo roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Username is already taken!"));
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already in use!"));
            }

            // Check for required fields for regular users
            if ( signUpRequest.getRoles() == null ||signUpRequest.getRoles().contains("user")) {
                if (signUpRequest.getFirstName() == null || signUpRequest.getLastName() == null || signUpRequest.getTelephoneNumber() == null) {
                    return ResponseEntity
                            .badRequest()
                            .body(new MessageResponse("Error: First name, last name, and telephone number are required for regular users!"));
                }
            }

            User user = new User(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()));

            Set<String> strRoles = signUpRequest.getRoles();
            Set<Role> roles = new HashSet<>();

            if (strRoles == null) {
                Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role) {
                        case "admin":
                            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(adminRole);
                            break;
                        default:
                            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                            roles.add(userRole);
                    }
                });
            }

            user.setRoles(roles);

            // Additional user details only for regular users
            if (roles.stream().anyMatch(role -> role.getName().equals(ERole.ROLE_USER))) {
                user.setFirstName(signUpRequest.getFirstName());
                user.setLastName(signUpRequest.getLastName());
                user.setTelephoneNumber(signUpRequest.getTelephoneNumber());
            }

            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error occurred while registering user: " + e.getMessage()));
        }
    }
    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_USER'), hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getAdminProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user/{username}/profile")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> getUserProfileByUsername(@PathVariable String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
}