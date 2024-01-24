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
import ob.debitos.simp.model.mantenimiento.PINEntryCapability;
import ob.debitos.simp.service.IPINEntryCapabilityService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

/**
 * Recibe las peticiones del usuario relacionadas al mantenimiento de las
 * capacidades de ingreso de las transacciones de tipo PIN.
 * <p>
 * Especificamente recibe las peticiones de:
 * <ul>
 * <li>Búsqueda de capacidades de ingreso de las txns tipo PIN.
 * <li>Registro de capacidades de ingreso de las txns tipo PIN.
 * <li>Actualización de capacidades de ingreso de las txns tipo PIN.
 * <li>Eliminación de capacidades de ingreso de las txns tipo PIN.
 * </ul>
 * 
 * @author Fernando Fonseca
 * @see IPINEntryCapabilityService
 * @see PINEntryCapability
 */
@Audit(tipo = Tipo.PIN_ENTRY_CAP, datos = Dato.PIN_ENTRY_CAP)
@RequestMapping("/pinEntryCapability")
public @RestController class PINEntryCapabilityController
{

    private @Autowired IPINEntryCapabilityService pinEntryCapabilityService;

    /**
     * Recibe las peticiones para obtener todas las capacidades de ingreso de
     * las transacciones de tipo PIN.
     * 
     * @return lista de {@link PINEntryCapability}
     */
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PINENTRYCAPABILITY','2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<PINEntryCapability> buscarTodos()
    {
        return this.pinEntryCapabilityService.buscarTodos();
    }

    /**
     * Recibe las peticiones de registro de capacidad de ingreso de transacción
     * de tipo PIN.
     * 
     * @param pinEntryCapability
     *            la capacidad de ingreso a registrar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>pinEntryCapability</b> anotado con
     *            {@literal @Validated}
     * @return el {@link PINEntryCapability} registrado
     */
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PINENTRYCAPABILITY', '1')")
    @PostMapping
    public List<PINEntryCapability> registrarPINEntryCapability(@Validated({ Default.class, IRegistro.class }) @RequestBody PINEntryCapability pinEntryCapability, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.pinEntryCapabilityService.registrarPINEntryCapability(pinEntryCapability);
        return this.pinEntryCapabilityService.buscarPorCodigo(pinEntryCapability.getCodigo());
    }

    /**
     * Recibe las peticiones de actualización de capacidad de ingreso de
     * transacción de tipo PIN.
     * 
     * @param pinEntryCapability
     *            la capacidad de ingreso a actualizar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>pinEntryCapability</b> anotado con
     *            {@literal @Validated}
     * @return el {@link PINEntryCapability} actualizado
     */
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PINENTRYCAPABILITY', '3')")
    @PutMapping
    public List<PINEntryCapability> actualizarPINEntryCapability(@Validated({ Default.class, IActualizacion.class }) @RequestBody PINEntryCapability pinEntryCapability, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.pinEntryCapabilityService.actualizarPINEntryCapability(pinEntryCapability);
        return this.pinEntryCapabilityService.buscarPorCodigo(pinEntryCapability.getCodigo());
    }

    /**
     * Recibe la petición de eliminación de capacidad de ingreso de transacción
     * de tipo PIN.
     * 
     * @param pinEntryCapability
     *            la capacidad de ingreso a eliminar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>pinEntryCapability</b> anotado con
     *            {@literal @Validated}
     * @return el mensaje {@link ConstantesGenerales#ELIMINACION_EXITOSA}
     */
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PINENTRYCAPABILITY', '4')")
    @DeleteMapping
    public String eliminarPINEntryCapability(@RequestBody PINEntryCapability pinEntryCapability, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.pinEntryCapabilityService.eliminarPINEntryCapability(pinEntryCapability);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }

}
