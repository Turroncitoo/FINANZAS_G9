package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Membresia;
import ob.debitos.simp.model.parametro.Parametro;

public interface IMembresiaMapper extends IMantenibleMapper<Membresia>
{
    @Select("{call MANT_MEMBRESIAS ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoMembresia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Membresia> mantener(Parametro parametro);
}