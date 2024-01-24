package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ob.debitos.simp.model.parametro.ParametroLote;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.model.proceso.ArchivoLote;
import ob.debitos.simp.model.proceso.LoteAfiliacion;
import ob.debitos.simp.model.reporte.ReporteMoneda;

public interface IArchivoLoteMapper {
	
	public void registrarLote(ArchivoLote archivoLote);
	
	public void registrarControlLote(Integer idLote);
	
	public void actualizarEstadoLote(@Param("idLote") Integer idLote, @Param("idEstado") Integer idEstado);
	
	public void registrarAfiliacionRespuestaUBA(Integer idInstitucion);
	
	public List<LoteAfiliacion> buscarReqAfiliacionPendientesEmision(@Param("idInstitucion") Integer idInstitucion);

	public List<ReporteMoneda> buscarReqRecargasPendientesEmision(@Param("idInstitucion") Integer idInstitucion);
	
	public List<LoteAfiliacion> buscarReqActivacionPendientesEmision(@Param("idInstitucion") Integer idInstitucion);
	
	public List<LotePP> actualizarLote(ParametroLote parametro);

}
