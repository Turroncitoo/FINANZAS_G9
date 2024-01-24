package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.IndCorreoTelefono;
import ob.debitos.simp.model.parametro.Parametro;

public interface IIndCorreoTelefonoMapper extends IMantenibleMapper<IndCorreoTelefono>
{
    @Select(value = { "{call MANT_IND_CORREO_TELEFONO ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoIndCorreoTelefono, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<IndCorreoTelefono> mantener(Parametro parametro);
}