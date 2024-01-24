package ob.debitos.simp.controller.consultas;

import org.springframework.beans.factory.annotation.Autowired;
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
import ob.debitos.simp.service.IEmpresaService;

@Vista
@Audit(tipo = Tipo.CON_MOV_NO_CONCIL, accion = Accion.Visita)
@RequestMapping("/consulta/noconciliadas")
public @Controller class ConsultaNoConciliadaController
{

    private @Autowired IEmpresaService empresaService;

    @Audit(comentario = Comentario.VisitaConsulta)
    @PreAuthorize("hasPermission('CON_MOVNOCONCIL','ANY')")
    @GetMapping(value = "/{consulta:logautorizaciones}")
    public String irPaginaConsultaNoConciliadas(@PathVariable String consulta, ModelMap model)
    {
        model.addAttribute("consulta", consulta);
        model.addAttribute("empresas", this.empresaService.buscarTodos());
        return "seguras/consulta/noConciliada";
    }
    
}