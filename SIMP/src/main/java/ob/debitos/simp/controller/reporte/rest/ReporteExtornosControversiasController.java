package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaExtornosControversias;
import ob.debitos.simp.model.reporte.ReporteExtornosControversias;
import ob.debitos.simp.service.IReporteExtornosControversiasService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/extornosControversias")
public @RestController class ReporteExtornosControversiasController
{

    private @Autowired IReporteExtornosControversiasService reporteExtornosControversiasService;

    @Audit(tipo = Tipo.RPT_EXTORNOS_CONTR)
    @PreAuthorize("hasPermission('RPT_EXTCONT', '2')")
    @GetMapping(params = "accion=buscar")
    public List<ReporteExtornosControversias> buscar(CriterioBusquedaExtornosControversias criterio, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        return this.reporteExtornosControversiasService.buscar(criterio);
    }

}
