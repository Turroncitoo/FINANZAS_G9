package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.Usuario;

public interface IUsuarioMapper extends IMantenibleMapper<Usuario>
{
    
    @Select("{call MANT_USUARIOS ( " 
            + "#{verbo,                         jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.idUsuario,              jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.idPerfil,               jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.activo,                 jdbcType = BIT,     mode = IN},"
            + "#{objeto.contrasenia,            jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.contraseniaEncriptada,  jdbcType = VARCHAR, mode = IN}," 
            + "#{objeto.cambioContrasenia,      jdbcType = BOOLEAN, mode = IN},"
            + "#{objeto.visualizaPAN,           jdbcType = BOOLEAN, mode = IN}," 
            + "#{userAudit,                     jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Usuario> mantener(Parametro parametro);

    @Select("{call SEC_NUMERO_MAX_DIAS ( #{usuario, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public Integer obtenerCantidadDiasCaducidadContrasenia(String usuario);

}