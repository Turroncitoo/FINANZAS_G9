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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.ClienteAfinidad;
import ob.debitos.simp.service.IClienteAfinidadService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.AFIN_CLTE, datos = Dato.AFINIDAD_CLTE)
@RequestMapping("/cliente")
public @RestController class ClienteAfinidadController
{

    private @Autowired IClienteAfinidadService clienteAfinidadService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '2')")
    @GetMapping("/empresa/afinidad")
    public List<ClienteAfinidad> buscarTodosAsociacionClienteAfinidad()
    {
        return this.clienteAfinidadService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '2')")
    @GetMapping("/{idCliente}/empresa/{idEmpresa}/afinidad/{idAfinidad}")
    public List<ClienteAfinidad> buscarAsociacionPorClienteAfinidad(@PathVariable String idEmpresa, @PathVariable String idCliente, @PathVariable int idAfinidad)
    {
        return this.clienteAfinidadService.buscarAsociacionPorClienteAfinidad(idEmpresa, idCliente, idAfinidad);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '2')")
    @GetMapping("/{idCliente}/empresa/{idEmpresa}/afinidad")
    public List<ClienteAfinidad> buscarAsociacionPorCliente(@PathVariable String idEmpresa, @PathVariable String idCliente)
    {
        return this.clienteAfinidadService.buscarAsociacionPorCliente(idEmpresa, idCliente);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '2')")
    @GetMapping("/{idCliente}/empresa/{idEmpresa}/logo/{idLogo}")
    public List<ClienteAfinidad> buscarAsociacionPorClienteLogo(@PathVariable String idEmpresa, @PathVariable String idCliente, @PathVariable String idLogo)
    {
        return this.clienteAfinidadService.buscarAsociacionPorClienteLogo(idEmpresa, idCliente, idLogo);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '2')")
    @GetMapping("/empresa/afinidad/{idAfinidad}")
    public List<ClienteAfinidad> buscarAsociacionPorClienteAfinidad(@PathVariable int idAfinidad)
    {
        return this.clienteAfinidadService.buscarAsociacionPorAfinidad(idAfinidad);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '1')")
    @PostMapping(params = "accion=asociarClienteAfinidad")
    public ResponseEntity<?> asociarClienteAfinidad(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody ClienteAfinidad clienteAfinidad, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.clienteAfinidadService.asociarClienteAfinidad(clienteAfinidad);
        return ResponseEntity.ok(this.clienteAfinidadService.buscarAsociacionPorClienteAfinidad(clienteAfinidad.getIdEmpresa(), clienteAfinidad.getIdCliente(), clienteAfinidad.getIdAfinidad()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_CLTEAFIN', '4')")
    @DeleteMapping(params = "accion=removerAsociacionClienteAfinidad")
    public ResponseEntity<?> eliminarAsociacionSubBinCliente(@RequestBody ClienteAfinidad clienteAfinidad, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.clienteAfinidadService.eliminarAsociacionClienteAfinidad(clienteAfinidad);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
