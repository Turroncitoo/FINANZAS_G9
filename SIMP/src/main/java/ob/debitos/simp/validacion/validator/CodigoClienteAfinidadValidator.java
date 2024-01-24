package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IClienteAfinidadService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoClienteAfinidad;

public class CodigoClienteAfinidadValidator implements ConstraintValidator<CodigoClienteAfinidad, Object> {
	private boolean existe;
	private String campoIdAfinidad;
	private String campoIdEmpresa;
	private String campoIdCliente;
	private @Autowired IClienteAfinidadService clienteAfinidadService;
	
	@Override
    public void initialize(CodigoClienteAfinidad anotacion)
    {
        this.existe = anotacion.existe();
        this.campoIdAfinidad = anotacion.campoIdAfinidad();
        this.campoIdEmpresa = anotacion.campoIdEmpresa();
        this.campoIdCliente = anotacion.campoIdCliente();
    }
	
	@Override
    public boolean isValid(Object obj, ConstraintValidatorContext context)
    {		
		try{
			Integer idAfinidad = Integer.parseInt(BeanUtils.getProperty(obj, this.campoIdAfinidad));
			String idEmpresa = BeanUtils.getProperty(obj, this.campoIdEmpresa);
			String idCliente = BeanUtils.getProperty(obj, this.campoIdCliente);
	        boolean existeAsociacionClienteAfinidad = this.clienteAfinidadService.existeAsociacionClienteAfinidad(idEmpresa, idCliente, idAfinidad);
	        if (existe && !existeAsociacionClienteAfinidad)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.ClienteAfinidad.codigoProceso}", this.campoIdAfinidad, context);
                return false;
            }
            if (!existe && existeAsociacionClienteAfinidad)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.ClienteAfinidad.idAfinidad}", this.campoIdAfinidad, context);
                return false;
            }
            return true;
			
		}catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoIdAfinidad, context);
            return false;
        }
    }
	
}
