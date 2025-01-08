package com.webber.nflsurvivor.controller;

import com.webber.nflsurvivor.domain.GameWillStartSoonException;
import com.webber.nflsurvivor.domain.TeamAlreadySelectedException;
import com.webber.nflsurvivor.domain.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No valid user found");
    }

    @ExceptionHandler(TeamAlreadySelectedException.class)
    public ResponseEntity<String> handleTeamAlreadySelected(TeamAlreadySelectedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body("Team was already selected");
    }

    @ExceptionHandler(GameWillStartSoonException.class)
    public ResponseEntity<String> handleGameWillStartSoon(GameWillStartSoonException ex) {
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Game already started or will start soon");
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExists(UserAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("User already exists");
    }
}
