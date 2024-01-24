package ob.debitos.simp.controller.seguridad.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import ob.debitos.simp.model.seguridad.PoliticaSeguridad;
import ob.debitos.simp.service.IPoliticaSeguridadService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.POLIT_SEG, datos = Dato.POLIT_SEG)
@RequestMapping("/seguridad/politicaSeguridad")
public @RestController class PoliticaSeguridadController
{
    private @Autowired IPoliticaSeguridadService politicaSeguridadService;

    @PreAuthorize("hasPermission('MANT_POLITICA_SEGURIDAD', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<PoliticaSeguridad> buscarTodos()
    {
        return this.politicaSeguridadService.buscarTodos();
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_POLITICA_SEGURIDAD', '3')")
    @PutMapping
    public String actualizarPolicaSeguridad(
            @Validated @RequestBody PoliticaSeguridad politicaSeguridad, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.politicaSeguridadService.actualizarPoliticaSeguridad(politicaSeguridad);
        return ConstantesGenerales.ACTUALIZACION_EXITOSA;
    }
}