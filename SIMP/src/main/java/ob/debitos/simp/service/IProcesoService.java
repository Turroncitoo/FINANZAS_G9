package ob.debitos.simp.service;

import ob.debitos.simp.model.proceso.EntradaProceso;

public interface IProcesoService
{
    public Integer ejecutarProceso(EntradaProceso entradaProceso);
}
