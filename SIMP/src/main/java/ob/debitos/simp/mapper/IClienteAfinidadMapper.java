package ob.debitos.simp.mapper;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ClienteAfinidad;
import ob.debitos.simp.model.parametro.Parametro;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import java.util.List;

public interface IClienteAfinidadMapper extends IMantenibleMapper<ClienteAfinidad>
{
    
    @Select(value = {
            "{call MANT_CLIENTE_AFINIDAD ( "
            + "#{verbo,                 jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idAfinidad,     jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.idCliente,      jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEmpresa,      jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idLogo,         jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit,             jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ClienteAfinidad> mantener(Parametro parametro);

}
