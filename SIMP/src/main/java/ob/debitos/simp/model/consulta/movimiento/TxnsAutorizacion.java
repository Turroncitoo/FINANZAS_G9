package ob.debitos.simp.model.consulta.movimiento;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsAutorizacion
{
    private String fechaTransmision;
    private String numeroCuenta;
    private String descripcionRol;
    private String descripcionCanal;
    private String descripcionProceso;
    private String identificadorCuenta;
    private String horaTransmision;
    private String codigoAutorizacion;
    private String numeroRastreo;
    private String descripcionRespuesta;
    private int transaccionMonetaria;
    private String cantidadTransaccion;
    private String ubicacionTarjeta;
    private String estadoTarjeta;
    private String numeroDocumentoAutorizacion;
    private String tipoMensaje;
}