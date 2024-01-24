package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaResumen
{
    private String verbo;
    private Integer codigoInstitucion;
    private String idEmpresa;
    private String idCliente;
    private String descripcionEmpresa;
    private String descripcionCliente;
    private int codigoMoneda;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProceso;
    private String descripcionFechaProceso;
    private String descripcionInstitucion;
}