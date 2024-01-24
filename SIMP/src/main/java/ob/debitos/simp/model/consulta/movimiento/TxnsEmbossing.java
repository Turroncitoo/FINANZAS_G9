package ob.debitos.simp.model.consulta.movimiento;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnsEmbossing
{	
	//Nuevos
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
	
	@NoIdentificable(campo = "numeroTarjeta")
	private String numeroTarjeta;
	
	private String tipoDocumento;
	private String descripciontipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String fechaBloqueo;
	private String tipoBloqueo;
	private String descripcionTipoBloqueo;
	private String codigoSeguimiento;
	private String informacion;
	private String horaProcesoUBA;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaProcesoUBA;
}
