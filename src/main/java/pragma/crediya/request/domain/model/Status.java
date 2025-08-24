package pragma.crediya.request.domain.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    private Long id;
    private String name;
    private String description;
}
