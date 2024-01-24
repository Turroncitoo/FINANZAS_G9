package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTPArchivo;
import ob.debitos.simp.model.parametro.Parametro;

public interface IParametroSFTPArchivoMapper extends IMantenibleMapper<ParametrosSFTPArchivo> {
	@Select("{call MANT_PARAMETROS_SFTP_ARCHIVO ( "
			+ "#{objeto.codigoArchivo, jdbcType = VARCHAR, mode = IN},"
    		+ "#{objeto.codigoProceso, jdbcType = VARCHAR, mode = IN},"
    		+ "#{objeto.tipoOperacion, jdbcType = VARCHAR, mode = IN},"
    		+ "#{objeto.origenOriginal, jdbcType = VARCHAR, mode = IN}, "
            + "#{objeto.extensionOrigenOriginal, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.habilitado, jdbcType = BIT, mode = IN},"
            + "#{objeto.validaHeader, jdbcType = BIT, mode = IN},"
            + "#{objeto.numeroBytes, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.diasAumentoRebajoFechaProceso, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.cargaIncremental, jdbcType = BIT, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
	public List<ParametrosSFTPArchivo> mantener(Parametro parametro);
}
