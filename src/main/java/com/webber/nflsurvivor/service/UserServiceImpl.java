package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
