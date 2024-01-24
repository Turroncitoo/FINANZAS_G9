package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.PerfilRecurso;

public interface IPerfilRecursoMapper extends IMantenibleMapper<PerfilRecurso> 
{
	@Select(value = { "{call MANT_PERFIL_RECURSO ( "
	        + "#{verbo, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idPerfil, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idRecurso, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.permiso, jdbcType = VARCHAR, mode = IN}," 
			+ "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
	@Options(statementType = StatementType.CALLABLE)
	public List<PerfilRecurso> mantener(Parametro parametro);
	
	@Delete("{call ELIMINAR_PERMISOS ( #{idPerfil, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public void eliminarPermisos(@Param("idPerfil") String idPerfil);
}