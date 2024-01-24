package ob.debitos.simp.controller.excepcion;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import ob.debitos.simp.model.parametro.MensajeError;
import ob.debitos.simp.model.parametro.MensajeValidacion;
import ob.debitos.simp.service.excepcion.ArchivoExcelNoValidoException;
import ob.debitos.simp.service.excepcion.ArchivoNuloException;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.service.excepcion.EscenarioException;
import ob.debitos.simp.service.excepcion.ListaVaciaException;
import ob.debitos.simp.service.excepcion.OrdenEjecucionException;
import ob.debitos.simp.service.excepcion.SimpException;
import ob.debitos.simp.service.excepcion.ValorEncontradoException;
import ob.debitos.simp.service.excepcion.ValorNoEncontradoException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.StringsUtils;

@RestControllerAdvice(annotations = RestController.class)
public class ExceptionRestController
{
    private @Autowired Logger logger;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = ConstraintViolationException.class)
    public List<MensajeValidacion> capturarConstraintViolationException(
            ConstraintViolationException ex)
    {
        logger.error(ex.getConstraintViolations().stream().findFirst().get().getMessage(), ex);
        return ex.getConstraintViolations().stream()
                .map(constraint -> new MensajeValidacion(StringsUtils.obtenerCadenaDespuesDePunto(
                        constraint.getPropertyPath().toString()), constraint.getMessage()))
                .distinct().collect(Collectors.toList());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BadRequestException.class)
    public List<MensajeValidacion> capturarBadRequestException(BadRequestException ex)
    {
        logger.error(ex.getMessage(), ex);
        return ex.getMensajesValidacion();
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = { EscenarioException.class, OrdenEjecucionException.class,
            EjecucionManualException.class, ListaVaciaException.class,
            ValorNoEncontradoException.class, ValorEncontradoException.class, 
            IndexOutOfBoundsException.class, NullPointerException.class,
            IllegalStateException.class, IllegalArgumentException.class, SimpException.class })
    public String capturarEscenarioException(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ex.getMessage();
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String capturarMissingServletRequestParameterException(
            MissingServletRequestParameterException ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_MISSING_PARAMETER_EXCEPTION_REST;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = { TypeMismatchException.class, InvalidFormatException.class,
            HttpMessageNotReadableException.class })
    public String capturarTypeMismatchException(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_TYPE_MISMATCH_EXCEPTION_REST;
    }

    @ResponseStatus(code = HttpStatus.CONFLICT)
    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public MensajeError capturarDataIntegrityViolation(DataIntegrityViolationException ex)
    {
        logger.error(ex.getClass().getName(), ex);
        int codigoErrorSqlServer = ((SQLServerException) ex.getMostSpecificCause()).getErrorCode();
        return new MensajeError(codigoErrorSqlServer,
                ConstantesExcepciones.EXCEPCION_SQL_SERVER.get(codigoErrorSqlServer));
    }
    
    @ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(value = ArchivoNuloException.class)
	public MensajeError capturarArchivoNuloException(ArchivoNuloException ex) {
		logger.error(ex.getClass().getName(), ex);
		return new MensajeError(1111, ex.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(value = ArchivoExcelNoValidoException.class)
	public MensajeError capturarArchivoExcelNoValidoException(ArchivoExcelNoValidoException ex) {
		logger.error(ex.getClass().getName(), ex);
		return new MensajeError(1112, ex.getMessage());
	}

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public String capturarOtrasExcepcion(Exception ex)
    {
        logger.error(ex.getMessage(), ex);
        return ConstantesExcepciones.ERROR_DESCONOCIDO;
    }
}