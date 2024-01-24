package ob.debitos.simp.model.criterio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CriterioBusquedaApiSimphub {
	String codigoSeguimiento;
	String codigoEmpresa;
	String clienteUBAAsociar;
	String regimen;
	String indCategoria;
	
	String motivoBloqueo;
	String codigoSeguimientoNuevo;
	String tipoDocumento;
	String numeroDocumento;
	String afinidad;
	String programaLealtad;
	String empresa;
	String direccion;
	String monedaComision;
	String montoComision;
	String comercio;
	String monedaRecarga;
	String montoRecarga;
	String ciudad;
	String terminal;
	String monedaDebito;
	String montoDebito;
	String monedaTransferencia;
	String montoTransferencia;
	String codigoSeguimientoDestino;
	
	Double montoBD;
	
	String secuenciaOriginal;
	String tLocalOriginal;
	
	String transaccion;
}
