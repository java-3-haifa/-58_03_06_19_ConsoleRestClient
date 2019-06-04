import business.auth.AuthService;
import business.auth.AuthServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.auth.AuthRepository;
import data.auth.AuthRepositoryImpl;
import data.store.StoreRepository;
import data.store.StoreRepositoryImpl;
import org.springframework.web.client.RestTemplate;
import view.console.ConsoleInputOutput;
import view.console.InputOutput;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Context {
    private static final Map<Class,? super Object> map;

    static {
        InputOutput io = new ConsoleInputOutput();
        StoreRepository storeRepository = new StoreRepositoryImpl();
        AuthRepository authRepository = new AuthRepositoryImpl(storeRepository,new RestTemplate(),new ObjectMapper());
        AuthService authService = new AuthServiceImpl(authRepository);
        map = new HashMap<>();
        map.put(StoreRepository.class,storeRepository);
        map.put(AuthService.class,authService);
        map.put(AuthRepository.class,authRepository);
        map.put(InputOutput.class,io);
    }

    private Context(){
        throw new UnsupportedOperationException();
    }

    public static  <T extends Object> T get(Class<T> clazz){
        Objects.requireNonNull(clazz);
        return (T) map.get(clazz);
    }
}
