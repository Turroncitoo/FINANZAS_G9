package ob.debitos.simp.controller.excepcion;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class AlegraException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Integer codigoError;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private String mensaje;


    public AlegraException(Integer codeError){
        this.codigoError = codeError;
    }
}
