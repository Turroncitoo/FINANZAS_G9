package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.model.prepago.TarjetaPP;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaFiltroPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaRangoFechaPrepago;
import ob.debitos.simp.model.prepago.criterio.CriterioBusquedaTipoDocumentoPrepago;

public interface ITarjetaPPMapper
{
    public List<TarjetaPP> buscarTodos();

    public List<String> buscarCodigoSeguimientoPorDocumento(
            CriterioBusquedaTipoDocumentoPrepago criterioBusquedaTipoDocumento);

    public List<String> buscarCodigoSeguimientoPorTarjeta(
            CriterioBusquedaFiltroPrepago criterioBusquedaMovimientosPrepago);

    public List<TarjetaPP> buscarBloqueosPorRangoFecha(
            CriterioBusquedaRangoFechaPrepago criterioBusquedaRangoFechaPrepago);

    public List<TarjetaPP> buscarPorTipoDocumento(
            CriterioBusquedaTipoDocumento criterioBusqueda);

    public List<TarjetaPP> buscarPorCriterios(
            CriterioBusquedaTarjeta criterioBusqueda);
}
