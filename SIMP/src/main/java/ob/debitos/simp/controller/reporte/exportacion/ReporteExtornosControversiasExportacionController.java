package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaExtornosControversias;
import ob.debitos.simp.model.reporte.ReporteExtornosControversias;
import ob.debitos.simp.service.IReporteExtornosControversiasService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Vista
@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/extornosControversias")
public @Controller class ReporteExtornosControversiasExportacionController
{

    private @Autowired IReporteExtornosControversiasService reporteExtornosControversiasService;

    @Audit(tipo = Tipo.RPT_EXTORNOS_CONTR)
    @PreAuthorize("hasPermission('RPT_EXTCONT', '5')")
    @GetMapping(params = "accion=exportar")
    public void descargarReporteExtornosControversias(@Validated CriterioBusquedaExtornosControversias criterioBusqueda, Errors errors,
            HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        List<ReporteExtornosControversias> reporte = this.reporteExtornosControversiasService.buscar(criterioBusqueda);
        this.reporteExtornosControversiasService.descargarReporteExtornosControversias(reporte, criterioBusqueda, request, response);
    }
}
