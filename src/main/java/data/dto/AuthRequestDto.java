package data.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class AuthRequestDto {
    String email;
    String password;
}
