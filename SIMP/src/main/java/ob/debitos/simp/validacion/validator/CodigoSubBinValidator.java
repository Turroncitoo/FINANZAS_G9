package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ISubBinService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoSubBin;

public class CodigoSubBinValidator implements ConstraintValidator<CodigoSubBin, Object>
{
    private int tamanio;
    private boolean existe;
    private String campoCodigoBin;
    private String campoCodigoSubBin;

    private @Autowired ISubBinService subBinService;

    @Override
    public void initialize(CodigoSubBin anotacion)
    {
        this.existe = anotacion.existe();
        this.tamanio = anotacion.tamanio();
        this.campoCodigoBin = anotacion.campoCodigoBin();
        this.campoCodigoSubBin = anotacion.campoCodigoSubBin();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoBIN = BeanUtils.getProperty(dto, this.campoCodigoBin);
            String codigoSubBIN = BeanUtils.getProperty(dto, this.campoCodigoSubBin);
            if (codigoSubBIN == null)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.SubBin.codigoSubBIN}", this.campoCodigoSubBin, context);
                return false;
            }
            if (codigoSubBIN.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.SubBin.codigoSubBIN}", this.campoCodigoSubBin, context);
                return false;
            }
            if (codigoSubBIN.length() != tamanio)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Length.SubBin.codigoSubBIN}", this.campoCodigoSubBin, context);
                return false;
            }
            if (!codigoSubBIN.matches(Regex.SOLO_DIGITOS))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.SubBin.codigoSubBIN}", this.campoCodigoSubBin, context);
                return false;
            }
            boolean existeSubBIN = subBinService.existeSubBin(codigoBIN, codigoSubBIN);
            if (existe && !existeSubBIN)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.SubBin.codigoSubBIN}", this.campoCodigoSubBin, context);
                return false;
            }
            if (!existe && existeSubBIN)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.SubBin.codigoSubBIN}", this.campoCodigoSubBin, context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodigoSubBin, context);
            return false;
        }
    }
}