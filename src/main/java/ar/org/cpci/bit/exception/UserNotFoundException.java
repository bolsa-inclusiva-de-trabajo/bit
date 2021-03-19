package ar.org.cpci.bit.exception;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String nombre) {
        super(nombre);
    }
}
