package ob.debitos.simp.model.proceso;

import java.util.Date;

import lombok.Data;

@Data
public class ArchivoContabSwDmpLogFondos {
	private String idEmpresa;
	private Integer origenInformacion;	 
	private Integer secuenciaTransaccion;
	private String idCliente;	
	private String tipoDocumento;
	private String numeroDocumento;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private String idMembresia;
	private String idClaseServicio;	
	private String idBIN;
	private String idSubBIN;
	private String numeroTarjeta;	
	private String codigoSeguimiento;
	private Date fechaTransaccion;
	private Date fechaProceso;
	private Date fechaAtencion;	
	private String  numeroVoucher;
	private String lugarAdquirente;
	private Double cobroITF;	
	private Integer monedaTransacion;
	private Double  montoITF;
	private Integer  monedaCompensacion;
	private Double  valorCompnesacion;		
	private Integer  monedaCargoTHB;
	private Double valorCargoTHB;
	private Integer indTCSbs;
	private String  cuentaCargo;
	private String cuentaAbono;
	private String  codigoAnalitico;
}
