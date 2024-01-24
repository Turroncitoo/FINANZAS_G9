package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IModoEntradaPosService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoModoEntradaPos;

public class CodigoModoEntradaPosValidator
        implements ConstraintValidator<CodigoModoEntradaPos, String>
{
    private boolean existe;
    private int max;

    private @Autowired IModoEntradaPosService modoEntradaPosService;

    @Override
    public void initialize(CodigoModoEntradaPos anotacion)
    {
        this.existe = anotacion.existe();
        this.max = anotacion.max();
    }

    @Override
    public boolean isValid(String codigoModoEntradaPOS, ConstraintValidatorContext context)
    {
        if (codigoModoEntradaPOS == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.ModoEntradaPos.codigoModoEntradaPOS}", context);
            return false;
        }
        if (codigoModoEntradaPOS.length() > 0 && codigoModoEntradaPOS.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.ModoEntradaPos.codigoModoEntradaPOS}", context);
            return false;
        }
        if (!codigoModoEntradaPOS.matches(Regex.SOLO_DIGITOS_O_VACIO))
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Pattern.ModoEntradaPos.codigoModoEntradaPOS}", context);
            return false;
        }
        if (codigoModoEntradaPOS.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.ModoEntradaPos.codigoModoEntradaPOS}", context);
            return false;
        }
        boolean existeModoEntradaPOS = modoEntradaPosService
                .existeModoEntradaPos(codigoModoEntradaPOS);
        return (existe) ? existeModoEntradaPOS : !existeModoEntradaPOS;
    }
}