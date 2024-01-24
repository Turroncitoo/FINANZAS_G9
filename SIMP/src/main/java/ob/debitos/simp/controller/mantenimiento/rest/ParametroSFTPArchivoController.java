package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import javax.validation.groups.Default;

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
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;
import ob.debitos.simp.service.IParametroSFTPArchivoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.PARAM_SFTP_ARCHIVO, datos = Dato.PARAM_SFTP_ARCHIVO)
@RequestMapping("/parametroSFTPArchivos")
public @RestController class ParametroSFTPArchivoController
{

    private @Autowired IParametroSFTPArchivoService parametroSFTPArchivoService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPARC', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ParametrosSFTPArchivo> buscarTodos()
    {
        return this.parametroSFTPArchivoService.buscarTodos();
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPARC', '1')")
    @PostMapping
    public ResponseEntity<?> registrarParametroSFTPArchivo(@Validated({ Default.class, IRegistro.class }) @RequestBody ParametrosSFTPArchivo parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPArchivoService.registrarParametroSFTPArchivo(parametroSFTP);
        return ResponseEntity.ok(parametroSFTPArchivoService.buscarPorIdArchivo(parametroSFTP.getCodigoArchivo()));
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPARC', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarParametroSFTPArchivo(@Validated({ Default.class, IActualizacion.class }) @RequestBody ParametrosSFTPArchivo parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPArchivoService.actualizarParametroSFTPArchivo(parametroSFTP);
        return ResponseEntity.ok(parametroSFTPArchivoService.buscarPorIdArchivo(parametroSFTP.getCodigoArchivo()));
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPARC', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarParametroSFTPArchivo(@Validated(IActualizacion.class) @RequestBody ParametrosSFTPArchivo parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPArchivoService.eliminarParametroSFTPArchivo(parametroSFTP);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
