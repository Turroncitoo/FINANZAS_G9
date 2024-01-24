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
import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.service.IClienteService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.CLTE, datos = Dato.CLIENTE)
@RequestMapping("/cliente")
public @RestController class ClienteController
{

    private @Autowired IClienteService clienteService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_CLTE', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<Cliente> buscarTodos()
    {
        return clienteService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLTE', '2')")
    @GetMapping("/empresa/{idEmpresa}")
    public List<Cliente> buscarClientePorIdEmpresa(@IdEmpresa(existe = true) @PathVariable String idEmpresa)
    {
        return clienteService.buscarPorIdEmpresa(idEmpresa);
    }

    @GetMapping("/{idCliente}")
    public List<Cliente> buscarClientePorIdCliente(@IdEmpresa(existe = true) @PathVariable String idCliente)
    {
        return clienteService.buscarPorIdCliente(idCliente);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CLTE', '1')")
    @PostMapping
    public List<Cliente> registrarCliente(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody Cliente cliente, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        clienteService.registrarCliente(cliente);
        return this.clienteService.buscarPodIdClienteIdEmpresa(cliente.getIdCliente(), cliente.getIdEmpresa());
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CLTE', '3')")
    @PutMapping
    public List<Cliente> actualizarCliente(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody Cliente cliente, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        clienteService.actualizarCliente(cliente);
        return this.clienteService.buscarPodIdClienteIdEmpresa(cliente.getIdCliente(), cliente.getIdEmpresa());
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CLTE', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCliente(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody Cliente cliente, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        clienteService.eliminarCliente(cliente);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}