package ob.debitos.simp.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.model.proceso.EntradaProceso;

public interface IProcesoMapper
{
    @Select("{call P_EJECUTAR_PROCESO(" 
    		+ "#{idInstitucion, jdbcType = INTEGER, mode = IN}, "
            + "#{idPrograma, jdbcType = VARCHAR, mode = IN},"
            + "#{idGrupo, jdbcType = VARCHAR, mode= IN},"
            + "#{idUsuario,  jdbcType = VARCHAR, mode = IN},"
            + "#{resultado, jdbcType = INTEGER, mode = OUT})}")
    @Options(statementType = StatementType.CALLABLE)
    public Integer ejecutarProceso(EntradaProceso entradaProceso);
}