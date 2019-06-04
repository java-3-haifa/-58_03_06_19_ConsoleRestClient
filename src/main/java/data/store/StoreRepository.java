package data.store;

import data.exception.StoreException;

public interface StoreRepository {
    void saveToken(String token) throws StoreException;
    String getCurrentToken() throws StoreException;
    void clearToken() throws StoreException;
}
