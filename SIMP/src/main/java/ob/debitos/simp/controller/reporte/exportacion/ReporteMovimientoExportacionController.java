package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ob.debitos.simp.model.ajuste.TxnsAjuste;
import ob.debitos.simp.model.consulta.movimiento.TxnsCompensacion;
import ob.debitos.simp.model.paginacion.CriterioPaginacion;
import ob.debitos.simp.model.paginacion.filtro.FiltroDtTxnsCompensacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionAjuste;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionCompensacion;
import ob.debitos.simp.service.IConsultaAjusteService;
import ob.debitos.simp.service.ITxnsCompensacionService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/movimientos")
public @Controller class ReporteMovimientoExportacionController
{

    private @Autowired ITxnsCompensacionService txnsCompensacionService;
    private @Autowired IConsultaAjusteService txnsAjustesService;

    @Audit(tipo = Tipo.RPT_MOV_COMP)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','5')")
    @GetMapping(value = "/txnsCompensacion", params = "accion=exportarCriterios")
    public void generarExcelPorCriterios(
            @Validated CriterioBusquedaTransaccionCompensacion criterioBusqueda,
            Errors errors, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        CriterioPaginacion<CriterioBusquedaTransaccionCompensacion, FiltroDtTxnsCompensacion> criterioPaginacion = new CriterioPaginacion<>();
        criterioPaginacion.setCriterioBusqueda(criterioBusqueda);
        List<TxnsCompensacion> list = this.txnsCompensacionService
                .filtrarTxnsCompensacion(criterioPaginacion);
        this.txnsCompensacionService.exportarTxnsCompensacionPorCriterios(list,
                criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_MOV_COMP)
    @PreAuthorize("hasPermission('CON_MOVLGCNT','5')")
    @GetMapping(value = "/txnsCompensacion", params = "accion=exportarTipoDocumento")
    public void generarExcelPorCriterios(
            @Validated CriterioBusquedaTipoDocumento criterioBusqueda,
            Errors errors, HttpServletRequest request,
            HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        CriterioPaginacion<CriterioBusquedaTipoDocumento, FiltroDtTxnsCompensacion> criterioPaginacion = new CriterioPaginacion<>();
        criterioPaginacion.setCriterioBusqueda(criterioBusqueda);
        List<TxnsCompensacion> list = this.txnsCompensacionService
                .buscarTxnsCompensacion(criterioPaginacion);
        this.txnsCompensacionService.exportarTxnsCompensacionPorTipoDocumento(
                list, criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_MOV_AJUSTE)
    @PreAuthorize("hasPermission('CON_MOVAJUS','5')")
    @GetMapping(value = "/txnsAjustes", params = "accion=exportarPorTipoDocumento")
    public void generarExcelTxnsAjustesPorDocumento(
            CriterioBusquedaTipoDocumento criterioBusqueda, Errors errors,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TxnsAjuste> list = this.txnsAjustesService
                .buscarTransaccionesAjustesPorTipoDocumento(criterioBusqueda);
        this.txnsAjustesService.exportarTxnsAjustesPorTipoDocumento(list,
                criterioBusqueda, request, response);
    }

    @Audit(tipo = Tipo.RPT_MOV_AJUSTE)
    @PreAuthorize("hasPermission('CON_MOVAJUS','5')")
    @GetMapping(value = "/txnsAjustes", params = "accion=exportarPorCriterios")
    public void generarExcelTxnsAjustesPorDocumento(
            CriterioBusquedaTransaccionAjuste criterioBusqueda, Errors errors,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<TxnsAjuste> list = this.txnsAjustesService
                .buscarTransaccionesAjustesPorCriterios(criterioBusqueda);
        this.txnsAjustesService.exportarTxnsAjustesPorCriterios(list,
                criterioBusqueda, request, response);
    }

}