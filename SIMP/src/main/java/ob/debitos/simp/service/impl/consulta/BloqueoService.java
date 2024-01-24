package ob.debitos.simp.service.impl.consulta;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import ob.debitos.simp.model.criterio.CriterioBusquedaBloqueos;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.IExportacionPOIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IBloqueoMapper;
import ob.debitos.simp.model.consulta.movimiento.Bloqueo;
import ob.debitos.simp.service.IBloqueoService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class BloqueoService implements IBloqueoService
{

    private @Autowired IBloqueoMapper bloqueoMapper;

    private @Autowired IExportacionPOIService<Bloqueo> exportBloqueos;

    private static final String[][] cabeceraExportacion = {
            {"Fecha Proceso", "fechaProceso", "", "formatFecha", "-1"},
            {"Institución", "codigoInstitucion", "descripcionInstitucion", "formatCadena", "-1"},
            {"Empresa", "idEmpresa", "descEmpresa", "formatCadena", "-1"},
            {"Cliente", "idCliente", "descCliente", "formatCadena", "-1"},
            {"Fecha Bloqueo", "fechaBloqueoCadena", "horaBloqueo", "formatCadenaCentrada", "-1"},
            {"Tipo Documento", "tipoDocumento", "descripcionTipoDocumento", "formatCadena", "-1"},
            {"Número Documento", "numeroDocumento", "", "formatCadena", "-1"},
            {"Número Tarjeta", "numeroTarjeta", "", "formatCadena", "-1"},
            {"Código Bloqueo", "codigoBloqueo", "", "formatCadena", "-1"},
            {"Tipo Bloqueo", "tipoBloqueo", "descripcionTipoBloqueo", "formatCadena", "-1"},
            {"Comentario", "comentario", "", "formatCadena", "-1"}
    };

    @Truncable(clase = Bloqueo.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Bloqueo> buscarBloqueos(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento)
    {
        return bloqueoMapper.buscarBloqueos(criterioBusquedaTipoDocumento);
    }

    @Truncable(clase = Bloqueo.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Bloqueo> filtrarBloqueos(CriterioBusquedaBloqueos criterio)
    {
        return bloqueoMapper.filtrarBloqueos(criterio);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarBloqueosPorCriterios(List<Bloqueo> list, CriterioBusquedaBloqueos criterioBusquedaBloqueos, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Fecha Proceso", criterioBusquedaBloqueos.getDescripcionRangoFechasProceso()},
                {"Fecha Bloqueo", criterioBusquedaBloqueos.getDescripcionRangoFechasBloqueo()},
                {"Institución", criterioBusquedaBloqueos.getDescripcionInstitucion()},
                {"Empresa", criterioBusquedaBloqueos.getDescripcionEmpresa()},
                {"Cliente", criterioBusquedaBloqueos.getDescripcionCliente()},
                {"Número Tarjeta", criterioBusquedaBloqueos.getNumeroTarjeta()},
        };
        this.exportBloqueos.generarExportacionNormal("Consulta de Bloqueos - Criterios", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void exportarBloqueosPorTipoDocumento(List<Bloqueo> list, CriterioBusquedaTipoDocumento criterioBusquedaBloqueos, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        String[][] filtros = {
                {"Tipo Documento", criterioBusquedaBloqueos.getDescripcionTipoDocumento()},
                {"Número Documento", criterioBusquedaBloqueos.getNumeroDocumento()}
        };
        this.exportBloqueos.generarExportacionNormal("Consulta de Bloqueos - Tipo Documento", list, filtros, cabeceraExportacion, false, 3, 85, request, response);
    }
}
