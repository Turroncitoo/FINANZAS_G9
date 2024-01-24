package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.tarifario.Surcharge;
import ob.debitos.simp.service.ISurchargeService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdSurcharge;

public class IdSurchargeValidator implements ConstraintValidator<IdSurcharge, Object>
{
    private boolean existe;
    private String campoNivel;

    private @Autowired ISurchargeService surchargeService;

    @Override
    public void initialize(IdSurcharge anotacion)
    {
        this.existe = anotacion.existe();
        this.campoNivel = anotacion.campoNivel();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            Integer nivel = Integer.parseInt(BeanUtils.getProperty(dto, this.campoNivel));
            Surcharge surcharge = Surcharge.builder().nivel(nivel).build();
            List<Surcharge> surcharges = surchargeService.buscarPorCodigo(surcharge);
            if (existe && surcharges.isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Surcharge.codigo}", this.campoNivel, context);
                return false;
            }
            if (!existe && !surcharges.isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Existe.Surcharge.codigo}",
                        this.campoNivel, context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}