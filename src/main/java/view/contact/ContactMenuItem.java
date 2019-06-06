package view.contact;

import business.contact.ContactService;
import business.exception.ServiceException;
import data.dto.ContactDto;
import view.console.InputOutput;
import view.console.Item;

public class ContactMenuItem implements Item {
    public enum Mode {
        ADD, UPDATE, DELETE, DELETEALL, GETALL
    }

    private final String name;
    private final Mode mode;
    private final ContactService service;

    public ContactMenuItem(ContactService service, String name, Mode mode) {
        this.name = name;
        this.mode = mode;
        this.service = service;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void perform(InputOutput io) {

        try {
            boolean res = switch (mode){
                case ADD -> {
                    String name = io.readString("Name: ");
                    String lastName = io.readString("Last name: ");
                    String email = io.readString("Email: ");
                    String phone = io.readString("Phone: ");
                    String address = io.readString("Address: ");
                    String descriprion = io.readString("Description: ");
                    break service.add(new ContactDto(address, descriprion, email, 0, lastName, name, phone));
                }
                case UPDATE -> {
                    Integer id = io.readInt("Id", 0, Integer.MAX_VALUE);
                    String name = io.readString("Name: ");
                    String lastName = io.readString("Last name: ");
                    String email = io.readString("Email: ");
                    String phone = io.readString("Phone: ");
                    String address = io.readString("Address: ");
                    String descriprion = io.readString("Description: ");
                    break service.update(new ContactDto(address, descriprion, email, id, lastName, name, phone));
                }
                case DELETE -> {
                    Integer id = io.readInt("Type Id", 0, Integer.MAX_VALUE);
                    break service.deleteById(id);
                }
                case DELETEALL -> service.deleteAll();
                case GETALL -> {
                    for (ContactDto contact : service.getAll()) {
                        io.writeln(contact);
                    }
                    break true;
                }
            };

            if (res) {
                io.writeln("Operation success");
            }

        }catch (ServiceException ex) {
            Throwable cause = ex.getCause();
            if (cause != null) {
                io.writeln(cause.getMessage());
            } else {
                io.writeln(ex.getMessage());
            }
        }
    }




}
