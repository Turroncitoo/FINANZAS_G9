package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaEstadoTarjeta;
import ob.debitos.simp.model.reporte.ReporteEstadoTarjeta;
import ob.debitos.simp.service.impl.reporte.ReporteEstadoTarjetaService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/estadoTarjeta")
public @RestController class ReporteEstadoTarjetaController {
	
	@Autowired
	private  ReporteEstadoTarjetaService reporteEstadoTarjetaService;
	
	@Audit(tipo = Tipo.RPT_ESTADOTARJETAS)
    @PreAuthorize("hasPermission('RPT_ESTADOTARJETAS', '2')")
    @GetMapping(value = "resumenMensual", params = "accion=buscar")
    public List<ReporteEstadoTarjeta> buscarResumenMensualPorCriterios(CriterioBusquedaEstadoTarjeta criterioBusqueda)
    {
        return this.reporteEstadoTarjetaService.buscarResumenMensualPorCriterios(criterioBusqueda);
    }

}
