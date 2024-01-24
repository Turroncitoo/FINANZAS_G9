package ob.debitos.simp.service.impl.proceso;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IContabilizacionAutomaticaMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.proceso.ContabilizacionAutomatica;
import ob.debitos.simp.model.proceso.LogControlProgramaResumen;
import ob.debitos.simp.service.IContabilizacionAutomaticaService;
import ob.debitos.simp.service.IInstitucionService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.IReporteContablePrepagoService;

@Service
public class ContabilizacionAutomaticaService implements IContabilizacionAutomaticaService {
	private @Autowired IContabilizacionAutomaticaMapper contabilizacionAutomaticaMapper;
	private @Autowired IInstitucionService institucionService;
	private @Autowired IParametroGeneralService parametroGeneralService;
	private @Autowired IReporteContablePrepagoService reporteContablePrepagoService;

	@Transactional(propagation = Propagation.REQUIRED)
	public Integer contabilizarAutomaticamente(LogControlProgramaResumen logControlProgramaResumen) {
		Date fechaProceso = parametroGeneralService.buscarFechaProceso();
		Integer codigoInstitucion = parametroGeneralService.buscarCodigoInstitucion();
		List<Institucion> instituciones = this.institucionService.buscarPorInstitucionEmpresa();
		Integer codigoRespuesta = 0;
		
		ContabilizacionAutomatica.ContabilizacionAutomaticaBuilder contabilizacionParamsBuilder;
		contabilizacionParamsBuilder = ContabilizacionAutomatica.builder()
				.codigoInstitucion(codigoInstitucion)
				.fechaProceso(fechaProceso);
		ContabilizacionAutomatica contabilizacionAutomatica = contabilizacionParamsBuilder.build();
		
		if (logControlProgramaResumen.getCodigoPrograma().equals("CEMI")) {
			codigoRespuesta = contabilizacionAutomaticaMapper.contabilizarAutomaticamente(contabilizacionAutomatica);
		} else if (logControlProgramaResumen.getCodigoPrograma().equals("CEMS")) {
			codigoRespuesta = contabilizacionAutomaticaMapper.contabilizarAutomaticamenteSw(contabilizacionAutomatica);
			// Archivos
		} else {
			for (Institucion institucion: instituciones) {
					try {
						if (logControlProgramaResumen.getCodigoPrograma().equals("AFON")) {
							CriterioBusquedaArchivoContablePrepago criterioBusquedaArchivoContablePrepago = CriterioBusquedaArchivoContablePrepago
									.builder().fechaProceso(parametroGeneralService.buscarFechaProceso())
									.idInstitucion(institucion.getCodigoInstitucion()).tipo("FONDOS".toUpperCase())
									.build();
							codigoRespuesta = reporteContablePrepagoService
									.generarArchivoContable(criterioBusquedaArchivoContablePrepago);
	
						} else if (logControlProgramaResumen.getCodigoPrograma().equals("ACOM")) {
							CriterioBusquedaArchivoContablePrepago criterioBusquedaArchivoContablePrepago = CriterioBusquedaArchivoContablePrepago
									.builder().fechaProceso(parametroGeneralService.buscarFechaProceso())
									.idInstitucion(institucion.getCodigoInstitucion()).tipo("COMISIONES".toUpperCase())
									.build();
							codigoRespuesta = reporteContablePrepagoService
									.generarArchivoContable(criterioBusquedaArchivoContablePrepago);
						}
				} catch (IOException e) {
					return -1;
				}
			}
		}
		return codigoRespuesta;
	}
}
