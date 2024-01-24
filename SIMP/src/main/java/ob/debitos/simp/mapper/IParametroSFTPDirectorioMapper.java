package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPDirectorio;
import ob.debitos.simp.model.parametro.Parametro;

public interface IParametroSFTPDirectorioMapper extends IMantenibleMapper<ParametrosSFTPDirectorio> { 
    @Select("{call MANT_PARAMETROS_SFTP_DIRECTORIO ( "
    		+ "#{objeto.codigoProceso, jdbcType = VARCHAR, mode = IN},"
    		+ "#{objeto.tipoOperacion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.directorioOrigen, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.directorioDestino, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.borraFichero, jdbcType = BIT, mode = IN},"
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
	public List<ParametrosSFTPDirectorio> mantener(Parametro parametro);
}
