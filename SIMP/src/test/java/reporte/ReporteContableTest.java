package reporte;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xlsx4j.org.apache.poi.ss.usermodel.DateUtil;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.service.IReporteContablePrepagoService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class ReporteContableTest {

	 private @Autowired IReporteContablePrepagoService reporteContablePrepagoService;

	    @Test
	    @Transactional(propagation = Propagation.REQUIRES_NEW)
	    public void test() throws IOException
	    {
			CriterioBusquedaArchivoContablePrepago criterioBusquedaArchivoContablePrepago = 
					CriterioBusquedaArchivoContablePrepago.builder()
						.fechaProceso(DateUtil.parseYYYYMMDDDate("2017/05/31"))
						//.idEmpresa("2")
						.tipo("FONDOS")
						.build();
	        System.out.println(reporteContablePrepagoService.generarArchivoContable(
	        		criterioBusquedaArchivoContablePrepago));
	    }
}
