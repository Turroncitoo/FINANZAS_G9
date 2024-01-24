package ob.debitos.simp.aspecto;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.model.seguridad.Auditoria;
import ob.debitos.simp.service.IAuditoriaService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.CustomSpringExpressionLanguageParserUtil;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Aspect
@Component
public class AuditableAspecto
{
    private @Autowired Logger logger;
    private @Autowired IAuditoriaService auditoriaService;

    @Around("@annotation(audit)")
    public Object registrarAuditoria(ProceedingJoinPoint proceedingJoinPoint, Audit audit)
            throws Throwable
    {
        int exito = ConstantesGenerales.ESTADO_EXITO;
        Tipo tipo = null;
        String datos = "";
        Comentario comentario = null;
        String comentarioParaAuditoria = "";
        boolean tieneClaseAnotacion = false;
        Accion accion = null;
        String direccionIp = SecurityContextFacade.obtenerIpCliente();
        String nombreUsuario = SecurityContextFacade.obtenerNombreUsuario();

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Class<?> clazz = signature.getDeclaringType();
        tieneClaseAnotacion = this.tieneAnotacion(clazz);

        tipo = this.obtenerTipo(audit.tipo(), clazz, tieneClaseAnotacion);
        comentario = this.obtenerComentario(audit.comentario(), clazz, tieneClaseAnotacion);
        accion = this.obtenerAccion(audit.accion(), clazz, tieneClaseAnotacion);

        if (accion == Accion.Registro || accion == Accion.Actualizacion
                || accion == Accion.Eliminacion || accion == Accion.Ejecucion)
        {
            datos = this.obtenerDatos(audit.datos(), clazz, tieneClaseAnotacion);
            String datosParaAuditar = CustomSpringExpressionLanguageParserUtil.getDynamicValue(
                    signature.getParameterNames(), proceedingJoinPoint.getArgs(), datos);
            comentarioParaAuditoria = String.format(comentario.toString(), tipo.toString(),
                    datosParaAuditar);
        } else
        {
            comentarioParaAuditoria = String.format(comentario.toString(), tipo.getDescripcion());
        }
        Auditoria auditoria = Auditoria.builder().idRecurso(tipo.getIdRecurso())
                .idAccion(accion.toString()).comentario(comentarioParaAuditoria)
                .direccionIp(direccionIp).nombreUsuario(nombreUsuario).build();
        try
        {
            return proceedingJoinPoint.proceed();
        } catch (Exception ex)
        {
            exito = ConstantesGenerales.ESTADO_ERROR;
            auditoria.setLogError(ExcepcionUtil.obtenerMensajeExcepcionRaiz(ex));
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally
        {
            auditoria.setExito(exito);
            auditoriaService.registrarAuditoria(auditoria);
        }
    }

    public boolean tieneAnotacion(Class<?> clazz)
    {
        return clazz.isAnnotationPresent(Audit.class);
    }

    public Tipo obtenerTipo(Tipo tipo, Class<?> clazz, boolean tieneClaseAnotacion)
    {
        if (tipo == Tipo.NINGUNO && tieneClaseAnotacion)
        {
            tipo = clazz.getAnnotation(Audit.class).tipo();
        }
        return tipo;
    }

    public String obtenerDatos(Dato dato, Class<?> clazz, boolean tieneClaseAnotacion)
    {
        if (dato == Dato.NINGUNO && tieneClaseAnotacion)
        {
            dato = clazz.getAnnotation(Audit.class).datos();

        }
        return dato.toString();
    }

    public Comentario obtenerComentario(Comentario comentario, Class<?> clazz,
            boolean tieneClaseAnotacion)
    {
        if (comentario == Comentario.Ninguno && tieneClaseAnotacion)
        {
            comentario = clazz.getAnnotation(Audit.class).comentario();
        }
        return comentario;
    }

    public Accion obtenerAccion(Accion accion, Class<?> clazz, boolean tieneClaseAnotacion)
    {
        if (accion == Accion.Ninguna && tieneClaseAnotacion)
        {
            accion = clazz.getAnnotation(Audit.class).accion();
        }
        return accion;
    }
}