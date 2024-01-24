package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Producto;
import ob.debitos.simp.model.parametro.Parametro;

public interface IProductoMapper extends IMantenibleMapper<Producto>
{
    @Select(value = { "{call MANT_PRODUCTOS ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEmpresa, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCliente, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idLogo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoProducto, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descProducto, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoMoneda, jdbcType = INTEGER, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Producto> mantener(Parametro parametro);
}
