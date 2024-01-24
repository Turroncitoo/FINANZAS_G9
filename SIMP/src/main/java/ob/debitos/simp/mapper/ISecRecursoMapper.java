package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.SecRecurso;

public interface ISecRecursoMapper extends IMantenibleMapper<SecRecurso>
{
    @Select(value = { "{call MANT_RECURSO ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idRecurso, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCategoria, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcionCategoria, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.accionRecurso, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<SecRecurso> mantener(Parametro parametro);

    @Select("{call OBTENER_RECURSOS_PERMITIDOS_POR_ID_USUARIO ("
            + "#{idUsuario, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<SecRecurso> obtenerRecursosPermitidosPorIdUsuario(String idUsuario);
}