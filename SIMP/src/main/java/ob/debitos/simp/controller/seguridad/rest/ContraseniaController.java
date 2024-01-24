package ob.debitos.simp.controller.seguridad.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.seguridad.CambioContrasenia;
import ob.debitos.simp.service.IContraseniaService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.CAMB_CONTRASENIA, datos = Dato.CAMB_CONTRASENIA)
@RequestMapping("/seguridad/contrasenia")
public @RestController class ContraseniaController
{
    private @Autowired IContraseniaService contraseniaService;

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('CAMB_CONTRASENIA', '3')")
    @PutMapping
    public String actualizarContrasenia(@Validated @RequestBody CambioContrasenia cambioContrasenia,
            Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.contraseniaService.actualizarContrasenia(cambioContrasenia);
        return ConstantesGenerales.ACTUALIZACION_EXITOSA;
    }
}