package com.doge.tip.exception.user;

import java.util.NoSuchElementException;

public class MissingElementException extends NoSuchElementException {

    public MissingElementException() {
        super();
    }

    public MissingElementException(String message) {
        super(message);
    }

}
