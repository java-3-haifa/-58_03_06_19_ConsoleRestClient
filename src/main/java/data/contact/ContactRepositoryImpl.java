package data.contact;

import com.fasterxml.jackson.databind.ObjectMapper;
import data.dto.ContactDto;
import data.dto.ContactResponseDto;
import data.dto.ContactsListDto;
import data.dto.ErrorResponseDto;
import data.exception.AuthorizationException;
import data.exception.RepositoryException;
import data.exception.WrongContactException;
import data.store.StoreRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.stream.Collectors;

import static data.ApiConfig.*;

public class ContactRepositoryImpl implements ContactRepository {
    private ObjectMapper mapper;
    private StoreRepository store;
    private RestTemplate client;

    public ContactRepositoryImpl(StoreRepository store, RestTemplate client, ObjectMapper mapper) {
        this.mapper = mapper;
        this.store = store;
        this.client = client;
        client.setErrorHandler(new ContactRepoErrorHandler());

    }

    @Override
    public boolean deleteAllContacts() throws AuthorizationException {
        RequestEntity<?> requestDel = RequestEntity.delete(URI.create(BASE_URL + DELALL_PATH))
                .header("Authorization", store.getCurrentToken())
                .build();
        ResponseEntity<String> responseEntity = client.exchange(requestDel, String.class);
        return responseEntity != null;
    }

    @Override
    public Iterable<ContactDto> getAllContacts() throws AuthorizationException {
        RequestEntity<?> requestGetAll = RequestEntity.get(URI.create(BASE_URL + CONTACT_PATH))
                .header("Authorization", store.getCurrentToken())
                .header("content-type", "application/json;charset=UTF-8")
                .build();
        ResponseEntity<ContactsListDto> responseEntity = client.exchange(requestGetAll, ContactsListDto.class);
        if (responseEntity != null) {
            return responseEntity.getBody().getContacts().stream().collect(Collectors.toUnmodifiableList());
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean addContact(ContactDto contact) throws AuthorizationException, WrongContactException {
        RequestEntity<?> requestEntity = RequestEntity.post(URI.create(BASE_URL + CONTACT_PATH))
                .header("Authorization", store.getCurrentToken())
                .body(contact, ContactDto.class);
        ResponseEntity<ContactDto> responseEntity = client.exchange(requestEntity, ContactDto.class);
        return responseEntity != null;
    }

    @Override
    public boolean updateContact(ContactDto contactDto) throws AuthorizationException, WrongContactException {
        RequestEntity<?> requestEntity = RequestEntity.put(URI.create(BASE_URL + CONTACT_PATH))
                .header("Authorization", store.getCurrentToken())
                .body(contactDto, ContactDto.class);
        ResponseEntity<ContactResponseDto> responseEntity = client.exchange(requestEntity, ContactResponseDto.class);
        return true;
    }

    @Override
    public boolean deleteContactById(int id) throws AuthorizationException, WrongContactException {

        RequestEntity<?> requestEntity = RequestEntity.delete(URI.create(BASE_URL + CONTACT_PATH + "/" + id))
                .header("Authorization", store.getCurrentToken())
                .build();
        ResponseEntity<String> responseEntity = client.exchange(requestEntity, String.class);
        return !responseEntity.getBody().isEmpty();
    }

    private class ContactRepoErrorHandler implements ResponseErrorHandler {
        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
        }

        @Override
        public void handleError(ClientHttpResponse response) {
            try {
                ErrorResponseDto errorResponseDto = mapper.readValue(response.getBody(), ErrorResponseDto.class);
                if (errorResponseDto.getCode() == HttpStatus.BAD_REQUEST.value()) {
                    throw new WrongContactException("Wrong contact format! " + errorResponseDto.getMessage());
                } else if (errorResponseDto.getCode() == HttpStatus.UNAUTHORIZED.value()) {
                    throw new AuthorizationException("Wrong authorization! " + errorResponseDto.getMessage());
                } else if (errorResponseDto.getCode() == HttpStatus.NOT_FOUND.value()) {
                    throw new WrongContactException("Contact not found! " + errorResponseDto.getMessage());
                } else if (errorResponseDto.getCode() == HttpStatus.CONFLICT.value()) {
                    throw new WrongContactException("Duplicate fields! " + errorResponseDto.getMessage());
                } else {
                    throw new RepositoryException(errorResponseDto.getMessage());
                }
            } catch (IOException ex) {
                throw new RepositoryException("JSON parsing error! ", ex);
            }
        }
    }
}
