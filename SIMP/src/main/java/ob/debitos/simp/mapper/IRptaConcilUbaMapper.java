package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.RespuestaConcilUba;
import ob.debitos.simp.model.parametro.Parametro;

public interface IRptaConcilUbaMapper extends IMantenibleMapper<RespuestaConcilUba>
{
    @Select(value = { "{call MANT_RPTA_CONCIL_UBA ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idRespuestaConcilUba, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<RespuestaConcilUba> mantener(Parametro parametro);
}