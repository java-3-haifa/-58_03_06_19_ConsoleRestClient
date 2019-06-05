import business.auth.AuthService;

import business.contact.ContactService;
import view.auth.AuthMenuItem;
import view.console.InputOutput;
import view.console.Item;
import view.console.Menu;
import view.contact.ContactMenuItem;

public class App {
    public static void main(String[] args) {
        Menu contactMenu = new Menu("Contacts",
                new ContactMenuItem(Context.get(ContactService.class), "Add contact", ContactMenuItem.Mode.ADD),
                new ContactMenuItem(Context.get(ContactService.class), "Update contact", ContactMenuItem.Mode.UPDATE),
                new ContactMenuItem(Context.get(ContactService.class), "Get all contacts", ContactMenuItem.Mode.GETALL),
                new ContactMenuItem(Context.get(ContactService.class), "Delete contact", ContactMenuItem.Mode.DELETE),
                new ContactMenuItem(Context.get(ContactService.class), "Delete all contacts", ContactMenuItem.Mode.DELETEALL),
                Item.exit());

        Menu main = new Menu("Authorization",
                new AuthMenuItem(Context.get(AuthService.class),"Registration", AuthMenuItem.Mode.REGISTRATION,
                        contactMenu),
                new AuthMenuItem(Context.get(AuthService.class),"Login", AuthMenuItem.Mode.LOGIN,
                        contactMenu),
                Item.exit()
        );
        main.perform(Context.get(InputOutput.class));
    }
}
