package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaContabilizacion
{
    private String verbo;

    private Integer indicadorContabilizacion;
    private List<Integer> indicadoresContabilizacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioProceso;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinProceso;

    private String descripcionFechaProceso;

    private Integer codigoInstitucion;
    private String descripcionInstitucion;

    private String idEmpresa;
    private String descripcionEmpresa;

    private String idCliente;
    private List<String> clientes;
    private String descripcionCliente;

    private String descripcionIndicador;

    private String indicadores;

    private String empresas;
}