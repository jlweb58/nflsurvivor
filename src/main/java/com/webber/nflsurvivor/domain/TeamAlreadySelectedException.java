package com.webber.nflsurvivor.domain;

public class TeamAlreadySelectedException extends Exception {

    public TeamAlreadySelectedException(String message) {
        super(message);
    }
}
