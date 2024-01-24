package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.CategoriaRecurso;

public interface ICategoriaRecursoMapper extends IMantenibleMapper<CategoriaRecurso>
{
    @Select(value = { "{call MANT_CATEGORIA_RECURSO ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCategoria, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.categoria, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.accionCategoria, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CategoriaRecurso> mantener(Parametro parametro);
    
    public List<CategoriaRecurso> buscarTodosCategoriaRecurso();
}