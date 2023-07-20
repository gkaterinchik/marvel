package com.stm.marvel.Exceptions;

public class SuchElementAlreadyExist extends RuntimeException {


    public SuchElementAlreadyExist(String message) {
        super("Such element already exist");

    }
}
