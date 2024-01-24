package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IParametroSFTPProcesoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoParametroFTPProceso;

public class CodigoParametroFTPProcesoValidator  implements ConstraintValidator<CodigoParametroFTPProceso, String>  {
	private int min;
	private int max;
	private boolean existe;
	
	private @Autowired IParametroSFTPProcesoService parametroSFTPPorceso;
	
	@Override
    public void initialize(CodigoParametroFTPProceso anotacion)
    {
        this.min = anotacion.min();
		this.max = anotacion.max();
        this.existe = anotacion.existe();
    }
	
	@Override
    public boolean isValid(String codigoProceso, ConstraintValidatorContext context)
    {
        if (codigoProceso == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ParametrosSFTPProceso.codigo}", context);
            return false;
        }
        if (codigoProceso.length() < min || codigoProceso.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.ParametrosSFTPProceso.codigo}", context);
            return false;
        }
        if (!codigoProceso.matches(Regex.SOLO_LETRAS_A_a)){
        	ValidatorUtil.addCustomMessageWithTemplate("{Pattern.ParametrosSFTPProceso.codigo}", context);
            return false;
        }
        boolean existeParametro = parametroSFTPPorceso.existeParametroSFTPProceso(codigoProceso);
        return existe ? existeParametro : !existeParametro;
    }
	
}
