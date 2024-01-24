package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.Bloqueo;
import ob.debitos.simp.model.criterio.CriterioBusquedaBloqueos;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IBloqueoService
{

    public List<Bloqueo> buscarBloqueos(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<Bloqueo> filtrarBloqueos(CriterioBusquedaBloqueos criterio);

    public void exportarBloqueosPorCriterios(List<Bloqueo> list, CriterioBusquedaBloqueos criterioBusquedaBloqueos, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public void exportarBloqueosPorTipoDocumento(List<Bloqueo> list, CriterioBusquedaTipoDocumento criterioBusquedaBloqueos, HttpServletRequest request, HttpServletResponse response) throws IOException;

}
