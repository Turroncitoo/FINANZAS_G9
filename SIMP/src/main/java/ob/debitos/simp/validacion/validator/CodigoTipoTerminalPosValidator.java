package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ITipoTerminalPosService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoTipoTerminalPos;

public class CodigoTipoTerminalPosValidator
        implements ConstraintValidator<CodigoTipoTerminalPos, String>
{
    private int max;
    private boolean existe;

    private @Autowired ITipoTerminalPosService tipoTerminalPosService;

    @Override
    public void initialize(CodigoTipoTerminalPos anotacion)
    {
        this.existe = anotacion.existe();
        this.max = anotacion.max();
    }

    @Override
    public boolean isValid(String codigoTipoTerminalPOS, ConstraintValidatorContext context)
    {
        if (codigoTipoTerminalPOS == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.TipoTerminalPos.codigoTipoTerminalPOS}", context);
            return false;
        }
        if (codigoTipoTerminalPOS.length() > 0 && codigoTipoTerminalPOS.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.TipoTerminalPos.codigoTipoTerminalPOS}", context);
            return false;
        }
        if (!codigoTipoTerminalPOS.matches(Regex.SOLO_DIGITOS_O_VACIO))
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Pattern.TipoTerminalPos.codigoTipoTerminalPOS}", context);
            return false;
        }
        if (codigoTipoTerminalPOS.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.TipoTerminalPos.codigoTipoTerminalPOS}", context);
            return false;
        }
        boolean existeTipoTerminalPOS = tipoTerminalPosService
                .existeTipoTerminarPos(codigoTipoTerminalPOS);
        return (existe) ? existeTipoTerminalPOS : !existeTipoTerminalPOS;
    }
}