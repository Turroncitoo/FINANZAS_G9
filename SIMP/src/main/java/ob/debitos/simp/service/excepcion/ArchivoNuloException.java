package ob.debitos.simp.service.excepcion;

public class ArchivoNuloException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ArchivoNuloException(String mensaje) {
		super(mensaje);
	}

}
