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
import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.model.mantenimiento.CuentaAjuste;
import ob.debitos.simp.service.ICuentaAjusteService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

/**
 * Recibe las peticiones del usuario relacionadas al mantenimiento de cuentas
 * ajuste.
 * <p>
 * Especificamente recibe las peticiones de:
 * <ul>
 * <li>Búsqueda de cuentas ajuste.
 * <li>Registro de cuentas ajuste.
 * <li>Actualización de cuentas ajuste.
 * <li>Eliminación de cuentas ajuste.
 * </ul>
 * 
 * @author Carla Ulloa
 * @see ICuentaAjusteService
 * @see CuentaAjuste
 */
@Audit(tipo = Tipo.CTA_AJUSTE, datos = Dato.CTA_AJUSTE)
@RequestMapping("/cuentaAjuste")
public @RestController class CuentaAjusteController
{
    public @Autowired ICuentaAjusteService cuentaAjusteService;

    /**
     * Recibe las peticiones para obtener todos las cuentas ajuste registrados.
     * 
     * @return lista de {@link Cliente}
     */
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CTAAJUS', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CuentaAjuste> buscarTodos()
    {
        return this.cuentaAjusteService.buscarTodos();
    }

    /**
     * Recibe las peticiones de registro de cuentas ajuste.
     * 
     * @param cuentaAjuste
     *            la cuenta ajuste a registrar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>cuentaAjuste</b> anotado con
     *            {@literal @Validated}
     * @return la {@link CuentaAjuste} registrada
     */
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CTAAJUS', '1')")
    @PostMapping
    public List<CuentaAjuste> registrar(@Validated({ Default.class, IRegistro.class }) @RequestBody CuentaAjuste cuentaAjuste, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.cuentaAjusteService.registrarCuentaAjuste(cuentaAjuste);
        return this.cuentaAjusteService.buscarPorCodigo(cuentaAjuste.getRolTransaccion(), cuentaAjuste.getMonedaCompensacion(), cuentaAjuste.getTipoMovimiento(), cuentaAjuste.getRegistroContable());
    }

    /**
     * Recibe las peticiones de actualización de cuentas ajuste.
     * 
     * @param cuentaAjuste
     *            la cuenta ajuste a actualizar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>cuentaAjuste</b> anotado con
     *            {@literal @Validated}
     * @return la {@link CuentaAjuste} actualizada
     */
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CTAAJUS', '3')")
    @PutMapping
    public List<CuentaAjuste> actualizar(@Validated({ Default.class, IActualizacion.class }) @RequestBody CuentaAjuste cuentaAjuste, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.cuentaAjusteService.actualizarCuentaAjuste(cuentaAjuste);
        return this.cuentaAjusteService.buscarPorCodigo(cuentaAjuste.getNuevoRolTransaccion(), cuentaAjuste.getNuevoMonedaCompensacion(), cuentaAjuste.getNuevoTipoMovimiento(), cuentaAjuste.getNuevoRegistroContable());
    }

    /**
     * Recibe las peticiones de eliminación de cuentas ajuste.
     * 
     * @param cuentaAjuste
     *            la cuenta ajuste a eliminar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>cuentaAjuste</b> anotado con
     *            {@literal @Validated}
     * @return el mensaje {@link ConstantesGenerales#ELIMINACION_EXITOSA}
     */
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CTAAJUS', '4')")
    @DeleteMapping
    public String eliminar(@Validated(IActualizacion.class) @RequestBody CuentaAjuste cuentaAjuste, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.cuentaAjusteService.eliminarCuentaAjuste(cuentaAjuste);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }
}