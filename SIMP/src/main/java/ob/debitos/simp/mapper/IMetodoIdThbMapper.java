package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.MetodoIdThb;
import ob.debitos.simp.model.parametro.Parametro;

public interface IMetodoIdThbMapper extends IMantenibleMapper<MetodoIdThb>
{
    @Select(value = { "{call MANT_METODO_ID_THB ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idMetodo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<MetodoIdThb> mantener(Parametro parametro);
}