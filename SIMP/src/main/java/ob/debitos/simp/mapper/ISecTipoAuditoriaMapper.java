package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.SecTipoAuditoria;

public interface ISecTipoAuditoriaMapper extends IMantenibleMapper<SecTipoAuditoria>
{
    @Select(value = { "{call MANT_TIPO_AUDITORIA ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idTipoAuditoria, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<SecTipoAuditoria> mantener(Parametro parametro);
}