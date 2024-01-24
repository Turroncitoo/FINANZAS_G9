package ob.debitos.simp.validacion.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.service.IProgramaService;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.validacion.IdPrograma;

public class IdProgramaValidator implements ConstraintValidator<IdPrograma, Object>
{
    private boolean existe;
    private String campoCodigoGrupo;
    private String campoCodigoPrograma;
    private String campoCodigoSubModulo;

    private @Autowired IProgramaService programaService;

    @Override
    public void initialize(IdPrograma anotacion)
    {
        this.existe = anotacion.existe();
        this.campoCodigoGrupo = anotacion.campoCodigoGrupo();
        this.campoCodigoPrograma = anotacion.campoCodigoPrograma();
        this.campoCodigoSubModulo = anotacion.campoCodigoSubModulo();
    }

    @Override
    public boolean isValid(Object dto, ConstraintValidatorContext context)
    {
        try
        {
            String codigoGrupo = BeanUtils.getProperty(dto, this.campoCodigoGrupo);
            String codigoPrograma = BeanUtils.getProperty(dto, this.campoCodigoPrograma);
            String codigoSubModulo = BeanUtils.getProperty(dto, this.campoCodigoSubModulo);
            boolean existePrograma = programaService.existeCodigoPrograma(codigoGrupo,
                    codigoPrograma, codigoSubModulo);
            if (existe && !existePrograma)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{NoExiste.Programa.codigoPrograma}", this.campoCodigoPrograma, context);
                return false;
            }
            if (!existe && existePrograma)
            {
                ValidatorUtil.addCustomMessageWithTemplateWithProperty(
                        "{Existe.Programa.codigoPrograma}", this.campoCodigoPrograma, context);
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