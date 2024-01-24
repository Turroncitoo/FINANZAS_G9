package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CriterioBusquedaEstadoCuenta
{
    private String idEmpresa;
    private String idCliente;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private  Date fechaInicio;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    
    private String numeroTarjeta;
    
}
