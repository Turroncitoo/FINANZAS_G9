package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.proceso.SubModulo;

public interface ISubModuloMapper extends IMantenibleMapper<SubModulo>
{
    @Select("{call MANT_SUB_MODULOS ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoSubModulo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<SubModulo> mantener(Parametro parametro);
}