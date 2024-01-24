package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametrosInterfaceContable;

import ob.debitos.simp.model.parametro.Parametro;


public interface IParametroInterfaceContableMapper extends IMantenibleMapper<ParametrosInterfaceContable> {
	
	 @Select("{call MANT_PARAM_INT_CNT  ( "
			 	+ "#{verbo,                    jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.usuarioAlegra,     jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.tokenAlegra,       jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.cuentaContableURI, jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.activo,            jdbcType = BIT, 	   mode = IN},"
	            + "#{objeto.pagoURI,		   jdbcType = VARCHAR, mode = IN})}")
	    @Options(statementType = StatementType.CALLABLE)
		public List<ParametrosInterfaceContable> mantener(Parametro parametro);
}
