package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaLogControlPrograma;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.proceso.LogControlPrograma;

public interface ILogControlProgramaMapper extends IMantenibleMapper<LogControlPrograma>
{
	@Select("{call INSERT_LOG_CONTROL_PROGRAMA ( "
            + "#{objeto.fechaProceso, jdbcType = DATE, mode = IN},"
            + "#{objeto.estado, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.mensaje, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.registro, jdbcType = INTEGER, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoGrupo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoPrograma, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoSubModulo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.horaInicio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.horaFin, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tiempoProceso, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.tipoEjecucion, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<LogControlPrograma> mantener(Parametro parametro);

    public List<LogControlPrograma> buscarPorCriterioBusqueda(
            CriterioBusquedaLogControlPrograma criterioBusqueda);
}