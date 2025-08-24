package pragma.crediya.request.infrastructure.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("status")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StatusEntity {

    @Id
    @Column("id_status")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;
}
