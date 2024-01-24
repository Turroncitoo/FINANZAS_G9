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
import ob.debitos.simp.model.mantenimiento.CuentaContable;
import ob.debitos.simp.service.ICuentaContableService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

/**
 * Recibe las peticiones del usuario relacionadas al mantenimiento de cuentas
 * contables.
 * <p>
 * Especificamente recibe las peticiones de:
 * <ul>
 * <li>Búsqueda de cuentas contables.
 * <li>Registro de cuentas contables.
 * <li>Actualización de cuentas contables.
 * <li>Eliminación de cuentas contables.
 * </ul>
 * 
 * @author Carla Ulloa
 * @see ICuentaContableService
 * @see CuentaContable
 */
@Audit(tipo = Tipo.CTA_CONTABLE, datos = Dato.CTA_CONTABLE)
@RequestMapping("/cuentaContable")
public @RestController class CuentaContableController
{
    private @Autowired ICuentaContableService cuentaContableService;

    /**
     * Recibe las peticiones para obtener todos las cuentas contables
     * registradas.
     * 
     * @return lista de {@link CuentaContable}
     */
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CTA', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CuentaContable> buscarTodos()
    {
        return this.cuentaContableService.buscarTodos();
    }

    /**
     * Recibe las peticiones de registro de cuentas contables.
     * 
     * @param cuentaContable
     *            la cuenta contable a registrar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>cuentaContable</b> anotado con
     *            {@literal @Validated}
     * @return la {@link CuentaContable} registrada
     */
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CTA', '1')")
    @PostMapping
    public List<CuentaContable> registrarCuentaContable(@Validated({ Default.class,
            IRegistro.class }) @RequestBody CuentaContable cuentaContable, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.cuentaContableService.registrarCuentaContable(cuentaContable);
        return this.cuentaContableService.buscarPorCodigo(cuentaContable.getIdGenerado());
    }

    /**
     * Recibe las peticiones de actualización de cuentas contables.
     * 
     * @param cuentaContable
     *            la cuenta contable a actualizar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>cuentaContable</b> anotado con
     *            {@literal @Validated}
     * @return la {@link CuentaContable} actualizada
     */
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CTA', '3')")
    @PutMapping
    
    public List<CuentaContable> actualizarCuentaContable(
            @Validated({ Default.class,
                    IActualizacion.class }) @RequestBody CuentaContable cuentaContable,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        
        this.cuentaContableService.actualizarCuentaContable(cuentaContable);
        return this.cuentaContableService.buscarPorCodigo(cuentaContable.getIdCuenta());
    }

    /**
     * Recibe las peticiones de eliminación de cuentas contables.
     * 
     * @param cuentaContable
     *            la cuenta contable a eliminar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>cuentaContable</b> anotado con
     *            {@literal @Validated}
     * @return el mensaje {@link ConstantesGenerales#ELIMINACION_EXITOSA}
     */
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CTA', '4')")
    @DeleteMapping
    public String eliminarCuentaContable(
            @Validated(IActualizacion.class) @RequestBody CuentaContable cuentaContable,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.cuentaContableService.eliminarCuentaContable(cuentaContable);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }
}