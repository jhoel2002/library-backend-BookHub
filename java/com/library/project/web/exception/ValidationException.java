package com.library.project.web.exception;

public class ValidationException {
    public static boolean esString(String cadena) {
        return cadena.matches("[a-zA-Z]+");
    }
}
