package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Error;
import ob.debitos.simp.model.parametro.Parametro;

public interface IErrorMapper extends IMantenibleMapper<Error>
{
    @Select(value = {
            "{call MANT_ERRORES ( #{verbo, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.idError, jdbcType = INTEGER, mode = IN},"
                    + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
                    + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Error> mantener(Parametro parametro);
}
