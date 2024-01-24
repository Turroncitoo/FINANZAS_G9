package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteCuadreCuentasPorPagarResumen
{
    // common
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;
    private String cuentaCargo;
    private String cuentaAbono;
    private Integer cantidadCargo;
    private Integer cantidadAbono;
    private double totalCargo;
    private double totalAbono;
    // resumen compensacion
    private Integer codigoClaseTransaccion;
    private String descripcionClaseTransaccion;
    // resumen autorizaciones
    private String tipoMensaje;
    private Integer codigoConciliacion;
    private String descripcionConciliacion;
    private Integer codigoInstitucion;
    private String descripcionInstitucion;
}