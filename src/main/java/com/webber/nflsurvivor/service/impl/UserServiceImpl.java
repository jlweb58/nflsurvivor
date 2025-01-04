package com.webber.nflsurvivor.service.impl;

import com.webber.nflsurvivor.controller.UserNotFoundException;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.repository.UserRepository;
import com.webber.nflsurvivor.service.UserService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(User user) {
        if (user.getId() != null) {
            throw new IllegalArgumentException("User with email " + user.getEmail() + " already exists");
        }
        String password = user.getPassword();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        password = encoder.encode(password);
        user.setPassword(password);
        return userRepository.save(user);    }

    @Override
    public void changePassword(User user, String oldPassword, String newPassword) {
        if (newPassword == null) {
            throw new SecurityException("New password may not be empty");
        }
        user = userRepository.findById((Long) user.getId()).get();
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(oldPassword, user.getPassword());
        if (!matches) {
            throw new SecurityException("Old password incorrect");
        }
        user.setPassword(encoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Load the current user from the security context, if none exists throw an exception.
     *
     * @return The current user from the security context
     * @throws UserNotFoundException if there is no current user
     */
    public User getCurrentUser() throws UserNotFoundException {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LOG.info("Current user is " + userDetails.getUsername());
        User user = userRepository.findByEmail(userDetails.getUsername());
        if (user == null) {
            throw new UserNotFoundException("No user found for username " + userDetails.getUsername());
        }
        return user;
    }

}
