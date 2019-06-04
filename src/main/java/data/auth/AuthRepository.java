package data.auth;

import data.exception.LoginException;
import data.exception.RegistrationException;

public interface AuthRepository {
    boolean login(String email, String password) throws LoginException;
    boolean registration(String email, String password) throws RegistrationException;

}
