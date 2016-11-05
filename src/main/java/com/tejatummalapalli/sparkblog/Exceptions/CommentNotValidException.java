package com.tejatummalapalli.sparkblog.Exceptions;

public class CommentNotValidException extends Exception {

    public CommentNotValidException() {
    }

    public CommentNotValidException(String message) {
        super(message);
    }

}