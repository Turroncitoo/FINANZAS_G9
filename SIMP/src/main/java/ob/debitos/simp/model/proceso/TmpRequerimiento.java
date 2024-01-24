package ob.debitos.simp.model.proceso;

import java.math.BigDecimal;
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
public class TmpRequerimiento {
	
	private Integer idLote;
	private String idBIN;
	private String idSubBIN;
	private Integer tipoDocumento;
	private String numeroDocumento;
	private String nombre;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String nombreEmbossing;
	private String nombreCliente;
	private Integer codigoMoneda;
	private BigDecimal montoRecarga;
	private Integer mesesVencimiento;
	private String correo;
	private String direccion;
	private String telefonoMovil;
	private String telefonoFijo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaNacimiento;
	private String nacionalidad;
	private String idAfinidad;
	private String idTipoEmision;
	
}
