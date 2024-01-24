package ob.debitos.simp.aspecto;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ob.debitos.simp.aspecto.anotacion.NoIdentificable;
import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.configuracion.security.SecurityContextFacade;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.utilitario.StringsUtils;

@Aspect
@Component
public class TruncableAspecto
{
    private @Autowired IUsuarioService usuarioService;

    @AfterReturning(value = "@annotation(ob.debitos.simp.aspecto.anotacion.Truncable)", returning = "valorRetornado")
    public void truncar(JoinPoint joinPoint, Object valorRetornado) throws IllegalAccessException
    {
        String idUsuario = SecurityContextFacade.obtenerNombreUsuario();
        boolean puedeVisualizarTarjetas = this.usuarioService.puedeVisualizarPAN(idUsuario);
        if (!puedeVisualizarTarjetas)
        {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Truncable truncable = signature.getMethod().getAnnotation(Truncable.class);
            String campoAnidado = truncable.campoAnidado();
            Class<?> claseRetornada = obtenerClaseRetornada(signature.getDeclaringType(),
                    truncable);
            Field[] camposAnotados = FieldUtils.getFieldsWithAnnotation(claseRetornada,
                    NoIdentificable.class);
            if (valorRetornado instanceof List<?>)
            {
                boolean esCampoAnidado = campoAnidado != null && !campoAnidado.isEmpty();
                @SuppressWarnings("unchecked")
                List<Object> listaRetornada = (esCampoAnidado
                        ? obtenerListaAnidado(valorRetornado, campoAnidado)
                        : (List<Object>) valorRetornado);

                for (Object objeto : listaRetornada)
                {
                    ejecutarTruncamiento(camposAnotados, objeto);
                }
            } else
            {
                if (campoAnidado != null && !campoAnidado.isEmpty())
                {
                    valorRetornado = FieldUtils.readField(valorRetornado, campoAnidado, true);
                }
                ejecutarTruncamiento(camposAnotados, valorRetornado);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Object> obtenerListaAnidado(Object valorRetornado, String campoAnidado)
            throws IllegalAccessException
    {
        List<Object> listaRetornada = (List<Object>) valorRetornado;
        List<Object> listaSubCampo = new ArrayList<>();
        for (Object objeto : listaRetornada)
        {
            objeto = FieldUtils.readField(objeto, campoAnidado, true);
            if (objeto instanceof List<?>)
            {
                List<Object> objetosAnidado = (List<Object>) objeto;
                listaSubCampo.addAll(objetosAnidado);
            } else
            {
                listaSubCampo.add(objeto);
            }
        }
        return listaSubCampo;
    }

    public void ejecutarTruncamiento(Field[] camposAnotados, Object objeto)
            throws IllegalAccessException
    {
        for (Field campoNoIdentificable : camposAnotados)
        {
        	String numeroTruncado = "";
            campoNoIdentificable.setAccessible(true);
            NoIdentificable noIdentificable = campoNoIdentificable
                    .getAnnotation(NoIdentificable.class);
            Object campo = campoNoIdentificable.get(objeto);
            if(campo != null){
                String campoATruncar = String.valueOf(campo);
                numeroTruncado = StringsUtils.maskCCNumber(campoATruncar,
                        noIdentificable.primerosCaracteres(), noIdentificable.ultimosCaracteres());	
            }
            FieldUtils.writeField(objeto, noIdentificable.campo(), numeroTruncado, true);
        }
    }

    public Class<?> obtenerClaseRetornada(Class<?> clazz, Truncable truncable)
    {
        Class<?> claseRetornada = truncable.clase();
        if (tieneAnotacion(clazz) && claseRetornada.isAssignableFrom(Void.class))
        {
            claseRetornada = clazz.getAnnotation(Truncable.class).clase();
        }
        return claseRetornada;
    }

    public boolean tieneAnotacion(Class<?> clazz)
    {
        return clazz.isAnnotationPresent(Truncable.class);
    }
}