package data.store;

import data.exception.StoreException;

public class StoreRepositoryImpl implements StoreRepository {
    private String token;
    @Override
    public void saveToken(String token) throws StoreException {
        this.token = token;
    }

    @Override
    public String getCurrentToken() throws StoreException {
        return token;
    }

    @Override
    public void clearToken() throws StoreException {
        token = null;
    }
}
