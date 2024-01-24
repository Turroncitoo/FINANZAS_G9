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
import ob.debitos.simp.model.mantenimiento.CodigoProcesoBevertec;
import ob.debitos.simp.service.ICodigoProcBevertecService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.CodigoCanalEmisor;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.PROC_BTEC, datos = Dato.COD_PROC_BEVERTEC)
@RequestMapping("/codigoProcBevertec")
public @RestController class CodigoProcBevertecController
{

    private @Autowired ICodigoProcBevertecService codigoProcBevertecService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PROCBTEC', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoProcesoBevertec> buscarTodos()
    {
        return codigoProcBevertecService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_PROCBTEC', '2')")
    @GetMapping(value = "/canalEmisor/{codigoCanalEmisor}")
    public List<CodigoProcesoBevertec> buscarPorCodigoCanalEmisor(@CodigoCanalEmisor(existe = true) @PathVariable String codigoCanalEmisor)
    {
        return codigoProcBevertecService.buscarPorCodigoCanalEmisor(codigoCanalEmisor);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PROCBTEC', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCodigoProcBevertec(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody CodigoProcesoBevertec codigoProcBevertec, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoProcBevertecService.registrarCodigoProcBevertec(codigoProcBevertec);
        return ResponseEntity.ok(codigoProcBevertecService.buscarPorCodigoCanalEmisorTipoTransaccion(codigoProcBevertec.getCodigoCanalEmisor(), codigoProcBevertec.getTipoTransaccion()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PROCBTEC', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCodigoProcBevertec(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CodigoProcesoBevertec codigoProcBevertec, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoProcBevertecService.actualizarCodigoProcBevertec(codigoProcBevertec);
        return ResponseEntity.ok(codigoProcBevertecService.buscarPorCodigoCanalEmisorTipoTransaccion(codigoProcBevertec.getCodigoCanalEmisor(), codigoProcBevertec.getTipoTransaccion()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PROCBTEC', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCodigoProcBevertec(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody CodigoProcesoBevertec codigoProcBevertec, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoProcBevertecService.eliminarCodigoProcBevertec(codigoProcBevertec);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}