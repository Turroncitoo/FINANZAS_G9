package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICodigoTxnBevertecService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoTxnBevertec;

public class CodigoTxnBevertecValidator implements ConstraintValidator<CodigoTxnBevertec, Object>
{
    private int tamanio;
    private boolean existe;
    private String campoCodigoCanalEmisor;
    private String campoTipoTransaccion;
    private String campoCodTransaccion;

    private @Autowired ICodigoTxnBevertecService codigoTxnBevertecService;

    @Override
    public void initialize(CodigoTxnBevertec anotacion)
    {
        this.tamanio = anotacion.tamanio();
        this.existe = anotacion.existe();
        this.campoCodigoCanalEmisor = anotacion.campoCodigoCanalEmisor();
        this.campoTipoTransaccion = anotacion.campoTipoTransaccion();
        this.campoCodTransaccion = anotacion.campoCodTransaccion();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoCanalEmisor = BeanUtils.getProperty(dto, this.campoCodigoCanalEmisor);
            String tipoTransaccion = BeanUtils.getProperty(dto, this.campoTipoTransaccion);
            String codTransaccion = BeanUtils.getProperty(dto, this.campoCodTransaccion);
            if (codTransaccion == null)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.CodigoTxnBevertec.codTransaccion}", this.campoCodTransaccion,
                        context);
                return false;
            }
            if (codTransaccion.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.CodigoTxnBevertec.codTransaccion}", this.campoCodTransaccion,
                        context);
                return false;
            }
            if (codTransaccion.length() != tamanio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Length.CodigoTxnBevertec.codTransaccion}", this.campoCodTransaccion,
                        context);
                return false;
            }
            if (!codTransaccion.matches(Regex.SOLO_DIGITOS))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.CodigoTxnBevertec.codTransaccion}", this.campoCodTransaccion,
                        context);
                return false;
            }
            boolean existeCodigoTxnBevertec = codigoTxnBevertecService
                    .existeCodigoTxnBevertec(codigoCanalEmisor, tipoTransaccion, codTransaccion);
            if (existe && !existeCodigoTxnBevertec)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.CodigoTxnBevertec.codTransaccion}", this.campoCodTransaccion,
                        context);
                return false;
            }
            if (!existe && existeCodigoTxnBevertec)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.CodigoTxnBevertec.codTransaccion}", this.campoCodTransaccion,
                        context);
                return false;
            }
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodTransaccion, context);
            return false;
        }
        return true;
    }
}