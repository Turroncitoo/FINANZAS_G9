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
import ob.debitos.simp.model.mantenimiento.SubBin;
import ob.debitos.simp.service.ISubBinService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.CodigoBin;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.SUB_BIN, datos = Dato.SUB_BIN)
@RequestMapping("/subBin")
public @RestController class SubBinController
{

    private @Autowired ISubBinService subBinService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_SUBBIN', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<SubBin> buscarTodos()
    {
        return subBinService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_SUBBIN', '2')")
    @GetMapping("/bin/{codigo_bin}")
    public List<SubBin> buscarSubBinesPorCodigoBin(@CodigoBin(existe = true) @PathVariable String codigo_bin)
    {
        return subBinService.buscarPorCodigoBin(codigo_bin);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_SUBBIN', '1')")
    @PostMapping
    public ResponseEntity<?> registrarSubBin(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody SubBin subBin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subBinService.registrarSubBin(subBin);
        return ResponseEntity.ok(subBinService.buscarPorCodigoBinCodigoSubBin(subBin.getCodigoBIN(), subBin.getCodigoSubBIN()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_SUBBIN', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarSubBin(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody SubBin subBin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subBinService.actualizarSubBin(subBin);
        return ResponseEntity.ok(subBinService.buscarPorCodigoBinCodigoSubBin(subBin.getCodigoBIN(), subBin.getCodigoSubBIN()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_SUBBIN', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarSubBin(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody SubBin subBin, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        subBinService.eliminarSubBin(subBin);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}