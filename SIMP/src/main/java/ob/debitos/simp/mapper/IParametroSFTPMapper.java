package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTP;
import ob.debitos.simp.model.parametro.Parametro;

public interface IParametroSFTPMapper extends IMantenibleMapper<ParametrosSFTP> {
	
    @Select("{call MANT_PARAMETROS_SFTP ( "
            + "#{objeto.host, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.usuario, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.contrasenia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.puerto, jdbcType = INTEGER, mode = IN},"
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
	public List<ParametrosSFTP> mantener(Parametro parametro);
}
