package ob.debitos.simp.model.proceso;

import lombok.Data;

@Data
public class ReporteContableLogCont {
	 private String fechaTransaccion;
	 private String fechaProceso;
	 private String fechaAfectacion;
	 private String idCliente;
	 private String descripcionCliente;
	 private Integer idPersona;
	 private String numeroTarjeta;
	 private Integer tipoDocumento;	
	 private String descripcionTipoDocumento;
	 private String numeroDocumento;
	 private String nombres;
	 private String apellidoPaterno;
	 private String apellidoMaterno;
	 private String idBIN;
	 private String descripcionBIN;
	 private String idSubBIN;
	 private String descripcionSubBIN;
	 private String nombreAfiliado;
	 private Integer transaccion ;
	 private String descripcionTransaccion;
	 private Integer monedaTransaccion;
	 private String valorTransaccion;  
	 private Integer monedaCompensacion;
	 private String valorCompensacion;  
	 private String cuentaCargo;
	 private String cuentaAbono;
	 private String codigoAnalitico;
	 
	 private Integer codigoRpta;
	 private String codigoAutorizacion;
	 private String numeroTrace;
	 private String horaTransaccion;
	 
	 private Integer conciliaLog;
	 private String fechaConcilia;
	 private Integer conciliaSwitch;
	 
	 private String fechaLocalTxn;
	 private String horaLocalTxn;
	 
}
