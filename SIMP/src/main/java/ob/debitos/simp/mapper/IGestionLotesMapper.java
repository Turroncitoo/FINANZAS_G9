package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ob.debitos.simp.model.criterio.CriterioBusquedaLoteTarjetas;
import ob.debitos.simp.model.proceso.AfiliacionesCarga;
import ob.debitos.simp.model.tarjetas.AuxLotes;
import ob.debitos.simp.model.tarjetas.Lote;
import ob.debitos.simp.model.tarjetas.LoteDetalle;
import ob.debitos.simp.model.tarjetas.LoteParametro;
import ob.debitos.simp.model.tarjetas.LoteRecargaDebito;

public interface IGestionLotesMapper
{

    public void registroAfiliacionesLoteInnominadas(Lote lote);

    public List<Lote> consultaLotesPorCriterios(CriterioBusquedaLoteTarjetas criterio);

    public List<LoteDetalle> consultaLotesDetalle(CriterioBusquedaLoteTarjetas criterio);

    public List<String> consultaLoteAfiliacionPendiente();

    public List<String> consultaLoteRecargasDebitosPendientes(@Param("operacion")  String operacion, @Param("moneda")  Integer moneda);

    public List<LoteDetalle> consultaLoteActivacionPendiente(CriterioBusquedaLoteTarjetas criterio);

    public List<LoteDetalle> consultaLoteRecargaPendiente(CriterioBusquedaLoteTarjetas criterio);

    public List<AfiliacionesCarga> consultaValidacionAfil();

    public LoteParametro registroAfiliacionesLoteNominadas(LoteParametro loteParametro);

    public LoteParametro registrarLoteRecargas(LoteParametro loteParametro);

    public LoteParametro registrarLoteActivaciones(LoteParametro loteParametro);
    
    public List<LoteRecargaDebito> consultaValidacionRecargaDebito();
    
    public AuxLotes obtenerTodosAuxiliares(AuxLotes auxLotes);

    public void actualizarEstadoLoteProcesado(LoteParametro loteParametro);

    public void mantenerLote(Lote lote);

}
