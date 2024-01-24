package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoProcSwitch;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoProcesoSwitchMapper extends IMantenibleMapper<CodigoProcSwitch>
{
    @Select(value = {
            "{call MANT_CODIGO_PROCESO_SWITCH ( " + "#{verbo, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.codigoProcesoSwitch, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.transaccionMonetaria, jdbcType = INTEGER, mode = IN},"
                    + "#{objeto.aplicaInteres, jdbcType = INTEGER, mode = IN},"
                    + "#{objeto.codigoClaseTransaccion, jdbcType = INTEGER, mode = IN},"
                    + "#{objeto.codigoTransaccion, jdbcType = INTEGER, mode = IN},"
                    + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoProcSwitch> mantener(Parametro parametro);
}