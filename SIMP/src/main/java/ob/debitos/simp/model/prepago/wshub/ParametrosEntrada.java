package ob.debitos.simp.model.prepago.wshub;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParametrosEntrada
{

    private String numeroTarjeta;
    private String numeroTarjetaNuevo;
    private String numeroTarjetaAnterior;
    private String motivo;
    private String moneda;
    private Double monto;
    
    private String apellidoMaterno;
    private String apellidoPaterno; 
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fechaCumpleanios;
    
    private String email;
    private String nombre1;
    private String nombre2;
    private String tipoDocumento;
    private String numeroDocumento;

    
}
