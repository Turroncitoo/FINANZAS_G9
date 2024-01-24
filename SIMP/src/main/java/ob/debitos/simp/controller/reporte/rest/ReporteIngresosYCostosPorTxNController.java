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
import ob.debitos.simp.model.criterio.CriterioBusquedaIngresosYCostosPorTxN;
import ob.debitos.simp.model.reporte.ReporteIngresosYCostosPorTxN;
import ob.debitos.simp.service.impl.reporte.ReporteIngresosYCostosPorTxNService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/ingresosCostosPorTxn")
public @RestController class ReporteIngresosYCostosPorTxNController {
	
	private @Autowired ReporteIngresosYCostosPorTxNService reporteIngresosYCostosPorTxNService;
	
	@Audit(tipo = Tipo.RPT_INGRESOS_COSTOS_TXN)
    @PreAuthorize("hasPermission('RPT_INGRESOS_COSTOS_TXN', '2')")
    @GetMapping(value = "resumenMensual", params = "accion=buscar")
    public List<ReporteIngresosYCostosPorTxN> buscarResumenMensualPorCriterios(CriterioBusquedaIngresosYCostosPorTxN criterioBusqueda)
    {
        return this.reporteIngresosYCostosPorTxNService.buscarResumenMensualPorCriterios(criterioBusqueda);
    }

}
