package business.auth;

import business.exception.EmailFormatException;
import business.exception.PasswordFormatException;
import business.exception.ServiceException;
import data.auth.AuthRepository;
import data.exception.RepositoryException;

public class AuthServiceImpl implements AuthService{
    AuthRepository repository;

    public AuthServiceImpl(AuthRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean registration(String email, String password) throws ServiceException {
        if(!emailValidation(email)){
            throw new EmailFormatException();
        }
        if(!passwordValidation(password)){
            throw new PasswordFormatException();
        }
        try {
            return repository.registration(email,password);
        }catch (RepositoryException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public boolean login(String email, String password) throws ServiceException {
        if(!emailValidation(email)){
            throw new EmailFormatException();
        }
        if(!passwordValidation(password)){
            throw new PasswordFormatException();
        }
        try {
            return repository.login(email,password);
        }catch (RepositoryException e){
            throw new ServiceException(e.getMessage(),e);
        }
    }

    private boolean emailValidation(String email){
        return email != null && email.contains("@");
    }

    private boolean passwordValidation(String password){
        return password != null && password.length() >= 8;
    }
}
