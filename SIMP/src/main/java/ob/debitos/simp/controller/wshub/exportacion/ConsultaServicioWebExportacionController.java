package ob.debitos.simp.controller.wshub.exportacion;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.prepago.wshub.LogControlWS;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaConsultaServicioWeb;
import ob.debitos.simp.service.IConsultaServicioWebService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Audit(tipo = Tipo.CON_WS_SER_WEB)
@RequestMapping("/prepago/consulta")
public @Controller class ConsultaServicioWebExportacionController
{

    private @Autowired IConsultaServicioWebService consultaServicioWebService;

    @Audit(comentario = Comentario.ConsultaServicioWeb)
    @PreAuthorize("hasPermission('WS_LOG_CONTROL','5')")
    @GetMapping(value = "/logControl", params = "accion=exportar")
    public void descargarLogControlHistorico(CriterioBusquedaConsultaServicioWeb criterioBusqueda, Errors errors,
                                             HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<LogControlWS> list = this.consultaServicioWebService.buscarLogControlHistorico(criterioBusqueda);
        this.consultaServicioWebService.exportarLogControlHistorico(list, criterioBusqueda, request, response);
    }

}
