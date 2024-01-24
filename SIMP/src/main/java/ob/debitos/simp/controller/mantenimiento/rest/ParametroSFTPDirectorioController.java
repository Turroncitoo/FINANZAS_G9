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
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPDirectorio;
import ob.debitos.simp.service.IParametroSFTPDirectorioService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.PARAM_SFTP_DIRECTORIO, datos = Dato.PARAM_SFTP_DIRECTORIO)
@RequestMapping("/parametroSFTPDirectorios")
public @RestController class ParametroSFTPDirectorioController
{

    private @Autowired IParametroSFTPDirectorioService parametroSFTPService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPDIR', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ParametrosSFTPDirectorio> buscarTodos()
    {
        return this.parametroSFTPService.buscarTodos();
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPDIR', '2')")
    @GetMapping(value = "/{idDirectorio}/{tipoOperacion}", params = "accion=buscar")
    public List<ParametrosSFTPDirectorio> buscarPorIdTipo(@PathVariable String idDirectorio, @PathVariable String tipoOperacion)
    {
        return this.parametroSFTPService.buscarParametroSFTPDirectorio(idDirectorio, tipoOperacion);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPDIR', '1')")
    @PostMapping
    public List<ParametrosSFTPDirectorio> registrarParametroSFTPDirectorio(@Validated({ Default.class, IRegistro.class }) @RequestBody ParametrosSFTPDirectorio parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPService.registrarParametrosSFTPDirectorio(parametroSFTP);
        return this.parametroSFTPService.buscarParametroSFTPDirectorio(parametroSFTP.getCodigoProceso(), parametroSFTP.getTipoOperacion());
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPDIR', '3')")
    @PutMapping
    public List<ParametrosSFTPDirectorio> actualizarParametroSFTPDirectorio(@Validated({ Default.class, IActualizacion.class }) @RequestBody ParametrosSFTPDirectorio parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPService.actualizarParametrosSFTPDirectorio(parametroSFTP);
        return this.parametroSFTPService.buscarParametroSFTPDirectorio(parametroSFTP.getCodigoProceso(), parametroSFTP.getTipoOperacion());
    }

    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @PreAuthorize("hasPermission('MANT_PARAMSFTPDIR', '4')")
    @DeleteMapping
    public ResponseEntity<?> eliminarParametroSFTPDirectorio(@Validated(IActualizacion.class) @RequestBody ParametrosSFTPDirectorio parametroSFTP, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroSFTPService.eliminarParametrosSFTPDirectorio(parametroSFTP);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
