package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICodigoTransaccionService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdCodigoTransaccion;

public class IdCodigoTransaccionValidator
        implements ConstraintValidator<IdCodigoTransaccion, Object>
{
    private int min;
    private int max;
    private boolean existe;
    private String campoCodigoTransaccion;
    private String campoCodigoClaseTransaccion;

    private @Autowired ICodigoTransaccionService codigoTransaccionService;

    @Override
    public void initialize(IdCodigoTransaccion anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
        this.campoCodigoTransaccion = anotacion.campoCodigoTransaccion();
        this.campoCodigoClaseTransaccion = anotacion
                .campoCodigoClaseTransaccion();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            Integer codigoTransaccion = Integer.parseInt(Optional.ofNullable(
                    BeanUtils.getProperty(dto, this.campoCodigoTransaccion))
                    .orElse("-1"));
            Integer codigoClaseTransaccion = Integer
                    .parseInt(Optional
                            .ofNullable(BeanUtils.getProperty(dto,
                                    this.campoCodigoClaseTransaccion))
                            .orElse("-1"));
            if (codigoTransaccion == -1 || codigoClaseTransaccion == -1)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.CodigoTransaccion.codigoTransaccion}",
                        this.campoCodigoTransaccion, context);
                return false;
            }
            if (codigoTransaccion < min || codigoTransaccion > max)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Range.CodigoTransaccion.codigoTransaccion}",
                        this.campoCodigoTransaccion, context);
                return false;
            }
            boolean existeCodigoTransaccion = codigoTransaccionService
                    .existeCodigoTransaccion(codigoTransaccion,
                            codigoClaseTransaccion);
            if (existe && !existeCodigoTransaccion)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.CodigoTransaccion.codigoTransaccion}",
                        this.campoCodigoTransaccion, context);
                return false;
            }
            if (!existe && existeCodigoTransaccion)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.CodigoTransaccion.codigoTransaccion}",
                        this.campoCodigoTransaccion, context);
                return false;
            }
            return true;
        } catch (NumberFormatException | IllegalAccessException
                | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                    "{Excepcion.DTO.propiedad}", this.campoCodigoTransaccion,
                    context);
            return false;
        }
    }
}