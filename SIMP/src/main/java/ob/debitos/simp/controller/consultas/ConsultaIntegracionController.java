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

/**
 * 
 * @author Carlos Mirano
 *
 */
@Vista
@Audit(accion = Accion.Visita, comentario = Comentario.Visita)
@RequestMapping("/consulta/integracion")
public @Controller class ConsultaIntegracionController
{

    @Audit(tipo = Tipo.CON_LOG_INTER_ALEGRA)
    @PreAuthorize("hasPermission('CON_LOG_INTER_ALEGRA','ANY')")
    @GetMapping("/{consulta:alegra}")
    public String irPaginaConsultaInterfaceAlegra(@PathVariable String consulta, ModelMap modelMap)
    {
        modelMap.addAttribute("consulta", consulta);
        return "seguras/consulta/integracion";
    }

}
