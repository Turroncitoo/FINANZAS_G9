package ob.debitos.simp.controller.excepcion;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.microsoft.sqlserver.jdbc.SQLServerException;

import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.service.excepcion.EscenarioException;
import ob.debitos.simp.service.excepcion.ListaVaciaException;
import ob.debitos.simp.service.excepcion.NoDisponibleException;
import ob.debitos.simp.service.excepcion.OrdenEjecucionException;
import ob.debitos.simp.service.excepcion.ValorEncontradoException;
import ob.debitos.simp.service.excepcion.ValorNoEncontradoException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;

@ControllerAdvice(annotations = Vista.class)
public class ExceptionController
{
    private @Autowired Logger logger;

    @ExceptionHandler(SQLServerException.class)
    public String catchConexionException(SQLServerException ex, RedirectAttributes redirect)
    {
        logger.error("ERROR SQL SERVER " + ex.getErrorCode(), ex);
        redirect.addFlashAttribute("mensaje", ex.getMessage());
        return "redirect:/409";
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public String catchAccessDeniedException(Exception ex, RedirectAttributes redirect)
    {
        logger.error(ex.getMessage(), ex);
        return "redirect:/403";
    
    }

    @ExceptionHandler(BadRequestException.class)
    public String catchBadRequestException(BadRequestException ex)
    {
        logger.error(ex.getMessage(), ex);
        return "redirect:/400";
    }

    @ExceptionHandler({ EscenarioException.class, OrdenEjecucionException.class,
            EjecucionManualException.class, ListaVaciaException.class,
            ValorNoEncontradoException.class, ValorEncontradoException.class })
    public String catchListaVaciaException(Exception ex, RedirectAttributes redirect)
    {
        logger.error(ex.getMessage(), ex);
        redirect.addFlashAttribute("mensaje", ex.getMessage());
        return "redirect:/409";
    }

    @ExceptionHandler(Exception.class)
    public String catchExcepcion(Exception ex, RedirectAttributes redirect)
    {
        logger.error(ex.getMessage(), ex);
        redirect.addFlashAttribute("mensaje", ConstantesExcepciones.ERROR_DESCONOCIDO);
        return "redirect:/500";
    }
    
    @ExceptionHandler(NoDisponibleException.class)
    public String catchExcepcionNoDisponible(Exception ex, RedirectAttributes redirect)
    {
        logger.error(ex.getMessage(), ex);
        redirect.addFlashAttribute("mensaje", ConstantesExcepciones.ERROR_DESCONOCIDO);
        return "redirect:/503";
    }
}