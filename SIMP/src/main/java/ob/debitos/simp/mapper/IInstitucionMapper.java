package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.parametro.Parametro;

public interface IInstitucionMapper extends IMantenibleMapper<Institucion>
{
    @Select("{call MANT_INSTITUCIONES (#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cuentaCompensacion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.bancoAdministrador, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.operadorInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcionCorta, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.institucionEmpresa, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoVisanet, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<Institucion> mantener(Parametro parametro);
}