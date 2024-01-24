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
public class ReporteCuadreCuentasPorPagarDetalle
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaTransaccion;
    private Integer codigoOrigenTransaccion;
    private String descripcionOrigenTransaccion;
    private Integer idClaseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer idCodigoTransaccion;
    private String descripcionCodigoTransaccion;
    private String cuentaCargo;
    private String cuentaAbono;
    private Integer cantidadCargo;
    private Integer cantidadAbono;
    private double totalCargo;
    private double totalAbono;
    private Integer codigoInstitucion;
    private String descripcionInstitucion;
}
