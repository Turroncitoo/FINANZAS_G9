package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import ob.debitos.simp.model.mantenimiento.CodigoTransaccionBevertec;
import ob.debitos.simp.service.ICodigoTxnBevertecService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionEliminacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.TRANS_BTEC, datos = Dato.COD_TRANSACCION_BEVERTEC)
@RequestMapping("/codigoTxnBevertec")
public @RestController class CodigoTxnBevertecController
{

    private @Autowired ICodigoTxnBevertecService codigoTxnBevertecService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_TRANSBTEC', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<CodigoTransaccionBevertec> buscarTodos()
    {
        return codigoTxnBevertecService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_TRANSBTEC', '1')")
    @PostMapping
    public ResponseEntity<?> registrarCodigoTxnBevertec(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody CodigoTransaccionBevertec codigoTxnBevertec, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoTxnBevertecService.registrarCodigoTxnBevertec(codigoTxnBevertec);
        return ResponseEntity.ok(codigoTxnBevertecService.buscarPorCodigoCanalEmisorTipoTransaccionCodTransaccion(codigoTxnBevertec.getCodigoCanalEmisor(), codigoTxnBevertec.getTipoTransaccion(), codigoTxnBevertec.getCodTransaccion()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_TRANSBTEC', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarCodigoTxnBevertec(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody CodigoTransaccionBevertec codigoTxnBevertec, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoTxnBevertecService.actualizarCodigoTxnBevertec(codigoTxnBevertec);
        return ResponseEntity.ok(codigoTxnBevertecService.buscarPorCodigoCanalEmisorTipoTransaccionCodTransaccion(codigoTxnBevertec.getCodigoCanalEmisor(), codigoTxnBevertec.getTipoTransaccion(), codigoTxnBevertec.getCodTransaccion()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_TRANSBTEC', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarCodigoTxnBevertec(@Validated(ISecuenciaValidacionEliminacion.class) @RequestBody CodigoTransaccionBevertec codigoTxnBevertec, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        codigoTxnBevertecService.eliminarCodigoTxnBevertec(codigoTxnBevertec);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}