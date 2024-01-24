package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.proceso.LogControlProgramaResumen;

public interface ILogControlProgramaResumenMapper extends IMantenibleMapper<LogControlProgramaResumen>
{
    @Select("{call MANT_LOG_CONTROL_PROGRAMA_RESUMEN ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoGrupo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoPrograma, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoSubModulo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.fechaProceso, jdbcType = DATE, mode = IN},"
            + "#{objeto.fechaInicio, jdbcType = DATE, mode = IN},"
            + "#{objeto.fechaFin, jdbcType = DATE, mode = IN},"
            + "#{objeto.horaInicio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.horaFin, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.estado, jdbcType = NUMERIC, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<LogControlProgramaResumen> mantener(Parametro parametro);
}