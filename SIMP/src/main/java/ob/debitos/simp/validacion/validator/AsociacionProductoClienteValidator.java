package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IProductoClienteService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.AsociacionProductoCliente;

public class AsociacionProductoClienteValidator implements ConstraintValidator<AsociacionProductoCliente, Object>
{
    private boolean existe;
    private String campoIdCliente;
    private String campoCodigoProducto;
    private String campoIdEmpresa;

    private @Autowired IProductoClienteService productoClienteService;

    @Override
    public void initialize(AsociacionProductoCliente anotacion)
    {
        this.existe = anotacion.existe();
        this.campoCodigoProducto = anotacion.campoCodigoProducto();
        this.campoIdCliente = anotacion.campoIdCliente();
        this.campoIdEmpresa = anotacion.campoIdEmpresa();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String idCliente = BeanUtils.getProperty(dto, this.campoIdCliente);
            String codigoProducto = BeanUtils.getProperty(dto, this.campoCodigoProducto);
            String idEmpresa = BeanUtils.getProperty(dto, this.campoIdEmpresa);
            boolean existeAsociacionProductoCliente = productoClienteService.existeAsociacionProductoCliente(codigoProducto, idCliente, idEmpresa);

            if (existe && !existeAsociacionProductoCliente)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty("{NoExisteAsociacion.ProductoCliente.codCliente}", this.campoCodigoProducto, context);
                return false;
            }

            if (!existe && existeAsociacionProductoCliente)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty("{ExisteAsociacion.ProductoCliente.idCliente}", this.campoCodigoProducto, context);
                return false;
            }

            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}", this.campoCodigoProducto, context);
            return false;
        }
    }

}
