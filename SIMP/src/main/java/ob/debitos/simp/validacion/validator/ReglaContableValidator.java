package ob.debitos.simp.validacion.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IReglaContableService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.existeReglaContable;

public class ReglaContableValidator implements ConstraintValidator<existeReglaContable, Object>
{
	private boolean existe;
	private String campoIdComercio;
	private String campoIdCliente;

	private @Autowired IReglaContableService reglaContableService;

	@Override
	public void initialize(existeReglaContable reglaContable)
	{
		this.existe = reglaContable.existe();
		this.campoIdComercio = reglaContable.campoIdComercio();
		this.campoIdCliente = reglaContable.campoIdCliente();
	}

	@Override
	public boolean isValid(Object dto, ConstraintValidatorContext context)
	{
		try
		{
			String idComercio = BeanUtils.getProperty(dto, campoIdComercio);
			String idCliente = BeanUtils.getProperty(dto, campoIdCliente);
			boolean existeReglaContable = this.reglaContableService.existeReglaContable(idComercio, idCliente);
			if (existe && !existeReglaContable)
			{
				ValidatorUtil.addCustomMessageWithTemplateWithProperty("{NoExiste.ReglaContable.existeReglaContable}",
						this.campoIdComercio, context);
			}
			if (!existe && existeReglaContable)
			{
				ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Existe.ReglaContable.existeReglaContable}",
						this.campoIdComercio, context);
			}
			return true;
		} catch (Exception e)
		{
			ValidatorUtil.addCustomMessageWithTemplateWithProperty("{Excepcion.DTO.propiedad}", this.campoIdComercio,
					context);
			return false;
		}
	}

}
