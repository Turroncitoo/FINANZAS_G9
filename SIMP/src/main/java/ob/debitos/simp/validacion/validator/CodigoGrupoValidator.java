package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoGrupo;

public class CodigoGrupoValidator implements ConstraintValidator<CodigoGrupo, String>
{
    private int max;
    private boolean existe;
    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    @Override
    public void initialize(CodigoGrupo anotacion)
    {
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoGrupo, ConstraintValidatorContext context)
    {
        if (codigoGrupo == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ProcesoAutomatico.codigoGrupo}",
                    context);
            return false;
        }
        if (codigoGrupo.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.ProcesoAutomatico.codigoGrupo}",
                    context);
            return false;
        }
        if (codigoGrupo.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.ProcesoAutomatico.codigoGrupo}",
                    context);
            return false;
        }
        boolean existeGrupo = procesoAutomaticoService.existeProcesoAutomatico(codigoGrupo);
        return (existe) ? existeGrupo : !existeGrupo;
    }
}