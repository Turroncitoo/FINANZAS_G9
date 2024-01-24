package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CanalesSegurosWS;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICanalesSegurosWSMapper extends IMantenibleMapper<CanalesSegurosWS>
{
    @Select(value = { "{call MANT_CANALSEGUROWS ("
    		+ "#{verbo, 				jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCanal, 		jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.contrasenia, 	jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.activo, 		jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idCliente, 		jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEmpresa, 		jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CanalesSegurosWS> mantener(Parametro parametro);
}