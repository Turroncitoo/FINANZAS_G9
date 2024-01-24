package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.consulta.administrativa.Cuenta;
import ob.debitos.simp.model.criterio.CriterioBusquedaCuenta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;

public interface ICuentaMapper
{

    public List<Cuenta> buscarTodos();

    public List<Cuenta> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<Cuenta> buscarPorCriterios(CriterioBusquedaCuenta criterioBusqueda);

}
