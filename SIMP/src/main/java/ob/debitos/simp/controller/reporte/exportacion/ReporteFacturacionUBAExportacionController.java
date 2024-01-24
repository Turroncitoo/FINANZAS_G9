package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

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
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteFacturacion;
import ob.debitos.simp.service.IReporteFacturacionUBAService;
import ob.debitos.simp.utilitario.ExcepcionUtil;
import org.springframework.validation.Errors;
import ob.debitos.simp.model.reporte.ReporteMoneda;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

@Vista
@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte/facturacionUBA")
public @Controller class ReporteFacturacionUBAExportacionController
{
	private @Autowired IReporteFacturacionUBAService reporteFacturacionUBAService;

	@Audit(tipo = Tipo.RPT_FACTURACION_UBA)
	@PreAuthorize("hasPermission('RPT_FACTURACIONUBA', '5')")
	@GetMapping(value="/resumen", params ="accion=exportar")
	public void exportarResumen(@Validated CriterioBusquedaReporteFacturacion criterio, Errors errors,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteMoneda> reporte = this.reporteFacturacionUBAService.buscarResumen(criterio);
        this.reporteFacturacionUBAService.descargarFacturacionUbaResumen(reporte, criterio, request, response);
	}
	
	@Audit(tipo = Tipo.RPT_FACTURACION_UBA)
	@PreAuthorize("hasPermission('RPT_FACTURACIONUBA', '5')")
	@GetMapping(value="/detalle", params ="accion=exportar")
	public void exportarDetalle(@Validated CriterioBusquedaReporteFacturacion criterio, Errors errors,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteMoneda> reporte = this.reporteFacturacionUBAService.buscarDetalle(criterio);
        this.reporteFacturacionUBAService.descargarFacturacionUbaDetalle(reporte, criterio, request, response);

	}
	
	@Audit(tipo = Tipo.RPT_FACTURACION_UBA)
	@PreAuthorize("hasPermission('RPT_FACTURACIONUBA', '5')")
	@GetMapping(value="/miscelaneos", params ="accion=exportar")
	public void exportarAutorizacionesDelDia(@Validated CriterioBusquedaReporteFacturacion criterio, Errors errors,
			HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		ExcepcionUtil.lanzarExcepcionSiHayErrores(errors);
        List<ReporteMoneda> reporte = this.reporteFacturacionUBAService.buscarMiscelaneos(criterio);
        this.reporteFacturacionUBAService.descargarFacturacionUbaMiscelaneos(reporte, criterio, request, response);
	}
}
