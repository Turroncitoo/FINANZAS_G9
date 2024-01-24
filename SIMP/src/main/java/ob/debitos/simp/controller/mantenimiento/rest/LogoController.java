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
import ob.debitos.simp.model.mantenimiento.Logo;
import ob.debitos.simp.service.ILogoService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.ISecuenciaValidacionRegistro;

@Audit(tipo = Tipo.LOGO, datos = Dato.LOGO)
@RequestMapping("/logo")
public @RestController class LogoController
{

    private @Autowired ILogoService logoService;

    @PreAuthorize("hasPermission('MANT_LOGO', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(params = "accion=buscarTodosInstitucion")
    public List<Logo> buscarTodosInstitucion()
    {
        return logoService.buscarTodosInstitucion();
    }

    @PreAuthorize("hasPermission('MANT_LOGO', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(value = "/{idLogo}")
    public List<Logo> buscarPorIdLogo(@PathVariable String idLogo)
    {
        return logoService.buscarPorIdLogo(idLogo);
    }

    @PreAuthorize("hasPermission('MANT_LOGO', '2')")
    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @GetMapping(value = "/dependeInstitucion/{codigoInstitucion}")
    public List<Logo> buscarPorIdInstitucion(@PathVariable Integer codigoInstitucion)
    {
        return logoService.buscarPorCodigoInstitucion(codigoInstitucion);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('MANT_LOGO','2')")
    @GetMapping(value = "/membresia/{idMembresia}/claseServicio/{idClaseServicio}/institucion/{idInstitucion}")
    public List<Logo> buscarPorCodigoMembresiaCodigoClaseServicio(@PathVariable String idMembresia, @PathVariable String idClaseServicio, @PathVariable Integer idInstitucion)
    {
        return logoService.buscarPorCodigoMembresiaCodigoClaseServicio(idMembresia, idClaseServicio, idInstitucion);
    }

    @PreAuthorize("hasPermission('MANT_LOGO', '1')")
    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PostMapping
    public ResponseEntity<?> registrarLogo(@Validated(ISecuenciaValidacionRegistro.class) @RequestBody Logo logo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        logoService.registrarLogo(logo);
        return ResponseEntity.ok(logoService.buscarPorIdLogo(logo.getIdLogo()));
    }

    @PreAuthorize("hasPermission('MANT_LOGO', '3')")
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PutMapping
    public ResponseEntity<?> actualizarLogo(@Validated(ISecuenciaValidacionActualizacion.class) @RequestBody Logo logo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        logoService.actualizarLogo(logo);
        return ResponseEntity.ok(logoService.buscarPorIdLogo(logo.getIdLogo()));
    }

    @PreAuthorize("hasPermission('MANT_LOGO', '4')")
    @Audit(accion = Accion.Eliminacion, comentario = Comentario.Eliminacion)
    @DeleteMapping
    public ResponseEntity<?> eliminarLogo(@Validated(IActualizacion.class) @RequestBody Logo logo, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        logoService.eliminarLogo(logo);
        return ResponseEntity.ok(ConstantesGenerales.ELIMINACION_EXITOSA);
    }

}
