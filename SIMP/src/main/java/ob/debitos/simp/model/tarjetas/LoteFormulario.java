package ob.debitos.simp.model.tarjetas;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteFormulario
{

    List<LoteRequest> recargas;
    List<LoteRequest> debitos;
    List<LoteRequest> afiliaciones;
    AfiliacionFormulario formulario;

}
