package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.hibernate.validator.constraints.Length;
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
import ob.debitos.simp.model.mantenimiento.SubBin;
import ob.debitos.simp.service.ISubBinClienteService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.CodigoBin;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.ISecuenciaValidacionAsociacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.ISecuenciaValidacionAsociacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.ISecuenciaValidacionAsociacionRegistro;

@Audit(tipo = Tipo.SUB_BIN_CLTE, datos = Dato.SUB_BIN_CLTE)
@RequestMapping("/cliente")
public @RestController class SubBinClienteController
{

    private @Autowired ISubBinClienteService subBinClienteService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_SUBBINCLTE', '2')")
    @GetMapping("/{idCliente}/empresa/{idEmpresa}/subBin")
    public List<SubBin> buscarAsociacionSubBinPorIdCliente(@Length(min = 1, max = 4, message = "{Length.Cliente.idCliente}") @PathVariable String idCliente, @IdEmpresa(existe = true) @PathVariable String idEmpresa)
    {
        return subBinClienteService.buscarAsociacionSubBinClientePorIdClienteIdEmpresa(idCliente, idEmpresa);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_SUBBINCLTE', '2')")
    @GetMapping("/{idCliente}/empresa/{idEmpresa}/bin/{codigoBIN}/subBin")
    public List<SubBin> buscarAsociacionSubBinCientePorCodigoBinCodigoCliente(@CodigoBin(existe = true) @PathVariable String codigoBIN, @Length(min = 1, max = 4, message = "{Length.Cliente.idCliente}") @PathVariable String idCliente, @IdEmpresa(existe = true) @PathVariable String idEmpresa)
    {
        return subBinClienteService.buscarAsociacionSubBinCientePorCodigoBinIdClienteIdEmpresa(codigoBIN, idCliente, idEmpresa);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_SUBBINCLTE', '1')")
    @PostMapping(params = "accion=asociarSubBIN")
    public ResponseEntity<?> asociarSubBinCliente(@Validated(ISecuenciaValidacionAsociacionRegistro.class) @RequestBody SubBin subBin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subBinClienteService.asociarSubBinCliente(subBin);
        return ResponseEntity.ok(subBinClienteService.buscarAsociacionSubBinCliente(subBin.getCodigoBIN(), subBin.getCodigoSubBIN(), subBin.getIdCliente(), subBin.getIdEmpresa()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_SUBBINCLTE', '3')")
    @PutMapping(params = "accion=actualizarAsociacionSubBIN")
    public ResponseEntity<?> actualizarAsociacionSubBinCliente(@Validated(ISecuenciaValidacionAsociacionActualizacion.class) @RequestBody SubBin subBin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subBinClienteService.actualizarAsociacionSubBinCliente(subBin);
        return ResponseEntity.ok(subBinClienteService.buscarAsociacionSubBinCliente(subBin.getCodigoBIN(), subBin.getCodigoSubBIN(), subBin.getIdCliente(), subBin.getIdEmpresa()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_SUBBINCLTE', '4')")
    @DeleteMapping(params = "accion=removerAsociacionSubBIN")
    public ResponseEntity<?> eliminarAsociacionSubBinCliente(@Validated(ISecuenciaValidacionAsociacionEliminacion.class) @RequestBody SubBin subBin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subBinClienteService.eliminarAsociacionSubBinCliente(subBin);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}