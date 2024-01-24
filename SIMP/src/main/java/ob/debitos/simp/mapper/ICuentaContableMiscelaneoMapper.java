package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContableMiscelaneo;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICuentaContableMiscelaneoMapper extends IMantenibleMapper<CuentaContableMiscelaneo>
{
	@Select(value = { "{call MANT_CONTAB_MISCELANEOS (" + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoInstitucion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idCliente, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idEmpresa, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoMoneda, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoMembresia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoClaseServicio, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoOrigen, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoClaseTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.codigoTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.idRolTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.cuentaCargo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cuentaAbono, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAnalitico, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cuentaHibrida, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.cuentaCargoH, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cuentaAbonoH, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAnaliticoH, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CuentaContableMiscelaneo> mantener(Parametro parametro);
}