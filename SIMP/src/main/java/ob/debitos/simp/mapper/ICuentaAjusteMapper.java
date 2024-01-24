package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaAjuste;
import ob.debitos.simp.model.parametro.Parametro;

/**
 * Realiza las operaciones de mantenimiento de las cuentas de ajuste a través
 * del procedimiento almacenado {@code MANT_CUENTA_AJUSTE}.
 * <p>
 * Esta clase extiende de {@link IMantenibleMapper} para reutilizar el método
 * {@code IMantenibleMapper#mantener(Parametro)}.
 * 
 * @author Carla Ulloa
 */
public interface ICuentaAjusteMapper extends IMantenibleMapper<CuentaAjuste>
{
    @Select(value = { "{call MANT_CUENTA_AJUSTE ( #{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.rolTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.monedaCompensacion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.tipoMovimiento, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.registroContable, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cuentaCargo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cuentaAbono, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoAnalitico, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.nuevoRolTransaccion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.nuevoMonedaCompensacion, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.nuevoTipoMovimiento, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.nuevoRegistroContable, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN} )} " })
    @Options(statementType = StatementType.CALLABLE)
    public List<CuentaAjuste> mantener(Parametro parametro);
}