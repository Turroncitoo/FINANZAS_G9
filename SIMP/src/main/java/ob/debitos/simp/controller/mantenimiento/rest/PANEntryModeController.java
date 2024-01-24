package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.PANEntryMode;
import ob.debitos.simp.service.IPANEntryModeService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

/**
 * Recibe las peticiones del usuario relacionadas al mantenimiento de modo de
 * entrada de tarjeta.
 * <p>
 * Especificamente recibe las peticiones de:
 * <ul>
 * <li>Búsqueda de modos de entrada de tarjeta.
 * <li>Registro de modo de entrada de tarjeta.
 * <li>Actualización de modo de entrada de tarjeta.
 * <li>Eliminación de modo de entrada de tarjeta.
 * </ul>
 * 
 * @author Fernando Fonseca
 * @see IPANEntryModeService
 * @see PANEntryMode
 */
@Audit(tipo = Tipo.PAN_ENTRY_MODE, datos = Dato.PAN_ENTRY_MODE)
@RequestMapping("/panEntryMode")
public @RestController class PANEntryModeController
{

    private @Autowired IPANEntryModeService panEntryModeService;

    /**
     * Recibe las peticiones para obtener todos los modos de entrada de tarjeta
     * registrados.
     * 
     * @return lista de {@link PANEntryMode}
     */
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PANENTRYMODE','2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<PANEntryMode> buscarTodos()
    {
        return this.panEntryModeService.buscarTodos();
    }

    /**
     * Recibe las peticiones de registro de modo de entrada de tarjeta.
     * 
     * @param panEntryMode
     *            el modo de entrada a registrar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>panEntryMode</b> anotado con
     *            {@literal @Validated}
     * @return el {@link PANEntryMode} registrado
     */
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PANENTRYMODE', '1')")
    @PostMapping
    public List<PANEntryMode> registrarPANEntryMode(@Validated({ Default.class, IRegistro.class }) @RequestBody PANEntryMode panEntryMode, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.panEntryModeService.registrarPANEntryMode(panEntryMode);
        return this.panEntryModeService.buscarPorCodigo(panEntryMode.getCodigo());
    }

    /**
     * Recibe las peticiones de actualización de modo de entrada de tarjeta.
     * 
     * @param panEntryMode
     *            el modo de entrada a actualizar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>panEntryMode</b> anotado con
     *            {@literal @Validated}
     * @return el {@link PANEntryMode} actualizado
     */
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PANENTRYMODE', '3')")
    @PutMapping
    public List<PANEntryMode> actualizarPANEntryMode(@Validated({ Default.class, IActualizacion.class }) @RequestBody PANEntryMode panEntryMode, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.panEntryModeService.actualizarPANEntryMode(panEntryMode);
        return this.panEntryModeService.buscarPorCodigo(panEntryMode.getCodigo());
    }

    /**
     * Recibe la petición de eliminación de modo de entrada de tarjeta.
     * 
     * @param panEntryMode
     *            el modo de entrada a eliminar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>panEntryMode</b> anotado con
     *            {@literal @Validated}
     * @return el mensaje {@link ConstantesGenerales#ELIMINACION_EXITOSA}
     */
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PANENTRYMODE', '4')")
    @DeleteMapping
    public String eliminarPANEntryMode(@RequestBody PANEntryMode panEntryMode, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.panEntryModeService.eliminarPANEntryMode(panEntryMode);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }

}
