package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IEmpresaService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdEmpresa;

public class IdEmpresaValidator implements ConstraintValidator<IdEmpresa, String>
{
    private int max;
    private int min;
    private boolean existe;

    private @Autowired IEmpresaService empresaService;

    @Override
    public void initialize(IdEmpresa anotacion)
    {
        this.existe = anotacion.existe();
        this.min = anotacion.min();
        this.max = anotacion.max();
    }

    @Override
    public boolean isValid(String idEmpresa, ConstraintValidatorContext context)
    {
        if (idEmpresa == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Empresa.idEmpresa}", context);
            return false;
        }
        if (idEmpresa.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotBlank.Empresa.idEmpresa}", context);
            return false;
        }
        if (!idEmpresa.matches(Regex.ALFANUMERICO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Empresa.idEmpresa}", context);
            return false;
        }
        if (idEmpresa.length() > max || idEmpresa.length() < min)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.Empresa.idEmpresa}", context);
            return false;
        }
        boolean existeEmpresa = empresaService.existeEmpresa(idEmpresa);
        return existe ? existeEmpresa : !existeEmpresa;
    }
}