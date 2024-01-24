package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.tarifario.Escenario;
import ob.debitos.simp.service.IEscenarioService;
import ob.debitos.simp.utilitario.Conversor;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdEscenario;

public class IdEscenarioValidator implements ConstraintValidator<IdEscenario, Object>
{
    private boolean existe;
    private String campoCodigoMembresia;
    private String campoCodigoClaseServicio;
    private String campoCodigoClaseTransaccion;
    private String campoCodigoTransaccion;
    private String campoCodigoOrigen;

    private @Autowired IEscenarioService escenarioService;

    @Override
    public void initialize(IdEscenario anotacion)
    {
        this.existe = anotacion.existe();
        this.campoCodigoMembresia = anotacion.campoCodigoMembresia();
        this.campoCodigoClaseServicio = anotacion.campoCodigoClaseServicio();
        this.campoCodigoOrigen = anotacion.campoCodigoOrigen();
        this.campoCodigoClaseTransaccion = anotacion.campoCodigoClaseTransaccion();
        this.campoCodigoTransaccion = anotacion.campoCodigoTransaccion();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoMembresia = BeanUtils.getProperty(dto, this.campoCodigoMembresia);
            String codigoClaseServicio = BeanUtils.getProperty(dto, this.campoCodigoClaseServicio);
            Integer codigoClaseTxn = Integer
                    .parseInt(BeanUtils.getProperty(dto, this.campoCodigoClaseTransaccion));
            Integer codigoTxn = Conversor
                    .integerValueOf(BeanUtils.getProperty(dto, this.campoCodigoTransaccion));
            Integer codigoOrigen = Conversor
                    .integerValueOf(BeanUtils.getProperty(dto, this.campoCodigoOrigen));
            Escenario escenario = Escenario.builder().codigoMembresia(codigoMembresia)
                    .codigoClaseServicio(codigoClaseServicio).codigoClaseTransaccion(codigoClaseTxn)
                    .codigoTransaccion(codigoTxn).codigoOrigen(codigoOrigen).build();
            boolean existeEscenario = escenarioService.existeEscenario(escenario);
            if (existe && !existeEscenario)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Escenario.codigo}", this.campoCodigoMembresia, context);
                return false;
            }
            if (!existe && existeEscenario)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Existe.Escenario.codigo}",
                        this.campoCodigoMembresia, context);
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