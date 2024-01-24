package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Afinidad;
import ob.debitos.simp.model.parametro.Parametro;

public interface IAfinidadMapper extends IMantenibleMapper<Afinidad> 
{
	
	@Select(value = { 
			"{call MANT_AFINIDAD ( "
				+ "#{verbo, 				        jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.idAfinidad, 	        jdbcType = NUMERIC, mode = IN},"
	            + "#{objeto.codigo, 		        jdbcType = VARCHAR, mode = IN},"
				+ "#{objeto.idLogo, 		            jdbcType = VARCHAR, mode = IN},"
				+ "#{objeto.descripcionAfinidad,    jdbcType = VARCHAR, mode = IN},"
				+ "#{userAudit, 		            jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.idGenerado,	            jdbcType = INTEGER, mode = OUT})}" })
    @Options(statementType = StatementType.CALLABLE)
	List<Afinidad> mantener(Parametro parametro);

}
