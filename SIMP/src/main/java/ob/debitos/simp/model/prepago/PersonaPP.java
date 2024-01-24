package ob.debitos.simp.model.prepago;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonaPP {
	private Integer idPersona;
	private String descripcionTipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String alias;
	private String direccion;
	private String telefonoFijo;
	private String telefonoMovil;
	private String codPulsera;
	private Integer codUBA;
	private String correoElectronico;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date dFechaNacimiento;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date dFechaRegistro;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaNacimiento;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
	private Date fechaRegistro;
	
	//Atributos del SIMP_PRE
	private String apePaterno;
	private String apeMaterno;
	private String tipoDocumento;
	private String numDocumento;
	private String telFijo;
	private String telMovil;
	private String codCliente;
	private String nomCliente;
	private String nacionalidad;
	private Integer estadoBD;
	private Date fechaNacimientoo;
	private String sexo;
	private String codigoUbigeo;
	private String pais;
	
	private String fechaNacimientoFormateado;
	private String respuestaSimpHub;
	private String descripcionRespuestaSimpHub;
	
	private String datos;
	private String paisWs;
	private String idPaisWs;
}