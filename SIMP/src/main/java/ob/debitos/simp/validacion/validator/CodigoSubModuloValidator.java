package ob.debitos.simp.validacion.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.proceso.SubModulo;
import ob.debitos.simp.service.ISubModuloService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoSubModulo;

public class CodigoSubModuloValidator implements ConstraintValidator<CodigoSubModulo, String>
{
    private boolean existe;
    private int tamanio;

    private @Autowired ISubModuloService subModuloService;

    @Override
    public void initialize(CodigoSubModulo anotacion)
    {
        this.existe = anotacion.existe();
        this.tamanio = anotacion.tamanio();
    }

    @Override
    public boolean isValid(String codigoSubModulo, ConstraintValidatorContext context)
    {
        if (codigoSubModulo == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.SubModulo.codigo}", context);
            return false;
        }
        if (codigoSubModulo.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.SubModulo.codigo}", context);
            return false;
        }
        if (!codigoSubModulo.matches(Regex.SOLO_LETRAS_A_a))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.SubModulo.codigo}", context);
            return false;
        }
        if (codigoSubModulo.length() > tamanio)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Length.SubModulo.codigo}", context);
            return false;
        }

        SubModulo subModulo = SubModulo.builder().codigoSubModulo(codigoSubModulo).build();
        List<SubModulo> subModulos = subModuloService.buscarPorCodigo(subModulo);
        boolean existeSubModulo = !subModulos.isEmpty();
        return (existe) ? existeSubModulo : !existeSubModulo;
    }
}