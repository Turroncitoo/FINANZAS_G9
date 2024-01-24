package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IParametroSFTPDirectorioService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoParametroFTPDirectorio;

public class CodigoParametroFTPDirectorioValidator implements 
	ConstraintValidator<CodigoParametroFTPDirectorio, Object> {
	
	private boolean existe;
	private String campoCodigoProceso;
	private String campoTipoOperacion;
	
	private @Autowired IParametroSFTPDirectorioService parametroSFTPDirectorioService;
	
	@Override
    public void initialize(CodigoParametroFTPDirectorio anotacion)
    {
        this.existe = anotacion.existe();
        this.campoCodigoProceso = anotacion.campoCodigoProceso();
        this.campoTipoOperacion = anotacion.campoCodigoTipoOperacion();
    }
	
	@Override
    public boolean isValid(Object obj, ConstraintValidatorContext context)
    {		
		try{
			String codigoProceso = BeanUtils.getProperty(obj, this.campoCodigoProceso);
			String tipoOperacion = BeanUtils.getProperty(obj, this.campoTipoOperacion);
	        if (codigoProceso == null)
	        {
	            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ParametrosSFTPDirectorio.codigoProceso}", context);
	            return false;
	        }
	        if(tipoOperacion == null){
	        	ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ParametrosSFTPDirectorio.tipoOperacion}", context);
	            return false;
	        }
	        if(codigoProceso.trim().length() == 0){
	        	ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.ParametrosSFTPDirectorio.codigoProceso}", context);
	            return false;
	        }
	        if(tipoOperacion.trim().length() == 0){
	        	ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.ParametrosSFTPDirectorio.tipoOperacion}", context);
	            return false;
	        }
	        boolean existeParametro = this.parametroSFTPDirectorioService.existeParametroSFTPDirectorio(codigoProceso, tipoOperacion);
	        if (existe && !existeParametro)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.ParametrosSFTPDirectorio.codigoProceso}", this.campoCodigoProceso, context);
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.ParametrosSFTPDirectorio.tipoOperacion}", this.campoTipoOperacion, context);
                return false;
            }
            if (!existe && existeParametro)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.ParametrosSFTPDirectorio.codigoProceso}", this.campoCodigoProceso, context);
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.ParametrosSFTPDirectorio.tipoOperacion}", this.campoTipoOperacion, context);
                return false;
            }
            return true;
			
		}catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodigoProceso, context);
            return false;
        }
    }
}
