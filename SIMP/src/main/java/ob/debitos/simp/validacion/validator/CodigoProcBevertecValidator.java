package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICodigoProcBevertecService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoProcBevertec;

public class CodigoProcBevertecValidator implements ConstraintValidator<CodigoProcBevertec, Object>
{
    private int min;
    private int max;
    private boolean existe;
    private String campoCodigoCanalEmisor;
    private String campoTipoTransaccion;

    private @Autowired ICodigoProcBevertecService codigoProcBevertecService;

    @Override
    public void initialize(CodigoProcBevertec anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
        this.campoCodigoCanalEmisor = anotacion.campoCodigoCanalEmisor();
        this.campoTipoTransaccion = anotacion.campoTipoTransaccion();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoCanalEmisor = BeanUtils.getProperty(dto, this.campoCodigoCanalEmisor);
            String tipoTransaccion = BeanUtils.getProperty(dto, this.campoTipoTransaccion);
            if (tipoTransaccion == null)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.CodigoProcBevertec.tipoTransaccion}", this.campoTipoTransaccion,
                        context);
                return false;
            }
            if (tipoTransaccion.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.CodigoProcBevertec.tipoTransaccion}", this.campoTipoTransaccion,
                        context);
                return false;
            }
            if (tipoTransaccion.length() < min || tipoTransaccion.length() > max)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Length.CodigoProcBevertec.tipoTransaccion}", this.campoTipoTransaccion,
                        context);
                return false;
            }
            if (!tipoTransaccion.matches(Regex.SOLO_DIGITOS))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.CodigoProcBevertec.tipoTransaccion}", this.campoTipoTransaccion,
                        context);
                return false;
            }
            boolean existeProcBevertec = codigoProcBevertecService
                    .existeProcBevertec(codigoCanalEmisor, tipoTransaccion);
            if (existe && !existeProcBevertec)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.CodigoProcBevertec.tipoTransaccion}", this.campoTipoTransaccion,
                        context);
                return false;
            }
            if (!existe && existeProcBevertec)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.CodigoProcBevertec.tipoTransaccion}", this.campoTipoTransaccion,
                        context);
                return false;
            }
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoTipoTransaccion, context);
            return false;
        }
        return true;
    }
}