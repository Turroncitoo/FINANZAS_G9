package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ISubBinClienteService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.AsociacionSubBinCliente;

public class AsociacionSubBinClienteValidator
        implements ConstraintValidator<AsociacionSubBinCliente, Object>
{
    private boolean existe;
    private String campoCodigoBin;
    private String campoIdCliente;
    private String campoCodigoSubBin;
    private String campoIdEmpresa;

    private @Autowired ISubBinClienteService subBinClienteService;

    @Override
    public void initialize(AsociacionSubBinCliente anotacion)
    {
        this.existe = anotacion.existe();
        this.campoCodigoBin = anotacion.campoCodigoBin();
        this.campoCodigoSubBin = anotacion.campoCodigoSubBin();
        this.campoIdCliente = anotacion.campoIdCliente();
        this.campoIdEmpresa = anotacion.campoIdEmpresa();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoBIN = BeanUtils.getProperty(dto, this.campoCodigoBin);
            String idCliente = BeanUtils.getProperty(dto, this.campoIdCliente);
            String codigoSubBIN = BeanUtils.getProperty(dto, this.campoCodigoSubBin);
            String idEmpresa = BeanUtils.getProperty(dto, this.campoIdEmpresa);
            boolean existeAsociacionSubBinCliente = subBinClienteService
                    .existeAsociacionSubBinCliente(codigoBIN, codigoSubBIN, idCliente, idEmpresa);
            if (existe && !existeAsociacionSubBinCliente)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExisteAsociacion.SubBin.idCliente}", this.campoCodigoSubBin, context);
                return false;
            }
            if (!existe && existeAsociacionSubBinCliente)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{ExisteAsociacion.SubBin.idCliente}", this.campoCodigoSubBin, context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoCodigoSubBin, context);
            return false;
        }
    }
}