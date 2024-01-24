package ob.debitos.simp.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.model.proceso.OrdenEjecucion;

public interface IOrdenEjecucionMapper
{
    @Select("{call VALIDAR_EJECUCION_PROCESO (" 
            + "#{idGrupo, jdbcType = VARCHAR, mode = IN},"
            + "#{idPrograma, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public Integer buscarProcesosProgramasNoEjecutados(OrdenEjecucion ordenEjecucion);
}