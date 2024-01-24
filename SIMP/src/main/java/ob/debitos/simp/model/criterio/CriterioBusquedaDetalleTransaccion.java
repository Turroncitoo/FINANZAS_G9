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
public class CriterioBusquedaDetalleTransaccion
{

    private String numeroCuenta;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransmision;

    private String numeroRastreo;

    private String tipoMensaje;

    private String numeroTarjeta;

    private String numeroVoucher;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransaccion;

    private String claseTransaccion;

    private String secuenciaTransaccion;

    private Integer idInstitucion;

}