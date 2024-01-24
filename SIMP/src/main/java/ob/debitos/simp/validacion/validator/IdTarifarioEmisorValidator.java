package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.tarifario.TarifarioEmisor;
import ob.debitos.simp.service.ITarifarioEmisorService;
import ob.debitos.simp.utilitario.Conversor;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdTarifarioEmisor;

public class IdTarifarioEmisorValidator implements ConstraintValidator<IdTarifarioEmisor, Object>
{
    private boolean existe;
    private String campoNivel;
    private String campoTipoTarifa;
    private String campoCodigoGrupoBin;
    private String campoTransaccion;

    private @Autowired ITarifarioEmisorService tarifarioEmisorService;

    @Override
    public void initialize(IdTarifarioEmisor anotacion)
    {
        this.existe = anotacion.existe();
        this.campoNivel = anotacion.campoNivel();
        this.campoTipoTarifa = anotacion.campoTipoTarifa();
        this.campoCodigoGrupoBin = anotacion.campoCodigoGrupoBin();
        this.campoTransaccion = anotacion.campoTransaccion();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            Integer idTipoTarifa = Integer
                    .parseInt(BeanUtils.getProperty(dto, this.campoTipoTarifa));
            Integer nivel = Integer.parseInt(BeanUtils.getProperty(dto, this.campoNivel));
            String idGrupoBin = BeanUtils.getProperty(dto, this.campoCodigoGrupoBin);
            Integer transaccion = Conversor
                    .integerValueOf(BeanUtils.getProperty(dto, this.campoTransaccion));

            TarifarioEmisor tarifarioEmisor = TarifarioEmisor.builder().nivel(nivel)
                    .codigoTipoTarifa(idTipoTarifa).codigoGrupoBin(idGrupoBin)
                    .transaccion(transaccion).build();

            List<TarifarioEmisor> tarifariosEmisor = tarifarioEmisorService
                    .buscarPorCodigo(tarifarioEmisor);

            if (existe && tarifariosEmisor.isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.TarifarioEmisor.codigo}", this.campoTipoTarifa, context);
                return false;
            }
            if (!existe && !tarifariosEmisor.isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.TarifarioEmisor.codigo}", this.campoTipoTarifa, context);
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