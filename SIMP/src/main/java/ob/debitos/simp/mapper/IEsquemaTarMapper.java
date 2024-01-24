package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.tarifario.EsquemaTar;

public interface IEsquemaTarMapper extends IMantenibleMapper<EsquemaTar>
{
    @Select("{call MANT_TAR_ESQUEMA ("
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoEsquema, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<EsquemaTar> mantener(Parametro parametro);
}
