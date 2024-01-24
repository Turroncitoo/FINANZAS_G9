package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoTransaccion;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoTransaccionMapper extends IMantenibleMapper<CodigoTransaccion>
{
    @Select(value = { "{call MANT_CODIGO_TRANSACCION (#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoClaseTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.transaccionMiscelanea, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.compensaFondos, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.compensaComisiones, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.registroContable, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoTransaccion> mantener(Parametro parametro);
}