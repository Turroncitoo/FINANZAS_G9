package ob.debitos.simp.controller.reporte.exportacion;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;

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
import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteInterfaceContable;
import ob.debitos.simp.model.reporte.ReporteInterfaceContable;
import ob.debitos.simp.service.IReporteInterfaceContableService;
import ob.debitos.simp.service.excepcion.BadRequestException;
import ob.debitos.simp.utilitario.ValidatorUtil;

@Vista
@Audit(accion = Accion.Reporte, comentario = Comentario.Reporte)
@RequestMapping("/reporte")
public @Controller class InterfaceContableExportacionController
{
    private @Autowired IReporteInterfaceContableService reporteInterfaceContableService;

    @Audit(tipo = Tipo.RPT_INTER_CONTABLE)
    @PreAuthorize("hasPermission('RPT_INTER_CONTABLE','5')")
    @GetMapping(value = "/interfaceContable", params = "accion=exportar")
    public void descargaInterfaceContable(@Validated CriterioBusquedaReporteInterfaceContable criterioBusquedaReporteInterfaceContable, Errors errors, 
                HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        if (errors.hasErrors())
        {
            throw new BadRequestException(
                    ValidatorUtil.obtenerMensajeValidacionError(errors));
        }
        List<ReporteInterfaceContable> list = this.reporteInterfaceContableService
                .buscarInterfaceContablePorCriterio(criterioBusquedaReporteInterfaceContable);
        this.reporteInterfaceContableService.generarReporteInterfaceContable(list, criterioBusquedaReporteInterfaceContable, request, response);
    }
}
