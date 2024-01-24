package ob.debitos.simp.controller.consultas;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;

@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.VisitaConsulta)
@RequestMapping("/consulta/tipoCambio")
public @Controller class ConsultaTipoCambioController
{

    @Audit(tipo = Tipo.CON_CAMB_VISA)
    @PreAuthorize("hasPermission('CON_CAMBVISA','ANY')")
    @GetMapping(value = "/{tipo:visa}")
    public String irPaginaConsultaTipoCambioVisa(@PathVariable String tipo, ModelMap model)
    {
        model.addAttribute("tipo", tipo);
        return "seguras/consulta/tipoCambio";
    }

    @Audit(tipo = Tipo.CON_CAMB_SBS)
    @PreAuthorize("hasPermission('CON_CAMBSBS','ANY')")
    @GetMapping(value = "/{tipo:sbs}")
    public String irPaginaConsultaTipoCambioSBS(@PathVariable String tipo, ModelMap model)
    {
        model.addAttribute("tipo", tipo);
        return "seguras/consulta/tipoCambio";
    }

}