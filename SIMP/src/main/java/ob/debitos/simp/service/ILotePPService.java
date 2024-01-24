package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaLote;
import ob.debitos.simp.model.prepago.LoteInnominadoPP;
import ob.debitos.simp.model.prepago.LotePP;

public interface ILotePPService
{

    public void registrarLote(LotePP lotePP);

    public List<LotePP> buscarTodos();

    public List<LotePP> obtenerLotesAfiliacion();

    public void registrarLoteInnominadas(LoteInnominadoPP loteInnominadoPP);

    public void procesarLoteAfiliaciones(Integer nIdLote);

    public boolean existeLoteRecarga(Integer nIdLote);

    public List<LotePP> obtenerLotesPorCriterios(CriterioBusquedaLote criterioBusquedaLote);
    
}
