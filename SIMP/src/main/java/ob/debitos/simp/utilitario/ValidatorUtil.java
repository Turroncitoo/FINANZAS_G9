package ob.debitos.simp.utilitario;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidatorContext;

import org.springframework.validation.Errors;

import ob.debitos.simp.model.mantenimiento.ContabConceptoCuenta;
import ob.debitos.simp.model.parametro.MensajeValidacion;

public class ValidatorUtil
{

    public static void addCustomMessageWithTemplate(String template, ConstraintValidatorContext context)
    {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(template).addConstraintViolation();
    }

    public static void addCustomMessageWithTemplateWithProperty(String template, String property, ConstraintValidatorContext context)
    {
        if (property.isEmpty())
        {
            addCustomMessageWithTemplate(template, context);
        } else
        {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(template).addPropertyNode(property).addConstraintViolation();
        }
    }

    public static String obtenerCadenaErrores(Errors error)
    {
        return error.getAllErrors().stream().map(msg -> msg.getDefaultMessage()).distinct().collect(Collectors.joining("\n"));
    }

    public static List<MensajeValidacion> obtenerMensajeValidacionError(Errors error)
    {
        return error.getFieldErrors().stream().map(fieldError -> new MensajeValidacion(StringsUtils.obtenerCadenaDespuesDePunto(fieldError.getField()), fieldError.getDefaultMessage())).distinct().collect(Collectors.toList());
    }

    public static boolean tieneCodigoConceptoDuplicado(List<ContabConceptoCuenta> contabConceptosCuentas)
    {
        Set<Integer> codigosConceptoNoDuplicados = new HashSet<>();
        Set<Integer> codigosConceptoDuplicados = contabConceptosCuentas.stream().filter(a -> a.getIdConceptoComision() != 0 && !codigosConceptoNoDuplicados.add(a.getIdConceptoComision())).map(c -> c.getIdConceptoComision()).collect(Collectors.toSet());
        return codigosConceptoDuplicados.size() == 0 ? false : true;
    }

}
