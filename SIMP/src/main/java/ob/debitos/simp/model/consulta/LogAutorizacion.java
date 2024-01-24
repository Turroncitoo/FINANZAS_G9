package ob.debitos.simp.model.consulta;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogAutorizacion
{
    private String tipoMensaje;
    private String numeroTarjeta;
    private String descripcionCanal;
    private String descripcionProceso;
    private String codigoProcesamiento;
    private Integer monedaTransaccion;
    private String valorTransaccion;
    private String fechaTransmision;
    private String horaTransmision;
    private String trace;
    private String codigoAutorizacion;
    private String descripcionRespuesta;
    private String idTerminal;
    private String adquirente;
    private Integer numeroDias;
    
    private String fechaConciliaLog;
    
    private String transmissionDate;
    
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    
    private String idEmpresa;
    private String idCliente;
    
    private String descripcionEmpresa;
    private String descripcionCliente;

}
