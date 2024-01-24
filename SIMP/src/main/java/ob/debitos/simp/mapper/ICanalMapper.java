package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Canal;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICanalMapper extends IMantenibleMapper<Canal>
{
    @Select(value = { "{call MANT_CANAL ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCanal, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Canal> mantener(Parametro parametro);
}
