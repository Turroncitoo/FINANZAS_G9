package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaResumenTransaccionAprobadaAgencia
{    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private Integer idAgencia;
    private String idSexo;
    private String idEstadoCivil;
    private String descripcionAgencia;
    private String descripcionSexo;
    private String descripcionEstadoCivil;
    private String descripcionRangoFechas;
}