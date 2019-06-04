package data.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ErrorResponseDto {
    String message;
    String details;
    String timestamp;
    public int code;
}
