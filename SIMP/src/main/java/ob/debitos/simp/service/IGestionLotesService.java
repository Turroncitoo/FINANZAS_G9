package ob.debitos.simp.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import ob.debitos.simp.model.criterio.CriterioBusquedaLoteTarjetas;
import ob.debitos.simp.model.proceso.AfiliacionesCarga;
import ob.debitos.simp.model.tarjetas.Lote;
import ob.debitos.simp.model.tarjetas.LoteDetalle;
import ob.debitos.simp.model.tarjetas.LoteFormulario;
import ob.debitos.simp.model.tarjetas.LoteParametro;
import ob.debitos.simp.model.tarjetas.LoteRecargaDebito;

public interface IGestionLotesService
{

    public void registroAfiliacionesLoteInnominadas(Lote lote);

    public List<AfiliacionesCarga> validarArchivoAfiliacionesNominadas(MultipartFile archivoAfiliaciones);

    public String registroAfiliacionesLoteNominadas(LoteFormulario loteFormulario);

    public void registroRecargaYDebitos(LoteFormulario loteFormulario);

    public void actualizarEstadoLoteAfiliacionesAProcesada(LoteParametro loteParametro);

    public void actualizarEstadoLoteRecargaSolesAProcesada(LoteParametro loteParametro);

    public void actualizarEstadoLoteRecargaDolaresAProcesada(LoteParametro loteParametro);

    public void actualizarEstadoLoteDebitoSolesAProcesada(LoteParametro loteParametro);

    public void actualizarEstadoLoteDebitoDolaresAProcesada(LoteParametro loteParametro);

    public List<Lote> consultaLotesPorCriterios(CriterioBusquedaLoteTarjetas criterio);

    public void exportarLotesPorCriterios(List<Lote> list, CriterioBusquedaLoteTarjetas criterio, HttpServletRequest request, HttpServletResponse response) throws IOException;

    public List<LoteDetalle> consultaLotesDetalleAfiliacion(CriterioBusquedaLoteTarjetas criterio);
    
    public List<LoteDetalle> consultaLotesDetalleRecargaDebito(CriterioBusquedaLoteTarjetas criterio);

    public List<LoteRecargaDebito> validarLoteRecargaDebitos(MultipartFile archivoAfiliaciones);

    public void actualizarLote(Lote lote);

    public void eliminarLote(Integer idLote);

}
