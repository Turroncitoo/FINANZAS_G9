package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlPrograma;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.LogControlPrograma;

public interface ILogControlProgramaService
{
    public int registrarLogControlPrograma(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<LogControlPrograma> buscarPorCriterioBusqueda(
            CriterioBusquedaLogControlPrograma criterioBusqueda);
    
}