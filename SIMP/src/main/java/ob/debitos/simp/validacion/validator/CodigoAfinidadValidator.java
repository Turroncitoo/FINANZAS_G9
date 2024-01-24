package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IAfinidadService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoAfinidad;

@SupportedValidationTarget({ ValidationTarget.ANNOTATED_ELEMENT, ValidationTarget.PARAMETERS })
public class CodigoAfinidadValidator  implements ConstraintValidator<CodigoAfinidad, Object> {
	private String campoCodigoAfinidad;
    private String campoCodigoLogo;
    private boolean existe;

    private @Autowired IAfinidadService afinidadService;

    @Override
    public void initialize(CodigoAfinidad anotacion)
    {
        this.existe = anotacion.existe();
        this.campoCodigoAfinidad = anotacion.campoCodigoAfinidad();
        this.campoCodigoLogo = anotacion.campoCodigoLogo();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoAfinidad = BeanUtils.getProperty(dto, this.campoCodigoAfinidad);;
            String codigoLogo = BeanUtils.getProperty(dto, this.campoCodigoLogo);
            boolean existeAfinidad = this.afinidadService.existeAfinidad(codigoAfinidad, codigoLogo);
            if (existe && !existeAfinidad)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Afinidad.codigo}",
                        this.campoCodigoAfinidad, context);
                return false;
            }
            if (!existe && existeAfinidad)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.Afinidad.codigo}", this.campoCodigoAfinidad,
                        context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodigoAfinidad, context);
            return false;
        }
    }
}
