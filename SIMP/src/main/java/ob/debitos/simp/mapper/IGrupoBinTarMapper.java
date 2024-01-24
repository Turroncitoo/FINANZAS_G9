package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.tarifario.GrupoBinTar;

public interface IGrupoBinTarMapper extends IMantenibleMapper<GrupoBinTar>
{
    @Select("{call MANT_TAR_GRUPO_BIN ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.grupoBin, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.Bin, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<GrupoBinTar> mantener(Parametro parametro);
}
