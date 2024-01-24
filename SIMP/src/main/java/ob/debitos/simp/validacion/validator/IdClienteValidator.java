package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IClienteService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdCliente;

public class IdClienteValidator implements ConstraintValidator<IdCliente, Object>
{
    private int max;
    private int min;
    private boolean existe;
    private String campoIdEmpresa;
    private String campoIdCliente;

    private @Autowired IClienteService clienteService;

    @Override
    public void initialize(IdCliente anotacion)
    {
        this.existe = anotacion.existe();
        this.max = anotacion.max();
        this.min = anotacion.min();
        this.campoIdEmpresa = anotacion.campoIdEmpresa();
        this.campoIdCliente = anotacion.campoIdCliente();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String idCliente = BeanUtils.getProperty(dto, this.campoIdCliente);
            String idEmpresa = BeanUtils.getProperty(dto, this.campoIdEmpresa);
            if (idCliente == null)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.Cliente.idCliente}", this.campoIdCliente, context);
                return false;
            }
            if (idCliente.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.Cliente.idCliente}", this.campoIdCliente, context);
                return false;
            }
            if (idCliente.length() < min || idCliente.length() > max)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Length.Cliente.idCliente}",
                        this.campoIdCliente, context);
                return false;
            }
            boolean existeCliente = clienteService.existeCliente(idCliente, idEmpresa);
            if (existe && !existeCliente)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Cliente.idCliente}", this.campoIdCliente, context);
                return false;
            }
            if (!existe && existeCliente)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Existe.Cliente.idCliente}",
                        this.campoIdCliente, context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoIdCliente, context);
            return false;
        }
    }
}