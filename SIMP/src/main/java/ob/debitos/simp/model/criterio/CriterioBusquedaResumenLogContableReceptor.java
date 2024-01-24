package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaResumenLogContableReceptor
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioProceso;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinProceso;
    private String codigoMembresia;
    private String codigoClaseServicio;
    private Integer rolTransaccion;
    private Integer idCanal;
    private Integer idAtm;
    private Integer codigoTransaccion;
}