package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoUsuario;

public class CodigoUsuarioValidator implements ConstraintValidator<CodigoUsuario, String>
{
    private int min;
    private int max;
    private boolean existe;
    private @Autowired IUsuarioService usuarioService;

    @Override
    public void initialize(CodigoUsuario anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String idUsuario, ConstraintValidatorContext context)
    {
        if (idUsuario == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Usuario.idUsusario}", context);
            return false;
        }
        if (idUsuario.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Usuario.idUsusario}", context);
            return false;
        }
        if (!idUsuario.matches(Regex.ALFANUMERICO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Usuario.idUsusario}", context);
            return false;
        }
        if (idUsuario.length() < min || idUsuario.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.Usuario.idUsusario}", context);
            return false;
        }
        boolean existeUsuario = usuarioService.existeUsuario(idUsuario);
        return (existe ? existeUsuario : !existeUsuario);
    }
}