package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IPerfilService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdPerfil;

public class IdPerfiValidator implements ConstraintValidator<IdPerfil, String>
{
    private int min;
    private int max;
    private boolean existe;
    private @Autowired IPerfilService perfilService;

    @Override
    public void initialize(IdPerfil anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String idPerfil, ConstraintValidatorContext context)
    {
        if (idPerfil == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Perfil.idPerfil}", context);
            return false;
        }
        if (idPerfil.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Perfil.idPerfil}", context);
            return false;
        }
        if (!idPerfil.matches(Regex.SOLO_LETRAS_A_a))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Perfil.idPerfil}", context);
            return false;
        }
        if (idPerfil.length() < this.min || idPerfil.length() > this.max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Perfil.idPerfil}", context);
            return false;
        }
        boolean existePerfil = this.perfilService.existePerfil(idPerfil);
        return (this.existe ? existePerfil : !existePerfil);
    }
}