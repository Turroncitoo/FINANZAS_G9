package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ReglaContable;
import ob.debitos.simp.model.parametro.Parametro;

public interface IReglaContableMapper extends IMantenibleMapper<ReglaContable>
{
	@Select("{call MANT_REGLA_CONTABLE ( #{verbo, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idComercio		, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idEmpresa		, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.idCliente		, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.operacion		, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.monedaRecarga	, jdbcType = INTEGER, mode = IN},"
			+ "#{objeto.cuentaCargo		, jdbcType = VARCHAR, mode = IN},"
			+ "#{objeto.cuentaAbono		, jdbcType = VARCHAR, mode = IN},"
			+ "#{userAudit				, jdbcType = VARCHAR, mode = IN})}")
	@Options(statementType = StatementType.CALLABLE)
	public List<ReglaContable> mantener(Parametro parametro);
}
