package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.model.criterio.CriterioBusquedaLote;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.prepago.LoteInnominadoPP;
import ob.debitos.simp.model.prepago.LotePP;

public interface ILotePPMapper
{

    public List<LotePP> buscarTodos();

    @Select(value = { "{call MANT_LOTE ( " 
            + "#{verbo, 					jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.nIdLote, 			jdbcType = INTEGER, mode = IN}," 
            + "#{objeto.nEstadoLote, 		jdbcType = INTEGER, mode = IN},"
            + "#{objeto.dFechaRegistro, 	jdbcType = DATE,    mode = IN}," 
            + "#{objeto.dFechaModificacion, jdbcType = DATE,    mode = IN}," 
            + "#{objeto.nInstancia, 		jdbcType = INTEGER, mode = IN},"
            + "#{objeto.nIdInstitucion, 	jdbcType = INTEGER, mode = IN}," 
            + "#{objeto.sNombreArchivo, 	jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.nSecuencia, 		jdbcType = INTEGER, mode = IN},"
            + "#{userAudit, 				jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    @ResultMap("mapLoteCompleto")
    public List<LotePP> mantener(Parametro param);

    @Select(value = { "{call sp_InsertarLote(#{parametroXml})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<LotePP> insertarLote(String parametroXml);

    @Select(value = { "{call sp_InsertarLoteRecarga(#{parametroXml})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<LotePP> insertarLoteRecarga(String parametroXml);

    @Select(value = { "{call sp_ObtenerLotesInstancia (#{instancia})}" })
    @Options(statementType = StatementType.CALLABLE)
    @ResultMap("mapLoteCompleto")
    public List<LotePP> obtenerLotes(int instancia);

    @Select(value = { "{call sp_InsertarLoteEx ( "
            + "#{nIdInstitucion, 	jdbcType = NUMERIC, mode = IN}," 
            + "#{dFechaProceso, 	jdbcType = DATE,    mode = IN}," 
            + "#{codigoBIN, 		jdbcType = VARCHAR, mode = IN},"
            + "#{codigoSubBIN, 		jdbcType = VARCHAR, mode = IN}," 
            + "#{ivAfinidad, 		jdbcType = VARCHAR, mode = IN}," 
            + "#{tipoEmision, 		jdbcType = VARCHAR, mode = IN},"
            + "#{nEstadoLote, 		jdbcType = INTEGER, mode = IN}," 
            + "#{nInstancia, 		jdbcType = INTEGER, mode = IN}," 
            + "#{nIdClienteLote, 	jdbcType = VARCHAR, mode = IN}, "
            + "#{nIdEmpresa, 		jdbcType = VARCHAR, mode = IN}, " 
            + "#{cantidadLote, 		jdbcType = INTEGER, mode = IN}, " 
            + "#{nTipoAfiliacion,   jdbcType = INTEGER, mode = IN}, "
            + "#{nIdLote, 			jdbcType = NUMERIC, mode = OUT})}" })
    @Options(statementType = StatementType.CALLABLE)
    public Integer insertarLoteInnominadas(LoteInnominadoPP loteInnominadoDTO);

    @Select(value = { "{call sp_ProcesarLoteAfiliaciones(#{nIdLote})}" })
    @Options(statementType = StatementType.CALLABLE)
    public void procesarLoteAfiliaciones(Integer nIdLote);

    @Select(value = { "Select nIdLote from MovLotePP where nIdLoteOriginal = #{nIdLoteOriginal} AND nInstancia = 3 AND nEstadoLote = 1 " })
    public Integer obtenerLoteRecarga(Integer nIdLoteOriginal);

    public List<LotePP> obtenerLotesPorCriterios(CriterioBusquedaLote criterioBusquedaLote);
    
}
