package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContable;
import ob.debitos.simp.model.parametro.Parametro;

/**
 * Realiza las operaciones de mantenimiento de las escenarios contables
 * a través del procedimiento almacenado {@code MANT_CUENTA_CONTABLE}.
 * <p>
 * Esta clase extiende de {@link IMantenibleMapper} para reutilizar el método
 * {@code IMantenibleMapper#mantener(Parametro)}.
 *
 * @author Hanz Llanto
 */
public interface ICuentaContableMapper  extends IMantenibleMapper<CuentaContable>
{
	@Select(value = { "{call MANT_CUENTA_CONTABLE ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idCuenta, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.numeroCuentaContable, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoMoneda, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.cuentaATM, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.cuentaBase, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.cuentaAjuste, jdbcType = INTEGER, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN}, "
            + "#{objeto.tipoCuenta, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idGenerado, jdbcType = INTEGER, mode = OUT} )}" })
    @Options(statementType = StatementType.CALLABLE)
	List<CuentaContable> mantener(Parametro parametro);
}