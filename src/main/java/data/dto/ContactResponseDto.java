package data.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
/* Example dto
 "id": 1790,
  "name": "testUPD",
  "lastName": "testUPD",
  "email": "test@mail.test",
  "phone": "123456",
  "address": "testUPD",
  "description": "testUPD"
 */
public class ContactResponseDto {
    Integer id;
    String name;
    String lastName;
    String email;
    String phone;
    String address;
    String description;
}
