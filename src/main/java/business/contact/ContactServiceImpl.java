package business.contact;

import business.exception.ServiceException;
import data.contact.ContactRepository;
import data.dto.ContactDto;
import data.exception.RepositoryException;

public class ContactServiceImpl implements ContactService {
    ContactRepository repository;

    public ContactServiceImpl(ContactRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean deleteAll() throws ServiceException {
        try {
            return repository.deleteAllContacts();
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    @Override
    public Iterable<ContactDto> getAll() throws ServiceException {
        try {
            return repository.getAllContacts();
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    @Override
    public boolean add(ContactDto contact) throws ServiceException {
        if (contact == null) {
            throw new ServiceException("Contact is null");
        }
        try {
            return repository.addContact(contact);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }

    @Override
    public boolean update(ContactDto contact) throws ServiceException {
        if (contact == null) {
            throw new ServiceException("Contact is null");
        }
        try {
            return repository.updateContact(contact);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }
    }

    @Override
    public boolean deleteById(int id) throws ServiceException {
        if (id < 0) {
            throw new ServiceException("Id must be positive!");
        }
        try {
            return repository.deleteContactById(id);
        } catch (RepositoryException ex) {
            throw new ServiceException(ex.getMessage(), ex);
        }

    }
}
