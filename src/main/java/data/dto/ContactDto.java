package data.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class ContactDto {
    String address;
    String description;
    String email;
    Integer id;
    String lastName;
    String name;
    String phone;
}
