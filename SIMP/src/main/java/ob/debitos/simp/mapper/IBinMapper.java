package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Bin;
import ob.debitos.simp.model.parametro.Parametro;

public interface IBinMapper extends IMantenibleMapper<Bin>
{
    @Select("{call MANT_BINES ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoBIN, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion,	jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoMembresia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoClaseServicio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Bin> mantener(Parametro parametro);
}