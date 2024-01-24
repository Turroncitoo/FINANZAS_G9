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
import ob.debitos.simp.model.mantenimiento.CodigoFacturacion;
import ob.debitos.simp.service.ICodigoFacturacionService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.CODIGO_FACTURACION, datos = Dato.CODIGO_FACTURACION)
@RequestMapping("/codigoFacturacion")
public @RestController class CodigoFacturacionController
{

    private @Autowired ICodigoFacturacionService codigoFacturacionService;

    /**
     * Recibe las peticiones para obtener todos los códigos de facturación
     * registrados.
     * 
     * @return lista de {@link CodigoFacturacion}
     */
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CODIGOFACTURACION', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoFacturacion> buscarTodos()
    {
        return codigoFacturacionService.buscarTodos();
    }

    /**
     * Recibe las peticiones de registro de códigos de facturación.
     * 
     * @param codigoFacturacion
     *            el código de facturación a registrar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>codigoFacturacion</b> anotado con
     *            {@literal @Validated}
     * @return el {@link CodigoFacturacion} registrado
     */
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CODIGOFACTURACION', '1')")
    @PostMapping
    public List<CodigoFacturacion> registrar(@Validated({ Default.class, IRegistro.class }) @RequestBody CodigoFacturacion codigoFacturacion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoFacturacionService.registrarCodigoFacturacion(codigoFacturacion);
        return this.codigoFacturacionService.buscarPorCodigo(codigoFacturacion.getIdCodigoFacturacion());
    }

    /**
     * Recibe las peticiones de actualización de códigos de facturación.
     * 
     * @param codigoFacturacion
     *            el código de facturación a actualizar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>codigoFacturacion</b> anotado con
     *            {@literal @Validated}
     * @return el {@link CodigoFacturacion} actualizado
     */
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CODIGOFACTURACION', '3')")
    @PutMapping
    public List<CodigoFacturacion> actualizar(@Validated({ Default.class, IActualizacion.class }) @RequestBody CodigoFacturacion codigoFacturacion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoFacturacionService.actualizarCodigoFacturacion(codigoFacturacion);
        return this.codigoFacturacionService.buscarPorCodigo(codigoFacturacion.getIdCodigoFacturacion());
    }

    /**
     * Recibe las peticiones de eliminación de códigos de facturación.
     * 
     * @param codigoProcesoSwitch
     *            el código de facturación a eliminar
     * @param error
     *            almacena los mensajes de error obtenidos del proceso de
     *            validación del parámetro <b>codigoFacturacion</b> anotado con
     *            {@literal @Validated}
     * @return el mensaje {@link ConstantesGenerales#ELIMINACION_EXITOSA}
     */
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CODIGOFACTURACION', '4')")
    @DeleteMapping
    public String eliminar(@Validated(IActualizacion.class) @RequestBody CodigoFacturacion codigoFacturacion, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoFacturacionService.eliminarCodigoFacturacion(codigoFacturacion);
        return ConstantesGenerales.ELIMINACION_EXITOSA;
    }

}
