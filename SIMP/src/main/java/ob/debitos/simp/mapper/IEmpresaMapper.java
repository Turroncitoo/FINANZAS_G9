package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Empresa;
import ob.debitos.simp.model.parametro.Parametro;

public interface IEmpresaMapper extends IMantenibleMapper<Empresa>
{
    @Select(value = { "{call MANT_EMPRESAS ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEmpresa, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.ruc, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.direccion,jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<Empresa> mantener(Parametro parametro);
}