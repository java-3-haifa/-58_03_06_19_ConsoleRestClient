package data.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.dto.ContactDto;
import data.store.StoreRepository;
import data.store.StoreRepositoryImpl;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

public class ContactRepositoryImplTest {

    ContactRepository cr;
    ContactDto contact0;

    @org.junit.Before
    public void setUp() throws Exception {
        StoreRepository sr = new StoreRepositoryImpl();
        sr.saveToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InRlc3Q0OTE2NDY0OTE0MzMzQG1haWwucnUxIn0.S-ItEWk9Rkn-Rx_opH7seyhpDWMZwlx_KbcTp2KpPY8");
        cr = new ContactRepositoryImpl(sr, new RestTemplate(), new ObjectMapper());
        contact0 = new ContactDto("testAdr0", "testDesk0", "test@email.com", 0, "Pupkin", "Vasya", "0123456");
    }

    @org.junit.Test
    public void deleteAllContacts() {
        cr.deleteAllContacts();
    }

    @org.junit.Test
    public void getAllContacts() {
        cr.getAllContacts().forEach(System.out::println);
    }

    @org.junit.Test
    public void addContact() {

//        System.out.println(cr.addContact(contact0));
    }

    @org.junit.Test
    public void updateContact() {
//        cr.updateContact(new ContactDto("testAdr0UPD1a", "testAdr0UPdD", "test@mail.test", 1790, "PupkinUPDa", "VasyaUPDd", "01234561"));

    }

    @org.junit.Test
    public void deleteContactById() {
        cr.deleteContactById(1790);
    }
}