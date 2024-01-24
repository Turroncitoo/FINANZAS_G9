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
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransacciones;
import ob.debitos.simp.model.reporte.ReporteTransaccionesTarjetas;
import ob.debitos.simp.service.IReporteTransaccionesService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/transacciones/")
public @RestController class ReporteTransaccionesController {
	
	private @Autowired IReporteTransaccionesService reporteTransaccionesService;
	
	@Audit(tipo = Tipo.RPT_TXNSTARJETA)
    @PreAuthorize("hasPermission('RPT_TXNSTARJETA', '2')")
    @GetMapping(value = "tarjetas", params = "accion=buscar")
    public List<ReporteTransaccionesTarjetas> buscarTransaccionesTarjetas(CriterioBusquedaResumenTransacciones criterio)
    {
        return reporteTransaccionesService.buscarTransaccionesTarjetas(criterio);
    }
}
