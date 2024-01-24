package ob.debitos.simp.mapper;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.model.seguridad.CambioContrasenia;

public interface IContraseniaMapper 
{
	@Select("{call SEC_ACTUALIZAR_CONTRASENIA ( "
	        + "#{idUsuario, jdbcType = VARCHAR, mode = IN},"
			+ "#{passwordEncriptado, jdbcType = VARCHAR, mode = IN},"
			+ "#{passwordEncriptadoNuevo, jdbcType = VARCHAR, mode = IN},"
			+ "#{passwordEncriptadoNuevo2, jdbcType = VARCHAR, mode = IN})}")
	@Options(statementType = StatementType.CALLABLE)
	public void actualizarContrasenia(CambioContrasenia contrasenia);
}