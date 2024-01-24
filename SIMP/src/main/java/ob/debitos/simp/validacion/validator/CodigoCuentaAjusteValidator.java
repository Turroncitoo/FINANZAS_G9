package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.ICuentaAjusteService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.CodigoCuentaAjuste;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;

/**
 * Evalúa la existencia de la cuenta de ajuste.
 * 
 * @author Hanz Llanto
 */
public class CodigoCuentaAjusteValidator implements ConstraintValidator<CodigoCuentaAjuste, Object>
{
    private boolean existe;
    private Class<?>[] groups;
    private String campoRolTransaccion;
    private String campoTipoMovimiento;
    private String campoMonedaCompensacion;
    private String campoRegistroContable;
    private String campoNuevoRolTransaccion;
    private String campoNuevoTipoMovimiento;
    private String campoNuevoMonedaCompensacion;
    private String campoNuevoRegistroContable;
    private @Autowired Logger logger;
    private @Autowired ICuentaAjusteService cuentaAjusteService;

    @Override
    public void initialize(CodigoCuentaAjuste arg)
    {
        this.existe = arg.existe();
        this.campoRolTransaccion = arg.campoRolTransaccion();
        this.campoTipoMovimiento = arg.campoTipoMovimiento();
        this.campoRegistroContable = arg.campoRegistroContable();
        this.campoMonedaCompensacion = arg.campoMonedaCompensacion();
        this.campoNuevoMonedaCompensacion = arg.campoNuevoMonedaCompensacion();
        this.campoNuevoRegistroContable = arg.campoNuevoRegistroContable();
        this.campoNuevoRolTransaccion = arg.campoNuevoRolTransaccion();
        this.campoNuevoTipoMovimiento = arg.campoNuevoTipoMovimiento();
        this.groups = arg.groups();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context)
    {
        try
        {
            Integer rolTransaccion = Integer.parseInt(Optional
                    .ofNullable(BeanUtils.getProperty(obj, this.campoRolTransaccion)).orElse("-1"));
            Integer monedaCompensacion = Integer.parseInt(
                    Optional.ofNullable(BeanUtils.getProperty(obj, this.campoMonedaCompensacion))
                            .orElse("-1"));
            Integer tipoMovimiento = Integer.parseInt(Optional
                    .ofNullable(BeanUtils.getProperty(obj, this.campoTipoMovimiento)).orElse("-1"));
            String registroContable = BeanUtils.getProperty(obj, this.campoRegistroContable);

            Integer nuevoRolTransaccion = Integer.parseInt(
                    Optional.ofNullable(BeanUtils.getProperty(obj, this.campoNuevoRolTransaccion))
                            .orElse("-1"));
            Integer nuevoMonedaCompensacion = Integer.parseInt(Optional
                    .ofNullable(BeanUtils.getProperty(obj, this.campoNuevoMonedaCompensacion))
                    .orElse("-1"));
            Integer nuevoTipoMovimiento = Integer.parseInt(
                    Optional.ofNullable(BeanUtils.getProperty(obj, this.campoNuevoTipoMovimiento))
                            .orElse("-1"));
            String nuevoRegistroContable = BeanUtils.getProperty(obj,
                    this.campoNuevoRegistroContable);

            if (rolTransaccion == -1 || monedaCompensacion == -1 || tipoMovimiento == -1
                    || registroContable.trim().equals(""))
            {
                ValidatorUtil.addCustomMessageWithTemplate("{NotNull.CuentaAjuste.campos}",
                        context);
                return false;
            }
            boolean existeCuentaAjuste = this.cuentaAjusteService.existeCuentaAjuste(rolTransaccion,
                    monedaCompensacion, tipoMovimiento, registroContable);

            if (existe && !existeCuentaAjuste)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.CuentaAjuste.rolTransaccion}", this.campoRolTransaccion,
                        context);
                return false;
            }
            if (!existe && existeCuentaAjuste)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.CuentaAjuste.rolTransaccion}", this.campoRolTransaccion, context);
                return false;
            }
            if (this.groups[0] == IActualizacion.class
                    && (!rolTransaccion.equals(nuevoRolTransaccion)
                            || !monedaCompensacion.equals(nuevoMonedaCompensacion)
                            || !tipoMovimiento.equals(nuevoTipoMovimiento)
                            || !registroContable.equals(nuevoRegistroContable)))
            {
                boolean existeNuevoCuentaAjuste = this.cuentaAjusteService.existeCuentaAjuste(
                        nuevoRolTransaccion, nuevoMonedaCompensacion, nuevoTipoMovimiento,
                        nuevoRegistroContable);
                if (existeNuevoCuentaAjuste)
                {
                    ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                            "{Existe.CuentaAjuste.rolTransaccion}", this.campoNuevoRolTransaccion,
                            context);
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException | IllegalAccessException | InvocationTargetException
                | NoSuchMethodException e)
        {
            logger.error(e.getMessage(), e);
            ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}",
                    this.campoRolTransaccion, context);
            return false;
        }
    }
}