package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICanalesSegurosWSService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CanalSeguroWS;

public class CanalSeguroWSValidator
        implements ConstraintValidator<CanalSeguroWS, String>
{
    private int tamanioMin;
    private int tamanioMax;
    private boolean existe;

    private @Autowired ICanalesSegurosWSService canalesSegurosWSService;

    @Override
    public void initialize(CanalSeguroWS anotacion)
    {
        this.existe = anotacion.existe();
        this.tamanioMin = anotacion.tamanioMin();
        this.tamanioMax = anotacion.tamanioMax();
    }

    @Override
    public boolean isValid(String canal, ConstraintValidatorContext context)
    {
        if (canal == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.CanalesSegurosWS.idCanal}", context);
            return false;
        }
        if (canal.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.CanalesSegurosWS.idCanal}", context);
            return false;
        }
        if (canal.trim().length() < tamanioMin || canal.trim().length() > tamanioMax)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Length.CanalesSegurosWS.idCanal}", context);
            return false;
        }
        boolean existeCanal = canalesSegurosWSService.existeCanalSeguro(canal);
        return existe ? existeCanal : !existeCanal;
    }
}