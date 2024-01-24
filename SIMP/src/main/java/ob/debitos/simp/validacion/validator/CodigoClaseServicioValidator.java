package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraintvalidation.SupportedValidationTarget;
import javax.validation.constraintvalidation.ValidationTarget;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IClaseServicioService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoClaseServicio;

@SupportedValidationTarget({ ValidationTarget.ANNOTATED_ELEMENT, ValidationTarget.PARAMETERS })
public class CodigoClaseServicioValidator
        implements ConstraintValidator<CodigoClaseServicio, Object>
{
    private int tamanio;
    private boolean existe;
    private boolean enMetodo;
    private int posMembresia;
    private int posClaseServicio;
    private String campoCodigoClaseServicio;
    private String campoCodigoMembresia;

    private @Autowired IClaseServicioService claseServicioService;

    @Override
    public void initialize(CodigoClaseServicio anotacion)
    {
        this.existe = anotacion.existe();
        this.enMetodo = anotacion.enMetodo();
        this.posMembresia = anotacion.posMembresia();
        this.posClaseServicio = anotacion.posClaseServicio();
        this.tamanio = anotacion.tamanio();
        this.campoCodigoClaseServicio = anotacion.campoCodigoClaseServicio();
        this.campoCodigoMembresia = anotacion.campoCodigoMembresia();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoClaseServicio = "";
            String codigoMembresia = "";
            if (enMetodo)
            {
                Object[] parametrosMetodo = (Object[]) dto;
                codigoClaseServicio = String.valueOf(parametrosMetodo[this.posClaseServicio]);
                codigoMembresia = String.valueOf(parametrosMetodo[this.posMembresia]);
            } else
            {
                codigoClaseServicio = BeanUtils.getProperty(dto, this.campoCodigoClaseServicio);
                codigoMembresia = BeanUtils.getProperty(dto, this.campoCodigoMembresia);
            }
            if (codigoClaseServicio == null)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.ClaseServicio.codigoClaseServicio}",
                        this.campoCodigoClaseServicio, context);
                return false;
            }
            if (codigoClaseServicio.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.ClaseServicio.codigoClaseServicio}",
                        this.campoCodigoClaseServicio, context);
                return false;
            }
            if (!codigoClaseServicio.matches(Regex.SOLO_LETRAS_A_a))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.ClaseServicio.codigoClaseServicio}",
                        this.campoCodigoClaseServicio, context);
                return false;
            }
            if (codigoClaseServicio.length() != tamanio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Length.ClaseServicio.codigoClaseServicio}", this.campoCodigoClaseServicio,
                        context);
                return false;
            }
            boolean existeClaseServicio = claseServicioService
                    .existeClaseServicio(codigoClaseServicio, codigoMembresia);
            if (existe && !existeClaseServicio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.ClaseServicio.codigoClaseServicio}",
                        this.campoCodigoClaseServicio, context);
                return false;
            }
            if (!existe && existeClaseServicio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.ClaseServicio.codigoClaseServicio}", this.campoCodigoClaseServicio,
                        context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodigoClaseServicio, context);
            return false;
        }
    }
}