package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.MotivoReclamo;
import ob.debitos.simp.model.parametro.Parametro;

public interface IMotivoReclamoMapper extends IMantenibleMapper<MotivoReclamo>
{
    @Select(value = { "{call MANT_MOTIVO_RECLAMO ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idMotivo, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<MotivoReclamo> mantener(Parametro parametro);
}