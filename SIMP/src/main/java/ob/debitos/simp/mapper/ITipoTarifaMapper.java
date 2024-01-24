package ob.debitos.simp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.tarifario.TipoTarifa;

public interface ITipoTarifaMapper extends IMantenibleMapper<TipoTarifa>
{
    @Select("{call MANT_TAR_TIPO ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoTarifa, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.aplicaBin,	jdbcType = INTEGER, mode = IN},"
            + "#{objeto.diferenteTran, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoEsquema, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<TipoTarifa> mantener(Parametro parametro);
}