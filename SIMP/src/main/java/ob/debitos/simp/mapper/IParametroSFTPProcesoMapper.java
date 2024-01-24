package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPProceso;
import ob.debitos.simp.model.parametro.Parametro;

public interface IParametroSFTPProcesoMapper extends IMantenibleMapper<ParametrosSFTPProceso> {
	@Select("{call MANT_PARAMETROS_SFTP_PROCESO ( "
    		+ "#{objeto.codigo, jdbcType = VARCHAR, mode = IN},"
    		+ "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
	public List<ParametrosSFTPProceso> mantener(Parametro parametro);
}
