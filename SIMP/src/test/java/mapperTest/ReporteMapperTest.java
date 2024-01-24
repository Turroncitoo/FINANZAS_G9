package mapperTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.mapper.IReporteAutorizacionMapper;
import ob.debitos.simp.mapper.IReporteComisionCuadroBancoAdministradorMapper;
import ob.debitos.simp.mapper.IReporteTarifarioMapper;
import ob.debitos.simp.mapper.ITransaccionObservadaMapper;
import ob.debitos.simp.model.consulta.movimiento.TransaccionObservada;
import ob.debitos.simp.model.criterio.CriterioBusquedaAutorizacion;
import ob.debitos.simp.model.criterio.CriterioBusquedaResumen;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarifario;
import ob.debitos.simp.model.criterio.CriterioBusquedaTransaccionObservada;
import ob.debitos.simp.model.reporte.ReporteAutorizacion;
import ob.debitos.simp.model.reporte.ReporteMoneda;
import ob.debitos.simp.model.reporte.ReporteTarifario;
import ob.debitos.simp.utilitario.Verbo;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ReporteMapperTest
{
    private @Autowired IReporteComisionCuadroBancoAdministradorMapper reporteMapper;
    private @Autowired IReporteAutorizacionMapper reporteAutorizacionMapper;
    private @Autowired IReporteTarifarioMapper reporteTarifarioMapper;
    private @Autowired ITransaccionObservadaMapper transaccionObservadaMapper;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarComisionMonedaResumenPorInstitucionTest()
    {
        CriterioBusquedaResumen parametro = new CriterioBusquedaResumen();
        parametro.setCodigoInstitucion(50);
        LocalDate date = LocalDate.of(2017, 05, 30);
        Date fechaInicio = Date
                .from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        parametro.setFechaProceso(fechaInicio);
        parametro.setVerbo(Verbo.COMISION_BANCO_ADMINISTRADOR);
        List<ReporteMoneda> monedas = reporteMapper
                .buscarComisionesCuadroBancoAdministrador(parametro);
        System.out.println(monedas);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarGestionAutorizacionTest()
    {
        CriterioBusquedaAutorizacion criterio = new CriterioBusquedaAutorizacion();
        criterio.setVerbo("CANAL_MES");
        criterio.setRolTransaccion(1);
        criterio.setCodigoRespuestaTransaccion(0);
        LocalDate fechaInicioLocalDate = LocalDate.of(2017, 05, 28);
        Date fechaInicio = Date.from(
                fechaInicioLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        criterio.setFechaInicio(fechaInicio);
        LocalDate fechaFinLocalDate = LocalDate.of(2017, 05, 30);
        Date fechaFin = Date
                .from(fechaFinLocalDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        criterio.setFechaFin(fechaFin);
        List<ReporteAutorizacion> canales = reporteAutorizacionMapper
                .buscarAutorizaciones(criterio);
        System.out.println(canales);

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void buscarEmisorResumenTest()
    {
        CriterioBusquedaTarifario parametro = new CriterioBusquedaTarifario();
        LocalDate date = LocalDate.of(2017, 05, 30);
        Date fechaInicio = Date
                .from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        parametro.setFechaInicio(fechaInicio);
        parametro.setFechaFin(fechaInicio);
        parametro.setVerbo(Verbo.ADQUIRENTE_RESUMEN);
        List<ReporteTarifario> tarifariosEmisor = reporteTarifarioMapper.reporte(parametro);
        tarifariosEmisor.stream().forEach(tarEmisor -> {
            System.out.println(tarEmisor.toString());
        });

    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void transaccionesObservadasReporteTest()
    {
        CriterioBusquedaTransaccionObservada parametro = new CriterioBusquedaTransaccionObservada();
        LocalDate date = LocalDate.of(2017, 05, 30);
        LocalDate date2 = LocalDate.of(2017, 06, 01);
        Date fechaInicio = Date
                .from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date fechaFin = Date.from(date2.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        parametro.setFechaInicioProceso(fechaInicio);
        parametro.setFechaFinProceso(fechaFin);
        parametro.setIdOrigenArchivo(3);
        List<TransaccionObservada> txnObs = transaccionObservadaMapper
                .buscarConciliacionObservada(parametro);
        txnObs.stream().forEach(obs -> {
            System.out.println(obs.toString());
        });

    }
}