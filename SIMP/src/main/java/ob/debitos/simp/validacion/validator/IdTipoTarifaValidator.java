package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.tarifario.TipoTarifa;
import ob.debitos.simp.service.ITipoTarifaService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdTipoTarifa;

public class IdTipoTarifaValidator implements ConstraintValidator<IdTipoTarifa, Integer>
{
    private int max;
    private int min;
    private boolean existe;

    private @Autowired ITipoTarifaService tipoTarifaService;

    @Override
    public void initialize(IdTipoTarifa anotacion)
    {
        this.existe = anotacion.existe();
        this.max = anotacion.max();
        this.min = anotacion.min();
    }

    @Override
    public boolean isValid(Integer tipoTarifa, ConstraintValidatorContext context)
    {
        if (tipoTarifa == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.TipoTarifa.tipoTarifa}", context);
            return false;
        }
        if (tipoTarifa < min || tipoTarifa > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.TipoTarifa.tipoTarifa}", context);
            return false;
        }
        return (existe) ? !tipoTarifaService
                .buscarPorTipoTarifa(TipoTarifa.builder().tipoTarifa(tipoTarifa).build()).isEmpty()
                : tipoTarifaService
                        .buscarPorTipoTarifa(TipoTarifa.builder().tipoTarifa(tipoTarifa).build())
                        .isEmpty();
    }

}