package pragma.crediya.request.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {
    private List<T> contenido;
    private int numeroPagina;
    private int tamañoPagina;
    private long totalElementos;
    private int totalPaginas;
    private boolean primera;
    private boolean ultima;
}
