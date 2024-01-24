package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.TipoEmision;
import ob.debitos.simp.model.parametro.Parametro;

public interface ITipoEmisionMapper extends IMantenibleMapper<TipoEmision> {

	@Select(value = { 
			"{call MANT_TIPO_EMISION ( "
				+ "#{verbo, 				jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.idTipoEmision, 	jdbcType = INTEGER, mode = IN},"
	            + "#{objeto.codigo, 		jdbcType = VARCHAR, mode = IN},"
	            + "#{objeto.descripcion,	jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
	public List<TipoEmision> mantener(Parametro parametro); 
	
}
