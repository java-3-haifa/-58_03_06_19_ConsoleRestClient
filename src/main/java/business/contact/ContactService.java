package business.contact;

import business.exception.ServiceException;
import data.dto.ContactDto;

public interface ContactService {
    boolean deleteAll() throws ServiceException;
    Iterable<ContactDto> getAll() throws ServiceException;
    boolean add(ContactDto contact) throws ServiceException;
    boolean update(ContactDto contact) throws ServiceException;
    boolean deleteById(int id) throws ServiceException;
}
