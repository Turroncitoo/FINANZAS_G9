package ob.debitos.simp.controller.reporte.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableEmisor;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenLogContableReceptor;
import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenAutorizacionSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.model.reporte.ReporteResumenAutorizacion;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableReceptor;
import ob.debitos.simp.model.reporte.ReporteResumenSwDmpLog;
import ob.debitos.simp.model.reporte.ReporteResumenLogContableEmisor;
import ob.debitos.simp.model.reporte.ReporteResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.service.IReporteResumenMovimientoService;

@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/resumen/movimiento/")
public @RestController class ReporteResumenMovimientoController
{

    private @Autowired IReporteResumenMovimientoService reporteService;

    @Audit(tipo = Tipo.RPT_MOV_AUT)
    @PreAuthorize("hasPermission('RPT_MOVAUT', '2')")
    @GetMapping(value = "autorizacion", params = "accion=buscar")
    public List<ReporteResumenAutorizacion> buscarResumenAutorizacion(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda)
    {
        return reporteService.buscarResumenAutorizacion(criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_MOV_SWDMPLOG)
    @PreAuthorize("hasPermission('RPT_MOVSWDMPLOG', '2')")
    @GetMapping(value = "swDmpLog", params = "accion=buscar")
    public List<ReporteResumenSwDmpLog> buscarResumenSwDmpLog(CriterioBusquedaResumenAutorizacionSwDmpLog criterioBusqueda)
    {
        return reporteService.buscarResumenSwDmpLog(criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_MOV_TRANS_AG)
    @PreAuthorize("hasPermission('RPT_MOVTRANSAG', '2')")
    @GetMapping(value = "transaccionAprobadaAgencia", params = "accion=buscar")
    public List<ReporteResumenTransaccionAprobadaAgencia> buscarResumenTransaccionAprobadaAgencia(CriterioBusquedaResumenTransaccionAprobadaAgencia criterioBusqueda)
    {
        return reporteService.buscarResumenTransaccionAprobadaAgencia(criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_MOV_LG_CNT_EMI)
    @PreAuthorize("hasPermission('RPT_MOVLGCNTEMI', '2')")
    @GetMapping(value = "logContableEmisor", params = "accion=buscar")
    public List<ReporteResumenLogContableEmisor> buscarResumenLogContableEmisor(CriterioBusquedaResumenLogContableEmisor criterioBusqueda)
    {
        return this.reporteService.buscarResumenLogContableEmisor(criterioBusqueda);
    }

    @Audit(tipo = Tipo.RPT_MOV_LG_CNT_REC)
    @PreAuthorize("hasPermission('RPT_MOVLGCNTREC', '2')")
    @GetMapping(value = "logContableReceptor", params = "accion=buscar")
    public List<ReporteResumenLogContableReceptor> buscarResumenLogContableReceptor(CriterioBusquedaResumenLogContableReceptor criterioBusqueda)
    {
        return reporteService.buscarResumenLogContableReceptor(criterioBusqueda);
    }

}