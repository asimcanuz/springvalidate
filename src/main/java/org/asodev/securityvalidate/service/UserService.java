package org.asodev.securityvalidate.service;

import jakarta.persistence.EntityNotFoundException;
import org.asodev.securityvalidate.models.User;
import org.asodev.securityvalidate.payload.request.SignupRequest;
import org.asodev.securityvalidate.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public User createUser(SignupRequest request){
        User newUser = User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(bCryptPasswordEncoder.encode(request.password()))
                .username(request.username())
                .authorities(request.role())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();
        return userRepository.save(newUser);
    }

    public String getNameWithEmail(String usernameOrEmail){
        if (isEmail(usernameOrEmail)){
            if (!usernameOrEmail.endsWith(".com")){
                throw new IllegalArgumentException("Invalid Email address:"+usernameOrEmail);
            }
            // get username by email
            return userRepository.findUsernameByEmail(usernameOrEmail);
        }

        // username
        return usernameOrEmail;
    }

    private boolean isEmail(String str){
        return str.contains("@");
    }
}
