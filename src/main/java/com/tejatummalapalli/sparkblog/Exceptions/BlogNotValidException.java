package com.tejatummalapalli.sparkblog.Exceptions;

/**
 * Created by Teja on 11/6/2016.
 */
public class BlogNotValidException extends Throwable {

    public BlogNotValidException() {
    }

    public BlogNotValidException(String message) {
        super(message);
    }
}
