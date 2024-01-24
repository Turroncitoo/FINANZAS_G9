package ob.debitos.simp.validacion.validator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.OrdenEjecucion;

public class OrdenEjecucionValidator implements ConstraintValidator<OrdenEjecucion, Object>
{
    private int min;
    private int max;
    private String campoCodigoGrupo;
    private String campoOrdenEjecucion;

    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    @Override
    public void initialize(OrdenEjecucion anotacion)
    {
        this.min = anotacion.min();
        this.max = anotacion.max();
        this.campoCodigoGrupo = anotacion.campoCodigoGrupo();
        this.campoOrdenEjecucion = anotacion.campoOrdenEjecucion();
    }

    @Override
    public boolean isValid(Object procesoAutomatico, ConstraintValidatorContext context)
    {
        try
        {
            String codigoGrupo = BeanUtils.getProperty(procesoAutomatico, this.campoCodigoGrupo);
            Integer ordenEjecucion = Integer.parseInt(Optional
                    .ofNullable(BeanUtils.getProperty(procesoAutomatico, this.campoOrdenEjecucion))
                    .orElse("-1"));

            if (ordenEjecucion == -1)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.ProcesoAutomatico.ordenEjecucion}", this.campoOrdenEjecucion,
                        context);
                return false;
            }
            if (ordenEjecucion < min || ordenEjecucion > max)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Range.ProcesoAutomatico.ordenEjecucion}", this.campoOrdenEjecucion,
                        context);
                return false;
            }
            boolean existeProcesoAutomaticoPorOrdenEjecucionRepetido = procesoAutomaticoService
                    .existeProcesoAutomaticoPorOrdenEjecucionRepetido(codigoGrupo, ordenEjecucion);
            if (existeProcesoAutomaticoPorOrdenEjecucionRepetido)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.ProcesoAutomatico.ordenEjecucion}", this.campoOrdenEjecucion,
                        context);
                return false;
            }
            return true;
        } catch (Exception e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoOrdenEjecucion, context);
            return false;
        }
    }
}