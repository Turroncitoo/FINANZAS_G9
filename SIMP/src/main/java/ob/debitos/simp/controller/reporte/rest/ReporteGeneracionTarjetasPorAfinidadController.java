package ob.debitos.simp.controller.reporte.rest;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaGeneracionTarjetas;
import ob.debitos.simp.model.reporte.ReporteGeneracionTarjetasPorAfinidad;
import ob.debitos.simp.service.impl.reporte.ReporteGeneracionTarjetasPorAfinidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("reporte/generacionTarjetas/")
public @RestController class ReporteGeneracionTarjetasPorAfinidadController {

    private @Autowired  ReporteGeneracionTarjetasPorAfinidadService reporteGeneracionTarjetasPorAfinidadService;

    @Audit(tipo = Tipo.RPT_GENERACION_TARJ)
    @GetMapping(value="resumen", params = "accion=buscar")
    public List<ReporteGeneracionTarjetasPorAfinidad> generarReportePorRangoFechas(CriterioBusquedaGeneracionTarjetas criterios) {
        return reporteGeneracionTarjetasPorAfinidadService.generarReportePorRangoFechas(criterios);
    }
}
