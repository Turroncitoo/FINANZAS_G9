package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa el criterio de búsqueda para las comisiones de las transacciones
 * provenientes de las autorizaciones y del archivo Log Contable.
 * 
 * @author Robert Vega
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaDetalleComisionTransaccion
{
    private String numeroCuenta;
    private String numeroRastreo;
    private String tipoMensaje;
    private String numeroTarjeta;
    private String numeroVoucher;
    private String numeroTrace;
    private String codigoProceso;
    private Integer secuenciaTransaccion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransmision;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransaccion;
    
    private Integer codigoSeguimiento;
}