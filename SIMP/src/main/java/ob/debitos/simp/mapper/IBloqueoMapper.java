package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.movimiento.Bloqueo;
import ob.debitos.simp.model.criterio.CriterioBusquedaBloqueos;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

public interface IBloqueoMapper
{
    
    public List<Bloqueo> buscarBloqueos(CriterioBusquedaTipoDocumento criterioBusquedaTipoDocumento);

    public List<Bloqueo> filtrarBloqueos(CriterioBusquedaBloqueos criterio);
    
}