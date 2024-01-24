package serviceTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenAutorizacionSwDmpLog;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.model.reporte.ReporteResumenAutorizacion;
import ob.debitos.simp.model.reporte.ReporteResumenSwDmpLog;
import ob.debitos.simp.model.reporte.ReporteResumenTransaccionAprobadaAgencia;
import ob.debitos.simp.service.IReporteResumenMovimientoService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ReporteResumenMovimientoServiceTest
{    
    private @Autowired IReporteResumenMovimientoService resumenMovimientoService;

//    @Test
    public void listarAutorizaciones()
    {        
        List<ReporteResumenAutorizacion> reportes = resumenMovimientoService
                .buscarResumenAutorizacion(crearCriterioAutorizacionSwDmp());
        reportes.stream().forEach(reporte -> {
            System.out.println(reporte.toString());
        });
        System.out.println("TAMAÑO: " + reportes.size());
    }
    
//    @Test
    public void listarSwDmpLogs()
    {        
        List<ReporteResumenSwDmpLog> reportes = resumenMovimientoService
                .buscarResumenSwDmpLog(crearCriterioAutorizacionSwDmp());
        reportes.stream().forEach(reporte -> {
            System.out.println(reporte.toString());
        });
        System.out.println("TAMAÑO: " + reportes.size());
    }
    
    @Test
    public void listarTransaccionesAprobadas()
    {        
        List<ReporteResumenTransaccionAprobadaAgencia> reportes = resumenMovimientoService
                .buscarResumenTransaccionAprobadaAgencia(crearCriterioTransaccionAprobada());
        reportes.stream().forEach(reporte -> {
            System.out.println(reporte.toString());
        });
        System.out.println("TAMAÑO: " + reportes.size());
    }

    private CriterioBusquedaResumenAutorizacionSwDmpLog crearCriterioAutorizacionSwDmp()
    {
        CriterioBusquedaResumenAutorizacionSwDmpLog criterio = new CriterioBusquedaResumenAutorizacionSwDmpLog();
        criterio.setRolTransaccion(99);
        criterio.setIdCanal(999);
        criterio.setCodigoProceso("999");
        LocalDate fechaInicioLocalDate = LocalDate.of(2017, 05, 29);
        Date fechaInicio = Date.from(
                fechaInicioLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        criterio.setFechaInicio(fechaInicio);
        LocalDate fechaFinLocalDate = LocalDate.of(2017, 11, 01);
        Date fechaFin = Date
                .from(fechaFinLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        criterio.setFechaFin(fechaFin);
        return criterio;
    }
    
    private CriterioBusquedaResumenTransaccionAprobadaAgencia crearCriterioTransaccionAprobada()
    {
        CriterioBusquedaResumenTransaccionAprobadaAgencia criterio = new CriterioBusquedaResumenTransaccionAprobadaAgencia();
        criterio.setIdAgencia(99999);
        criterio.setIdEstadoCivil("99");
        criterio.setIdSexo("99");
        LocalDate fechaInicioLocalDate = LocalDate.of(2017, 05, 29);
        Date fechaInicio = Date.from(
                fechaInicioLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        criterio.setFechaInicio(fechaInicio);
        LocalDate fechaFinLocalDate = LocalDate.of(2017, 11, 01);
        Date fechaFin = Date
                .from(fechaFinLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        criterio.setFechaFin(fechaFin);
        return criterio;
    }
}
