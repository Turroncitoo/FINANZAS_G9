package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaCuenta;
import ob.debitos.simp.model.prepago.CuentaPP;

public interface ICuentaPPMapper
{

    public List<CuentaPP> buscarTodos();

    public List<CuentaPP> buscarPorCriterios(CriterioBusquedaCuenta criterioBusqueda);
    
}
