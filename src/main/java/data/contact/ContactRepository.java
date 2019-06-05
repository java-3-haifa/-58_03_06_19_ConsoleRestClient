package data.contact;


import data.dto.ContactDto;
import data.exception.AuthorizationException;
import data.exception.WrongContactException;

public interface ContactRepository {

    boolean deleteAllContacts() throws AuthorizationException;
    Iterable<ContactDto> getAllContacts() throws AuthorizationException;
    boolean addContact(ContactDto contact) throws AuthorizationException, WrongContactException;
    boolean updateContact(ContactDto contactDto) throws AuthorizationException, WrongContactException;
    boolean deleteContactById(int id) throws AuthorizationException, WrongContactException;
}
