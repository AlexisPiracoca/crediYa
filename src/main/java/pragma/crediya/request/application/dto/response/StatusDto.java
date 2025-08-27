package pragma.crediya.request.application.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatusDto {
    private Long id;
    private String name;
    private String description;
}