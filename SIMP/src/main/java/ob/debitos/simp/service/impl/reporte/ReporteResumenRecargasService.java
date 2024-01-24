package ob.debitos.simp.service.impl.reporte;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ob.debitos.simp.mapper.IReporteResumenRecargasMapper;
import ob.debitos.simp.model.RecargasTemp;
import ob.debitos.simp.model.criterio.CriterioBusquedaReporteResumenRecargas;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.reporte.ReporteResumenRecargas;
import ob.debitos.simp.service.IReporteResumenRecargasService;

@Service
public class ReporteResumenRecargasService implements IReporteResumenRecargasService {

	Boolean obtenido;
	Double montoTotal;
	Integer cantidadTotal;

	private @Autowired IReporteResumenRecargasMapper reporteResumenRecargasMapper;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<ReporteResumenRecargas> obtenerReporteResumenRecargar(
			CriterioBusquedaReporteResumenRecargas criterioBusquedaReporteResumenRecargas) {
		return this.reporteResumenRecargasMapper.obtenerReporteResumenRecargas(criterioBusquedaReporteResumenRecargas);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public List<RecargasTemp> obtenerReporteRecargasFormateada(
			CriterioBusquedaReporteResumenRecargas criterioBusquedaReporteResumenRecargas,
			List<Institucion> instituciones) {

		List<ReporteResumenRecargas> reporte = this.reporteResumenRecargasMapper
				.obtenerReporteResumenRecargas(criterioBusquedaReporteResumenRecargas);

		// LLENAR RECARGABLES
		List<String> recargables = new ArrayList<>();
		instituciones.forEach(institucion -> {
			obtenido = false;
			reporte.forEach(r -> {
				if (r.getIdInstitucion() == institucion.getCodigoInstitucion()
						&& r.getDescripcionTipoTarjeta().equals("RECARGABLE")) {
					recargables.add(String.valueOf(r.getMontoRecarga()));
					recargables.add(String.valueOf(r.getTotalRecarga()));
					obtenido = true;
				}
			});
			if (obtenido == false) {
				recargables.add("0.00");
				recargables.add("0");
			}
		});

		// LLENAR NO RECARGABLES
		List<String> noRecargables = new ArrayList<>();
		instituciones.forEach(institucion -> {
			obtenido = false;
			reporte.forEach(r -> {
				if (r.getIdInstitucion() == institucion.getCodigoInstitucion()
						&& r.getDescripcionTipoTarjeta().equals("NO RECARGABLE")) {
					noRecargables.add(String.valueOf(r.getMontoRecarga()));
					noRecargables.add(String.valueOf(r.getTotalRecarga()));
					obtenido = true;
				}
			});
			if (obtenido == false) {
				noRecargables.add("0.00");
				noRecargables.add("0");
			}
		});

		// TOTALES
		List<String> totales = new ArrayList<>();
		instituciones.forEach(institucion -> {
			montoTotal = 0.00;
			cantidadTotal = 0;

			reporte.forEach(r -> {
				if (r.getIdInstitucion() == institucion.getCodigoInstitucion()
						&& r.getDescripcionTipoTarjeta() != null) {
					montoTotal = montoTotal + r.getMontoRecarga();
					cantidadTotal = cantidadTotal + r.getTotalRecarga();

				}
			});
			totales.add(String.valueOf(montoTotal));
			totales.add(String.valueOf(cantidadTotal));

		});

		// FORMATO TEMPORAL
		List<RecargasTemp> recargasTemps = new ArrayList<RecargasTemp>();
		int contador = 0;
		for (int i = 0; i < instituciones.size() * 2; i++) {
			String montoCantidad = "Monto";
			String inst = " " + instituciones.get(contador).getCodigoInstitucion() + " - "
					+ instituciones.get(contador).getDescripcion();
			if ((i + 1) % 2 == 0) {
				montoCantidad = "Cantidad";
				inst = " ";
				contador = contador + 1;
			}
			recargasTemps.add(
					new RecargasTemp(inst, montoCantidad, recargables.get(i), noRecargables.get(i), totales.get(i)));

		}
		return recargasTemps;
	}

}
