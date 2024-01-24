package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IPoliticaSeguridadService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.Contrasenia;

public class ContraseniaValidator implements ConstraintValidator<Contrasenia, String>
{
    private int max;
    private boolean permiteContraseniaDefault;

    private @Autowired IPoliticaSeguridadService politicaSeguridadService;

    @Override
    public void initialize(Contrasenia anotacion)
    {
        this.max = anotacion.max();
        this.permiteContraseniaDefault = anotacion.permiteContraseniaDefault();
    }

    @Override
    public boolean isValid(String contrasenia, ConstraintValidatorContext context)
    {
        boolean complejidadContrasenia = this.politicaSeguridadService
                .buscarComplejidadContrasenia();
        int longitudMinimaContrasenia = this.politicaSeguridadService
                .buscarLongitudMinimaContrasenia();
        if (contrasenia == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Usuario.contrasenia}", context);
            return false;
        }
        if (this.permiteContraseniaDefault
                && contrasenia.equals(ConstantesGenerales.CONTRASENIA_DEFAULT))
        {
            /*
             * Cuando el usuario no desea cambiar la contrasenia, se inicializa
             * con '-1', no requiere validación.
             */
            return true;
        }
        if (contrasenia.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.Usuario.contrasenia}", context);
            return false;
        }
        if (contrasenia.length() < longitudMinimaContrasenia || contrasenia.length() > this.max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    String.format(ConstantesExcepciones.LENGTH_CONTRASENIA,
                            longitudMinimaContrasenia, this.max),
                    context);
            return false;
        }
        if (complejidadContrasenia && !contrasenia.matches(Regex.SEGURIDAD_PASS))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{PatternComplejidad.Usuario.contrasenia}",
                    context);
            return false;
        }
        return true;
    }
}