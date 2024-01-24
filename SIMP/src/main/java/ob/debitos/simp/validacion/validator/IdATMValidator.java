package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IAtmService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdATM;

public class IdATMValidator implements ConstraintValidator<IdATM, Integer>
{
    private int min;
    private int max;
    private boolean existe;
    private boolean esAtmInstitucional;

    private @Autowired IAtmService atmService;

    @Override
    public void initialize(IdATM anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
        this.esAtmInstitucional = anotacion.esAtmInstitucional();
    }

    @Override
    public boolean isValid(Integer idATM, ConstraintValidatorContext context)
    {
        if (idATM == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ATM.idATM}", context);
            return false;
        }
        if (idATM < min || idATM > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.ATM.idATM}", context);
            return false;
        }
        boolean existeATM = atmService.existeATM(idATM, esAtmInstitucional);
        return existe ? existeATM : !existeATM;
    }
}