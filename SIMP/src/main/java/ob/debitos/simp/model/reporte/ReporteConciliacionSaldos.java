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
public class ReporteConciliacionSaldos {
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaTransaccion;
	private String idTipoTransaccion;
	private String descripcionTipoTransaccion;
	private Integer idClaseTransaccion;
	private Integer idCodigoTransaccion;
	private String descripcionCodigoTransaccion;
	private Integer cantidadTransacciones;
	private Double montoTotal;
}
