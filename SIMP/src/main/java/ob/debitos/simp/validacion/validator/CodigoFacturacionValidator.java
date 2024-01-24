package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICodigoFacturacionService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdCodigoFacturacion;

/**
 * Evalúa las restricciones de validación y existencia del código de
 * facturación.
 * 
 * @author Fernando Fonseca
 */
public class CodigoFacturacionValidator implements ConstraintValidator<IdCodigoFacturacion, Integer> {
	private int min;
	private int max;
	private boolean existe;

	private @Autowired ICodigoFacturacionService codigoFacturacionService;

	@Override
	public void initialize(IdCodigoFacturacion anotacion) {
		this.min = anotacion.min();
		this.max = anotacion.max();
		this.existe = anotacion.existe();
	}

	@Override
	public boolean isValid(Integer idCodigoFacturacion, ConstraintValidatorContext context) {
		if (idCodigoFacturacion == null) {
			ValidatorUtil.addCustomMessageWithTemplate("{NotNull.CodigoFacturacion.idCodigoFacturacion}", context);
			return false;
		}
		if (idCodigoFacturacion < min || idCodigoFacturacion > max) {
			ValidatorUtil.addCustomMessageWithTemplate("{Range.CodigoFacturacion.idCodigoFacturacion}", context);
			return false;
		}
		boolean existeCanal = codigoFacturacionService.existeCodigoFacturacion(idCodigoFacturacion);
		return existe ? existeCanal : !existeCanal;
	}

}
