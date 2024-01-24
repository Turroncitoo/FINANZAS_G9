package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuditoria;
//github.com/hanzllcc/SIMP_DEB.git
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.Auditoria;

public interface IAuditoriaMapper extends IMantenibleMapper<Auditoria>
{
    @Select(value = { "{call MANT_AUDITORIA ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idRecurso, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idAccion, jdbcType = CHAR, mode = IN},"
            + "#{objeto.direccionIp, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.exito, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.comentario, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.logError, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nombreUsuario, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.fecha, jdbcType = DATE, mode = IN},"
            + "#{objeto.hora, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Auditoria> mantener(Parametro parametro);

    public List<Auditoria> buscarPistasAuditoriaPorCriterio(CriterioBusquedaAuditoria criterioBusqueda);
}