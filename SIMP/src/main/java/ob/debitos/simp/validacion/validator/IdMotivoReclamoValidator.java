package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IMotivoReclamoService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdMotivoReclamo;

public class IdMotivoReclamoValidator implements ConstraintValidator<IdMotivoReclamo, Integer>
{
    private int min;
    private int max;
    private boolean existe;

    private @Autowired IMotivoReclamoService motivoReclamoService;

    @Override
    public void initialize(IdMotivoReclamo anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.existe = anotacion.existe();
    }

    @Override
    public boolean isValid(Integer idMotivo, ConstraintValidatorContext context)
    {
        if (idMotivo == null)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{NotNull.MotivoReclamo.idMotivo}", context);
            return false;
        }
        if (idMotivo < min || idMotivo > max)
        {
            ValidatorUtil.addCustomMessageWithTemplate("{Range.MotivoReclamo.idMotivo}", context);
            return false;
        }
        boolean existeMotivoReclamo = motivoReclamoService.existeMotivoReclamo(idMotivo);
        return existe ? existeMotivoReclamo : !existeMotivoReclamo;
    }
}