package com.webber.nflsurvivor.user;

import com.webber.nflsurvivor.pool.Pool;
import com.webber.nflsurvivor.pool.PoolService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Transactional
@RequestMapping("/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    private final PoolService poolService;

    @Autowired
    public UserController(UserService userService, PoolService poolService) {
        this.userService = userService;
        this.poolService = poolService;
    }

    @PostMapping(path = "/password", produces = "application/json")
    public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) throws UserNotFoundException {
        User user = userService.getCurrentUser();
        try {
            userService.changePassword(user, passwordChangeRequest.oldPassword(), passwordChangeRequest.newPassword());
            LOG.info("Changed password for user " + user.getEmail());
            return ResponseEntity.ok(new ChangePasswordResponse(false, "Password change successful"));
        } catch (SecurityException e) {
            LOG.warn("Couldn't change password", e);
            return ResponseEntity.status(403).body(new ChangePasswordResponse(true, "Old password incorrect"));
        }
    }

    @PostMapping(path="/register", produces = "application/json")
    public ResponseEntity<RegisterUserResponse> registerUser(@RequestBody RegisterUserRequest registerUserRequest) throws UserAlreadyExistsException {

        User newRegisteredUser = userService.create(new User(registerUserRequest.name(), registerUserRequest.username(), registerUserRequest.password()));
        Pool poolForNewUser = poolService.find(registerUserRequest.poolId());
        poolForNewUser.addPoolMember(newRegisteredUser);
        return ResponseEntity.ok(new RegisterUserResponse(false, "User registered successfully"));
    }

}
