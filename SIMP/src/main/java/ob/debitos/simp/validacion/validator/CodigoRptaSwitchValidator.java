package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICodigoRptaSwitchService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoRptaSwitch;

public class CodigoRptaSwitchValidator implements ConstraintValidator<CodigoRptaSwitch, String>
{
    private boolean existe;
    private int min;
    private int max;
    private String pattern;

    private @Autowired ICodigoRptaSwitchService codigoRptaSwitchService;

    @Override
    public void initialize(CodigoRptaSwitch anotacion)
    {
        this.existe = anotacion.existe();
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.pattern = anotacion.pattern();
    }

    @Override
    public boolean isValid(String codigoRespuestaSwitch, ConstraintValidatorContext context)
    {
        if (codigoRespuestaSwitch == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.CodigoRptaSwitch.codigoRespuestaSwitch}", context);
            return false;
        }
        if (codigoRespuestaSwitch.length() < this.min || codigoRespuestaSwitch.length() > this.max) {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.CodigoRptaSwitch.codigoRespuestaSwitch}", context);
            return false;
        }
        if (!codigoRespuestaSwitch.matches(this.pattern)) {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Pattern.CodigoRptaSwitch.codigoRespuestaSwitch}", context);
            return false;
        }
        boolean existeCodigoRespuestaSwitch = codigoRptaSwitchService
                .existeCodigoRespuestaSwitch(codigoRespuestaSwitch);
        return (existe) ? existeCodigoRespuestaSwitch : !existeCodigoRespuestaSwitch;
    }
}