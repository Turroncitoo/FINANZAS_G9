package ob.debitos.simp.service.excepcion;
public class ArchivoExcelNoValidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ArchivoExcelNoValidoException(String mensaje) {
		super(mensaje);
	}

}
