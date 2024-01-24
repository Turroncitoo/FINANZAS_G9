package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumen;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.model.reporte.ReporteComision;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteTransaccion;
import ob.debitos.simp.service.IReporteComisionCuadroBancoAdministradorService;
import ob.debitos.simp.utilitario.ReporteUtilYarg;

@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
public @Controller class ReporteComisionCuadroBancoAdministradorExportacionController
{

    private @Autowired Logger logger;
    private @Autowired IReporteComisionCuadroBancoAdministradorService reporteService;

    @Audit(tipo = Tipo.RPT_COMIS_CUADRE)
    @PreAuthorize("hasPermission('RPT_COMISCUADRE', '5')")
    @GetMapping(value = "/reporte/comision/moneda", params = "accion=exportar")
    public void descargarResumenCuadreBancoAdministrador(ModelMap model,
            CriterioBusquedaResumen criterioBusqueda,
            HttpServletResponse httpServletResponse) throws IOException
    {
        try
        {
            this.reporteService.descargarResumenCuadreBancoAdministrador(
                    criterioBusqueda, httpServletResponse);
        } catch (Exception e)
        {
            logger.error(
                    "Ocurrio un problema al descargar el reporte de Cuadre Banco Administrador: {}",
                    e);
        }
    }

    @Audit(tipo = Tipo.RPT_SUMARIO_COMP)
    @PreAuthorize("hasPermission('RPT_SUMARIOCOMP', '5')")
    @GetMapping(value = "/reporte/resumen/sumario-compensacion", params = "accion=exportar")
    public ModelAndView buscarSumarioCompensacion(ModelMap model,
            CriterioBusquedaResumen criterioBusquedaResumen)
    {
        Map<String, Object> params = new HashMap<>();
        List<ReporteMoneda> reporteMonedas = this.reporteService
                .buscarSumarioCompensacion(criterioBusquedaResumen);
        params.put("criterioBusqueda", criterioBusquedaResumen);
        params.put("reporte", reporteMonedas);
        model.addAttribute(ReporteUtilYarg.PARAM_TEMPLATE,
                "reporteSumarioCompensacion");
        model.addAttribute(ReporteUtilYarg.PARAM_NOMBRE_REPORTE,
                "Reporte Sumario Compensación");
        model.addAttribute(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS, params);
        return new ModelAndView("jxlsView", model);
    }

    public List<ReporteMoneda> formatearReporte(List<ReporteMoneda> reporte,
            List<ConceptoComision> comisiones)
    {
        for (ReporteMoneda reporteMoneda : reporte)
        {
            for (ReporteTransaccion transaccion : reporteMoneda
                    .getTransacciones())
            {
                List<ReporteComision> nuevoComisiones = new ArrayList<>();
                for (int i = 0; i < comisiones.size(); i++)
                {
                    nuevoComisiones.add(this.buscarComision(
                            transaccion.getComisiones(), comisiones, i));
                }
                transaccion.setComisiones(nuevoComisiones);
                transaccion.setSubTotal(this.calcularSubtotal(transaccion));
            }
        }
        return reporte;
    }

    private ReporteComision buscarComision(List<ReporteComision> tComisiones,
            List<ConceptoComision> comisiones, int idxComision)
    {
        ReporteComision rc = tComisiones.stream().filter(tComision -> comisiones
                .get(idxComision)
                .getIdConceptoComision() == tComision.getIdConceptoComision())
                .findFirst().orElse(null);
        if (rc == null)
        {
            rc = new ReporteComision();
            rc.setIdConceptoComision(
                    comisiones.get(idxComision).getIdConceptoComision());
            rc.setComision(0);
        } else
        {
            if (rc.getRegistroContable().equals("C"))
            {
                rc.setComision(rc.getComision() * (-1));
            }
        }
        return rc;
    }

    /**
     * Calcula el subtotal de comisiones por cada transacción
     * <p>
     * Las comisiones que no se incluyen en esta operación son:
     * <ul>
     * <li>La comisión 1 - THB
     * <li>La comisión 6 - OIF
     * </ul>
     * 
     * @param reporteTxn
     *            la transacción cuyas comisiones se sumarán
     * @return el subtotal calculado
     */
    public double calcularSubtotal(ReporteTransaccion reporteTxn)
    {
        double subTotal = reporteTxn.getMonto();
        for (ReporteComision comision : reporteTxn.getComisiones())
        {
            if (comision.getIdConceptoComision() != 1
                    && comision.getIdConceptoComision() != 6)
            {
                subTotal += comision.getComision();
            }
        }
        return subTotal;
    }
}
