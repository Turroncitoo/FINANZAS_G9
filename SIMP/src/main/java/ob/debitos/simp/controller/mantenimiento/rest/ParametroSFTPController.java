package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.jquery.jstree.JsTreeAttribute;
import ob.debitos.simp.model.jquery.jstree.criterio.JsTreeFilter;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTP;
import ob.debitos.simp.model.respuestas.RespuestaConexionSFTP;
import ob.debitos.simp.service.IParametroSFTPService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.PARAM_SFTP, datos = Dato.PARAM_SFTP)
@RequestMapping("/parametroSFTP")
public @RestController class ParametroSFTPController
{

    private @Autowired IParametroSFTPService parametroSFTPService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMSFTP', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ParametrosSFTP> buscarTodos()
    {
        return this.parametroSFTPService.buscarTodosParaMantenimiento();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMSFTP', '2')")
    @GetMapping(params = "accion=probar-conexion-sftp")
    public RespuestaConexionSFTP probarConexion()
    {
        return this.parametroSFTPService.probarConexion();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMSFTP', '2')")
    @GetMapping(params = "accion=ver-tree-sftp")
    public List<JsTreeAttribute> verTreeSFTP(JsTreeFilter filtro)
    {
        return this.parametroSFTPService.verTreeSFTP(filtro);
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PARAMSFTP', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarParametroSFTP(@Validated @RequestBody ParametrosSFTP parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPService.actualizarParametrosSFTP(parametroSFTP);
        return ResponseEntity.ok(parametroSFTPService.buscarTodosParaMantenimiento());
    }

}
