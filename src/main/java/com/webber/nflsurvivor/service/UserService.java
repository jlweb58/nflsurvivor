package com.webber.nflsurvivor.service;

import com.webber.nflsurvivor.controller.UserNotFoundException;
import com.webber.nflsurvivor.domain.User;
import com.webber.nflsurvivor.domain.UserAlreadyExistsException;

public interface UserService {

    /**
     * Find the user with the given email
     * @param email  The email (may not be null)
     * @return  The unique user with that email, or <code>null</code> if none is found.
     */
    User findByEmail(String email);

    /**
     * Create a new persistent user.
     * @param user  The user, may not be already persistent
     * @return  The persisted user
     */
    User create(User user) throws UserAlreadyExistsException;

    /**
     * Change the password of the given user
     *
     * @param user   The user whose password should be changed
     * @param oldPassword  The user's old password (will be verified)
     * @param newPassword  The user's new password (no special checks are made here as to its strength, may not be <code>null</code>)
     *
     * @throws SecurityException if the old password is not correct
     */
    void changePassword(User user, String oldPassword, String newPassword);

    User getCurrentUser() throws UserNotFoundException;

    User findUserById(Long id);
}
