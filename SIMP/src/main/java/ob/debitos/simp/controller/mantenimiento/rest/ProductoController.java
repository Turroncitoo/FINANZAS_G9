package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import ob.debitos.simp.model.mantenimiento.Producto;
import ob.debitos.simp.model.mantenimiento.ProductoCliente;
import ob.debitos.simp.service.IProductoClienteService;
import ob.debitos.simp.service.IProductoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.CodigoProducto;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.PRODUCTO, datos = Dato.PRODUCTO)
@RequestMapping("/producto")
public @RestController class ProductoController
{

    private @Autowired IProductoService productoService;
    private @Autowired IProductoClienteService productoClienteService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Producto> buscarTodos()
    {
        return this.productoService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '2')")
    @GetMapping("/logo/{idLogo}")
    public List<Producto> buscarProductoPorLogo(@CodigoProducto(existe = true) @PathVariable String idLogo)
    {
        return this.productoService.buscarCodigoLogo(idLogo);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '2')")
    @GetMapping("/empresa/{idEmpresa}/cliente/{idCliente}/logo/{idLogo}")
    public List<Producto> buscarProductoPorLogoCliente(@PathVariable String idEmpresa, @PathVariable String idCliente, @CodigoProducto(existe = true) @PathVariable String idLogo)
    {
        return this.productoService.buscarPorCliente(idEmpresa, idCliente, idLogo);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '1')")
    @PostMapping
    public ResponseEntity<?> registrarProducto(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody Producto producto, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.productoService.registrarProducto(producto);
        return ResponseEntity.ok(this.productoService.buscarCodigoProducto(producto.getCodigoProducto()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarProducto(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody Producto producto, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.productoService.actualizarProducto(producto);
        return ResponseEntity.ok(this.productoService.buscarCodigoProducto(producto.getCodigoProducto()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '4')")
    @DeleteMapping
    public ResponseEntity<String> eliminarProducto(@Validated(IActualizacion.class) @RequestBody Producto producto, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.productoService.eliminarProducto(producto);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '2')")
    @GetMapping("/{codigoProducto}/empresa/cliente")
    public List<ProductoCliente> buscarAsociacionProductoCliente(@PathVariable String codigoProducto)
    {
        return this.productoClienteService.buscarPorProducto(codigoProducto);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '1')")
    @PostMapping("/empresa/cliente")
    public ResponseEntity<?> registrarClienteProducto(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody ProductoCliente producto, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.productoClienteService.asociarProductoCliente(producto);
        return ResponseEntity.ok(this.productoClienteService.buscarAsociacionProductoCliente(producto));
    }
    
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PRODUCT', '4')")
    @DeleteMapping("/empresa/cliente")
    public ResponseEntity<String> eliminarProductoCliente(@Validated(IActualizacion.class) @RequestBody ProductoCliente producto, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.productoClienteService.eliminarAsociacionProductoCliente(producto);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}