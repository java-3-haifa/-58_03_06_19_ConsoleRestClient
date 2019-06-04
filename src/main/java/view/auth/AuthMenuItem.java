package view.auth;

import business.auth.AuthService;
import business.exception.ServiceException;
import view.console.InputOutput;
import view.console.Item;

public class AuthMenuItem implements Item {
    public static enum Mode {
        REGISTRATION, LOGIN
    }

    private final String name;
    private final Item subMenu;
    private final Mode mode;
    private final AuthService service;

    public AuthMenuItem(AuthService service, String name, Mode mode, Item subMenu) {
        this.subMenu = subMenu;
        this.service = service;
        this.name = name;
        this.mode = mode;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform(InputOutput io) {
        String email = io.readString("Email: ");
        String password = io.readString("Password: ");
        try {
            boolean res = switch (mode) {
                case REGISTRATION -> service.registration(email, password);
                case LOGIN -> service.login(email, password);
            };

            if (res) {
                io.writeln("Authorization success!");
                subMenu.perform(io);
            }
        } catch (ServiceException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                io.writeln(cause.getMessage());
            } else {
                io.writeln(ex.getMessage());
            }
        }
    }
}
