package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IRptaConcilUbaService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdRptaConcilUba;

public class IdRptaConcilUbaValidator implements ConstraintValidator<IdRptaConcilUba, String>
{
    private boolean existe;
    private @Autowired IRptaConcilUbaService rptaConcilUbaService;

    private int min;
    private int max;
    private String pattern;

    @Override
    public void initialize(IdRptaConcilUba anotacion)
    {
        this.existe = anotacion.existe();
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.pattern = anotacion.pattern();
    }

    @Override
    public boolean isValid(String idRespuestaConcilUba, ConstraintValidatorContext context)
    {
        if (idRespuestaConcilUba == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.RptaConcilUba.idRespuestaConcilUba}", context);
            return false;
        }

        if(!idRespuestaConcilUba.matches(this.pattern))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.RptaConcilUba.idRespuestaConcilUba}",context);
            return false;
        }

        if (idRespuestaConcilUba.length() < this.min || idRespuestaConcilUba.length() > this.max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.RptaConcilUba.idRespuestaConcilUba}",
                    context);
            return false;
        }

        boolean existeRptaConcilUba = rptaConcilUbaService
                .existeRptaConcilUba(idRespuestaConcilUba);
        return existe ? existeRptaConcilUba : !existeRptaConcilUba;
    }
}