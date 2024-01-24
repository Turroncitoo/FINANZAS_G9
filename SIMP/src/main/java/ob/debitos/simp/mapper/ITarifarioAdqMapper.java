package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.tarifario.TarifarioAdq;

public interface ITarifarioAdqMapper extends IMantenibleMapper<TarifarioAdq>
{
    @Select("{call MANT_TAR_ADQUIRENTE ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.nivel, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoTipoTarifa, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.rangoInicial, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.rangoFinal, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoMoneda, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.tarifaFlat, jdbcType = DOUBLE, mode = IN},"
            + "#{objeto.tarifaPorc, jdbcType = DOUBLE, mode = IN},"
            + "#{objeto.tarifaFija, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.aplicaIgv, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<TarifarioAdq> mantener(Parametro parametro);
}