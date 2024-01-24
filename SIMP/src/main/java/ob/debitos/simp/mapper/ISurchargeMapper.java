package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.tarifario.Surcharge;

public interface ISurchargeMapper extends IMantenibleMapper<Surcharge>
{
    @Select("{call MANT_TAR_SURCHARGE ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.nivel, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.rangoInicial, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.rangoFinal, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoMoneda, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.surchargeFlat, jdbcType = DOUBLE, mode = IN},"
            + "#{objeto.surchargePorc, jdbcType = DOUBLE, mode = IN},"
            + "#{objeto.tarifaFija, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.aplicaIgv, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Surcharge> mantener(Parametro parametro);
}