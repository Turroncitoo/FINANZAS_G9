package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Logo;
import ob.debitos.simp.model.parametro.Parametro;

public interface ILogoMapper extends IMantenibleMapper<Logo>
{

    @Select("{call MANT_LOGOS ( "
            + "#{verbo,                         jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idInstitucion,          jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idLogo,                 jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion,            jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idMembresia,            jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idClaseServicio,        jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.longitudBin,            jdbcType = INTEGER, mode = IN},"
            + "#{objeto.longitudPan,            jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idBin,                  jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.rangoInicialTarjeta,    jdbcType = INTEGER, mode = IN},"
            + "#{objeto.rangoFinalTarjeta,      jdbcType = INTEGER, mode = IN},"
            + "#{userAudit,                     jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Logo> mantener(Parametro parametro);

}
