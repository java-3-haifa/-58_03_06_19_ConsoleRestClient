import business.auth.AuthService;

import view.auth.AuthMenuItem;
import view.console.InputOutput;
import view.console.Item;
import view.console.Menu;

public class App {
    public static void main(String[] args) {
        Menu main = new Menu("Authorization",
                new AuthMenuItem(Context.get(AuthService.class),"Registration", AuthMenuItem.Mode.REGISTRATION,
                        new Menu("Fake Menu",
                                Item.of("Add contact",io->{}),
                                Item.exit())),
                new AuthMenuItem(Context.get(AuthService.class),"Login", AuthMenuItem.Mode.LOGIN,
                        new Menu("Fake Menu",
                                Item.of("Add contact",io->{}),
                                Item.exit())),
                Item.exit()
        );
        main.perform(Context.get(InputOutput.class));
    }
}
