package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.HoraProgramada;

public class HoraProgramadaValidator implements ConstraintValidator<HoraProgramada, Object>
{
    private String campoCodigoGrupo;
    private String campoHoraProgramada;

    private @Autowired IProcesoAutomaticoService procesoAutomaticoService;

    @Override
    public void initialize(HoraProgramada anotacion)
    {
        this.campoCodigoGrupo = anotacion.campoCodigoGrupo();
        this.campoHoraProgramada = anotacion.campoHoraProgramada();
    }

    @Override
    public boolean isValid(Object procesoAutomatico, ConstraintValidatorContext context)
    {
        try
        {
            String codigoGrupo = BeanUtils.getProperty(procesoAutomatico, this.campoCodigoGrupo);
            String horaProgramada = BeanUtils.getProperty(procesoAutomatico,
                    this.campoHoraProgramada);

            if (horaProgramada == null)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotNull.ProcesoAutomatico.horaProgramada}", this.campoHoraProgramada,
                        context);
                return false;
            }
            if (horaProgramada.trim().isEmpty())
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NotBlank.ProcesoAutomatico.horaProgramada}", this.campoHoraProgramada,
                        context);
                return false;
            }
            if (!horaProgramada.matches(Regex.FORMATO_HHMM))
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Pattern.ProcesoAutomatico.horaProgramada}", this.campoHoraProgramada,
                        context);
                return false;
            }
            boolean existeProcesoAutomaticoPorHoraProgramadaRepetido = procesoAutomaticoService
                    .existeProcesoAutomaticoPorHoraProgramadaRepetido(codigoGrupo, horaProgramada);
            if (existeProcesoAutomaticoPorHoraProgramadaRepetido)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.ProcesoAutomatico.horaProgramada}", this.campoHoraProgramada,
                        context);
                return false;
            }
            return true;
        } catch (Exception e)
        {
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoHoraProgramada, context);
            return false;
        }
    }
}