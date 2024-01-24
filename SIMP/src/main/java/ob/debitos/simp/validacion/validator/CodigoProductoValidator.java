package ob.debitos.simp.validacion.validator;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.mantenimiento.ParametroGeneral;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IProductoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoProducto;

public class CodigoProductoValidator
        implements ConstraintValidator<CodigoProducto, String>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IProductoService productoService;
    private @Autowired IParametroGeneralService parametroGeneralService;

    @Override
    public void initialize(CodigoProducto anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(String codigoProducto,
            ConstraintValidatorContext context)
    {
        List<ParametroGeneral> parametroGeneral = parametroGeneralService
                .buscarTodos();

        if (codigoProducto == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotNull.Producto.codigoProducto}", context);
            return false;
        }

        if (codigoProducto.trim().isEmpty())
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{NotBlank.Producto.codigoProducto}", context);
            return false;
        }
        
        if (!codigoProducto.matches(Regex.ALFANUMERICO))
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Pattern.Producto.codigoProducto}",
                    context);
            return false;
        }

        if (codigoProducto.length() < min || codigoProducto.length() > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "{Range.Producto.codigoProducto}", context);
            return false;
        }

        String codigoInicial = Integer
                .toString(parametroGeneral.get(0).getCodigoInstitucion());
        while (codigoInicial.length() < 3)
        {
            codigoInicial = "0" + codigoInicial;
        }

        if (!codigoProducto.startsWith(codigoInicial))
        {
            ValidatorUtil.addCustomMessageWithTemplate(
                    "El C\u00F3digo Producto debe iniciar con " + codigoInicial,
                    context);
            return false;
        }

        boolean existeCodigoProducto = productoService
                .existeProducto(codigoProducto);
        return existe ? existeCodigoProducto : !existeCodigoProducto;
    }

}
