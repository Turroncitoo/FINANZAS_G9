package ob.debitos.simp.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoFacturacion;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoFacturacionMapper extends IMantenibleMapper<CodigoFacturacion>  {
	@Select(value = {
            "{call MANT_CODIGO_FACTURACION ("
                    + "#{verbo, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.idCodigoFacturacion, jdbcType = INTEGER, mode = IN},"
                    + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
                    + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoFacturacion> mantener(Parametro parametro);

}
