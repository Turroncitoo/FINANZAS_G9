package ob.debitos.simp.model.prepago;

import lombok.Data;

@Data
public class CuentaPP {
	private String idCuenta;
	private String fechaAlta;
	private String saldoDisponible;
	private String saldoContable;
	private String idBin;
	private String descripcionBIN;
	private String idSubBin;
}
