package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.service.IConceptoComisionService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.MANT_CONCEPTO_COMISION, datos = Dato.CONCEPTO_COMISION)
@RequestMapping("/conceptoComision")
public @RestController class ConceptoComisionController
{

    private @Autowired IConceptoComisionService conceptoComisionService;

    @PreAuthorize("hasPermission('MANT_CONCEPTO_COMISION', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(params = "accion=buscarTodos")
    public List<ConceptoComision> buscarTodos()
    {
        return conceptoComisionService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_CONCEPTO_COMISION', '1')")
    @PostMapping
    public ResponseEntity<?> registrarConceptoComision(@Validated({ IRegistro.class, Default.class }) @RequestBody ConceptoComision conceptoComision, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        conceptoComisionService.registrarConceptoComision(conceptoComision);
        return ResponseEntity.ok(conceptoComisionService.buscarPorIdConcepto(conceptoComision.getIdConceptoComision()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_CONCEPTO_COMISION', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarConceptoComision(@Validated({ IActualizacion.class, Default.class }) @RequestBody ConceptoComision conceptoComision, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        conceptoComisionService.actualizarConceptoComision(conceptoComision);
        return ResponseEntity.ok(conceptoComisionService.buscarPorIdConcepto(conceptoComision.getIdConceptoComision()));
    }

}
