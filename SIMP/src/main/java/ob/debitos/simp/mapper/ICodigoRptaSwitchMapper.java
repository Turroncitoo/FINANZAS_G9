package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaSwitch;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoRptaSwitchMapper extends IMantenibleMapper<CodigoRespuestaSwitch>
{
    @Select(value = {
            "{call MANT_CODIGO_RPTA_SWITCH ( " + "#{verbo, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.codigoRespuestaSwitch, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.tipoRespuesta, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.comisionTipoC, jdbcType = INTEGER, mode = IN},"
                    + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoRespuestaSwitch> mantener(Parametro parametro);
}