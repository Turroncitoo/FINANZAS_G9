package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.Perfil;

public interface IPerfilMapper extends IMantenibleMapper<Perfil>
{
    @Select(value = { "{call MANT_PERFIL ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idPerfil, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.visualizaPAN, jdbcType = BOOLEAN, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Perfil> mantener(Parametro parametro);

    public List<Perfil> buscarRecursosPorIdPerfil(String idPerfil);
}