package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IParametroSFTPArchivoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoParametroFTPArchivo;

public class CodigoParametroFTPArchivoValidator 
	implements ConstraintValidator<CodigoParametroFTPArchivo, String> 
{
	private int min;
	private int max;
	private boolean existe;
	
	private @Autowired IParametroSFTPArchivoService parametroSFTPArchivo;
	
	@Override
    public void initialize(CodigoParametroFTPArchivo anotacion)
    {
        this.min = anotacion.min();
		this.max = anotacion.max();
        this.existe = anotacion.existe();
    }
	
	@Override
    public boolean isValid(String codigoArchivo, ConstraintValidatorContext context)
    {
        if (codigoArchivo == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ParametrosSFTPArchivo.codigoArchivo}", context);
            return false;
        }
        if (codigoArchivo.length() < min || codigoArchivo.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.ParametrosSFTPArchivo.codigoArchivo}", context);
            return false;
        }
        if (!codigoArchivo.matches(Regex.ALFANUMERICO)){
        	ValidatorUtil.addCustomMessageWithTemplate("{Pattern.ParametrosSFTPArchivo.codigoArchivo}", context);
            return false;
        }
        boolean existeParametro = parametroSFTPArchivo.existeParametroSFTPArchivo(codigoArchivo);
        return existe ? existeParametro : !existeParametro;
    }
}