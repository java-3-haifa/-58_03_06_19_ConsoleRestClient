package data.contact;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.dto.ContactDto;
import data.dto.ContactResponseDto;
import data.dto.ContactsListDto;
import data.dto.ErrorResponseDto;
import data.exception.AuthorizationException;
import data.exception.RepositoryException;
import data.exception.WrongContactException;
import data.store.StoreRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.ContentHandler;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    }

    @Override
    public boolean deleteAllContacts() throws AuthorizationException {
        try {
            try {
                RequestEntity<?> requestDel = RequestEntity.delete(URI.create(BASE_URL + DELALL_PATH))
                        .header("Authorization", store.getCurrentToken())
                        .build();
                ResponseEntity<String> responseEntity = client.exchange(requestDel, String.class);
                return responseEntity != null;
            } catch (RestClientResponseException ex) {
                String error = ex.getResponseBodyAsString();
                ErrorResponseDto errorResponseDto = mapper.readValue(error, ErrorResponseDto.class);
                throw new AuthorizationException(errorResponseDto.getMessage(), ex);
            }
        } catch (IOException ex) {
            throw new RepositoryException("Parsing error", ex);
        }
    }

    @Override
    public Iterable<ContactDto> getAllContacts() throws AuthorizationException {
        try {
            try {
                RequestEntity<?> requestGetAll = RequestEntity.get(URI.create(BASE_URL + CONTACT_PATH))
                        .header("Authorization", store.getCurrentToken())
                        .header("content-type", "application/json;charset=UTF-8")
                        .build();
                ResponseEntity<ContactsListDto> responseEntity = client.exchange(requestGetAll, ContactsListDto.class);
                if (responseEntity != null) {
                    return responseEntity.getBody().getContacts().stream().collect(Collectors.toUnmodifiableList());
                }
            } catch (RestClientResponseException ex) {
                String error = ex.getResponseBodyAsString();
                ErrorResponseDto errorResponseDto = mapper.readValue(error, ErrorResponseDto.class);
                throw new AuthorizationException(errorResponseDto.getMessage(), ex);
            }
        }catch (IOException ex) {
            throw new RepositoryException("Parsing error", ex);
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean addContact(ContactDto contact) throws AuthorizationException, WrongContactException {
        try {
            try {
                RequestEntity<?> requestEntity = RequestEntity.post(URI.create(BASE_URL + CONTACT_PATH))
                        .header("Authorization", store.getCurrentToken())
                        .body(contact, ContactDto.class);
                ResponseEntity<ContactDto> responseEntity = client.exchange(requestEntity, ContactDto.class);
                return responseEntity != null;
            } catch (RestClientResponseException ex) {
                ErrorResponseDto errorResponseDto = mapper.readValue(ex.getResponseBodyAsString(), ErrorResponseDto.class);
                if (ex.getRawStatusCode() == 401) {
                    throw new AuthorizationException(errorResponseDto.getMessage(), ex);
                }
                throw new WrongContactException(errorResponseDto.getMessage(), ex);

            }
        } catch (IOException ex) {
            throw new RepositoryException("Parsing error", ex);
        }
    }

    @Override
    public boolean updateContact(ContactDto contactDto) throws AuthorizationException, WrongContactException {
        try {
            try {
                RequestEntity<?> requestEntity = RequestEntity.put(URI.create(BASE_URL + CONTACT_PATH))
                        .header("Authorization", store.getCurrentToken())
                        .body(contactDto, ContactDto.class);
                ResponseEntity<ContactResponseDto> responseEntity = client.exchange(requestEntity, ContactResponseDto.class);
                return true;
            } catch (RestClientResponseException ex) {
                ErrorResponseDto errorResponseDto = mapper.readValue(ex.getResponseBodyAsString(), ErrorResponseDto.class);
                if (ex.getRawStatusCode() == 401) {
                    throw new AuthorizationException(errorResponseDto.getMessage(), ex);
                }
                throw new WrongContactException(errorResponseDto.getMessage(), ex);
            }

        } catch (IOException ex) {
            throw new RepositoryException("Parsing error", ex);
        }
    }

    @Override
    public boolean deleteContactById(int id) throws AuthorizationException, WrongContactException {
        try {
            try {
                RequestEntity<?> requestEntity = RequestEntity.delete(URI.create(BASE_URL + CONTACT_PATH + "/" + id))
                        .header("Authorization", store.getCurrentToken())
                        .build();
                ResponseEntity<String> responseEntity = client.exchange(requestEntity, String.class);
                return !responseEntity.getBody().isEmpty();

            } catch (RestClientResponseException ex) {
                ErrorResponseDto errorResponseDto = mapper.readValue(ex.getResponseBodyAsString(), ErrorResponseDto.class);
                if (errorResponseDto.getCode() == 401) {
                    throw new AuthorizationException(errorResponseDto.getMessage(), ex);
                }
                throw new WrongContactException(errorResponseDto.getMessage(), ex);
            }

        } catch (IOException ex) {
            throw new RepositoryException("Parsing error", ex);
        }
    }
}
