package business.auth;

import business.exception.EmailFormatException;
import business.exception.PasswordFormatException;
import business.exception.ServiceException;

public interface AuthService {
    boolean registration(String email, String password) throws ServiceException;
    boolean login(String email, String password) throws ServiceException;
}
