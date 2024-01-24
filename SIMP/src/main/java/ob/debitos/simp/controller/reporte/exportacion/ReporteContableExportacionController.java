package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.sf.jasperreports.engine.JRException;
import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaDetalleContable;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenContable;
import ob.debitos.simp.service.IReporteContableService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

/**
 * Recibe las peticiones del usuario para la generación del reporte del
 * resultado de la consulta de balance contable.
 * <p>
 * Especificamente genera los reportes de los resultados retornados por las
 * consultas de
 * {@link ob.debitos.simp.controller.reporte.rest.ReporteContableController}.
 * <p>
 * Los reportes generados por esta clase son de tipo XLSX y utilizan la vista
 * {@link ob.debitos.simp.configuracion.view.JxlsView}
 * 
 * @author Carla Ulloa
 * @author Hanz Llanto
 */
@Vista
@Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
@RequestMapping("/reporte/contable/")
public @Controller class ReporteContableExportacionController
{
    private @Autowired IReporteContableService reporteContableService;

    /**
     * Recibe las peticiones de generación del reporte del resultado de la
     * consulta de balance contable resumen.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @param errors
     *            almacena y expone información sobre errores de validación
     * @param response
     *            proporcionar una funcionalidad específica de HTTP al enviar
     *            una respuesta
     */
    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT', '5')")
    @GetMapping(value = "resumen", params = "accion=exportar")
    public void buscarReporteContableResumen(
            @Validated CriterioBusquedaResumenContable criterioBusqueda,
            Errors errors, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        this.reporteContableService.exportarResumenContable(criterioBusqueda,
                response);
    }

    /**
     * Recibe las peticiones de generación del reporte del resultado de la
     * consulta de balance contable detalle.
     * 
     * @param criterioBusqueda
     *            almacena los criterios de búsqueda de la consulta
     * @param errors
     *            almacena y expone información sobre errores de validación
     * @param response
     *            proporcionar una funcionalidad específica de HTTP al enviar
     *            una respuesta
     */
    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT', '5')")
    @GetMapping(value = "detalle", params = "accion=exportar")
    public void buscarReporteContableDetalle(
            @Validated CriterioBusquedaDetalleContable criterioBusqueda,
            Errors errors, HttpServletResponse response) throws IOException
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        this.reporteContableService.exportarDetalleContable(criterioBusqueda,
                response);
    }

    @Audit(tipo = Tipo.RPT_CONTABLE)
    @PreAuthorize("hasPermission('RPT_CONT', '5')")
    @GetMapping(value = "resumenPorPeriodo", params = "accion=exportar")
    public void generarPdfReporteContableResumenPorPeriodo(
            @Validated CriterioBusquedaResumenContable criterioBusqueda,
            HttpServletResponse response)
            throws IOException, JRException, SQLException
    {
        this.reporteContableService.generarPdfReporteContableResumenPorPeriodo(
                criterioBusqueda, response);
    }
}