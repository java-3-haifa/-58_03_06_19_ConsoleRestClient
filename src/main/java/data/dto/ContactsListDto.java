package data.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ContactsListDto {
    List<ContactDto> contacts;
}
