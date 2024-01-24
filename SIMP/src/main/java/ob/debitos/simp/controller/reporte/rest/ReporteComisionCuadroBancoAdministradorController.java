package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumen;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.service.IReporteComisionCuadroBancoAdministradorService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
public @RestController class ReporteComisionCuadroBancoAdministradorController
{
    private @Autowired IReporteComisionCuadroBancoAdministradorService reporteService;

    @Audit(tipo = Tipo.RPT_COMIS_CUADRE)
    @PreAuthorize("hasPermission('RPT_COMISCUADRE','2')")
    @GetMapping(value = "/reporte/comision/moneda", params = "accion=buscar")
    public List<ReporteMoneda> buscarComisionesCuadroBancoAdministrador(
            CriterioBusquedaResumen criterioBusquedaResumen)
    {
        return this.reporteService.buscarComisionesCuadroBancoAdministrador(
                criterioBusquedaResumen);
    }

    @Audit(tipo = Tipo.RPT_SUMARIO_COMP)
    @PreAuthorize("hasPermission('RPT_SUMARIOCOMP','2')")
    @GetMapping(value = "/reporte/resumen/sumario-compensacion", params = "accion=buscar")
    public List<ReporteMoneda> buscarSumarioCompensacion(
            CriterioBusquedaResumen criterioBusquedaResumen)
    {
        return this.reporteService
                .buscarSumarioCompensacion(criterioBusquedaResumen);
    }
}