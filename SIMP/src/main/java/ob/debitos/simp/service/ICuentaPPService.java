package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaCuenta;
import ob.debitos.simp.model.prepago.CuentaPP;

public interface ICuentaPPService
{

    public List<CuentaPP> buscarTodos();

    public List<CuentaPP> buscarPorCriterios(CriterioBusquedaCuenta criterioBusqueda);
    
}
