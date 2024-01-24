package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.tarifario.TarifarioAdq;
import ob.debitos.simp.service.ITarifarioAdqService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdTarifarioAdq;

public class IdTarifarioAdqValidator implements ConstraintValidator<IdTarifarioAdq, Object>
{
    private boolean existe;
    private String campoNivel;
    private String campoTipoTarifa;

    private @Autowired ITarifarioAdqService tarifariosAdqService;

    @Override
    public void initialize(IdTarifarioAdq anotacion)
    {
        this.existe = anotacion.existe();
        this.campoNivel = anotacion.campoNivel();
        this.campoTipoTarifa = anotacion.campoTipoTarifa();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            Integer idTipoTarifa = Integer
                    .parseInt(BeanUtils.getProperty(dto, this.campoTipoTarifa));
            Integer nivel = Integer.parseInt(BeanUtils.getProperty(dto, this.campoNivel));

            TarifarioAdq tarifarioAdq = TarifarioAdq.builder().nivel(nivel)
                    .codigoTipoTarifa(idTipoTarifa).build();

            List<TarifarioAdq> tarifariosAdq = tarifariosAdqService.buscarPorCodigo(tarifarioAdq);

            if (existe && tarifariosAdq.isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.TarifarioAdq.codigo}", this.campoTipoTarifa, context);
                return false;
            }
            if (!existe && !tarifariosAdq.isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.TarifarioAdq.codigo}", this.campoTipoTarifa, context);
                return false;
            }
            return true;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}