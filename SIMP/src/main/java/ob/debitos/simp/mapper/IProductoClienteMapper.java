package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ProductoCliente;
import ob.debitos.simp.model.parametro.Parametro;

public interface IProductoClienteMapper extends IMantenibleMapper<ProductoCliente>
{

    @Select("{call MANT_PRODUCTO_CLIENTE("
            + "#{verbo                      ,jdbcType=VARCHAR   ,mode=IN},"
            + "#{objeto.idEmpresa           ,jdbcType=VARCHAR   ,mode=IN},"
            + "#{objeto.idCliente           ,jdbcType=VARCHAR   ,mode=IN},"
            + "#{objeto.codigoProducto      ,jdbcType=VARCHAR   ,mode=IN},"
            + "#{userAudit                  ,jdbcType=VARCHAR   ,mode=IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<ProductoCliente> mantener(Parametro parametro);

}

