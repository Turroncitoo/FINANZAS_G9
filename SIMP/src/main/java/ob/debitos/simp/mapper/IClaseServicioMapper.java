package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ClaseServicio;
import ob.debitos.simp.model.parametro.Parametro;

public interface IClaseServicioMapper extends IMantenibleMapper<ClaseServicio>
{
    @Select("{call MANT_CLASES_SERVICIO ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoClaseServicio, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoMembresia, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<ClaseServicio> mantener(Parametro parametro);
}