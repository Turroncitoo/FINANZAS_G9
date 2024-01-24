package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class CriterioBusquedaTransaccionNoConciliada
{
    private String tipoBusqueda;
    private Integer numeroDias;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaCorte;
    
    private String idEmpresa;
    private String idCliente;
    
    private String descripcionEmpresa;
    private String descripcionCliente;
    
    private String descripcionFechaCorte;
    
    private String descripcionTipoBusqueda;
    
    private String descripcionOrigen;
    
    private Integer idInstitucion;
}