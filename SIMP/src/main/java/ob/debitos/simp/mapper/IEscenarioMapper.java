package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.tarifario.Escenario;

public interface IEscenarioMapper extends IMantenibleMapper<Escenario>
{
    @Select("{call MANT_TAR_ESCENARIO ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoMembresia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoClaseServicio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoClaseTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoOrigen, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoTipoTarifa, jdbcType = INTEGER, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Escenario> mantener(Parametro parametro);
}
