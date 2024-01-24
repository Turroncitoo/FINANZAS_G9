package ob.debitos.simp.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.model.proceso.ContabilizacionAutomatica;

public interface IContabilizacionAutomaticaMapper
{
    @Select(value = {"{call P_CONTAB_AUTOMATICA_LOG_EMI ( "
            + "#{codigoInstitucion, jdbcType = NUMERIC, mode = IN},"
            + "#{fechaProceso, jdbcType = DATE, mode = IN},"
            + "#{usuario, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public Integer contabilizarAutomaticamente(
            ContabilizacionAutomatica contabilizacionAutomatica);
    
    @Select(value = {"{call P_CONTAB_AUTOMATICA_SWITCH_EMI ( "
            + "#{codigoInstitucion, jdbcType = NUMERIC, mode = IN},"
            + "#{fechaProceso, jdbcType = DATE, mode = IN},"
            + "#{usuario, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public Integer contabilizarAutomaticamenteSw(
            ContabilizacionAutomatica contabilizacionAutomatica);
}