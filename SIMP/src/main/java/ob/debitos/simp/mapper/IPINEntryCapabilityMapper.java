package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.PINEntryCapability;
import ob.debitos.simp.model.parametro.Parametro;

/**
 * Realiza las operaciones de mantenimiento de capacidad de ingreso de las transacciones   
 * de tipo PIN a través del procedimiento almacenado {@code MANT_PIN_ENTRY_CAPABILITY}.
 * <p>
 * Esta clase extiende de {@link IMantenibleMapper} para reutilizar el método
 * {@code IMantenibleMapper#mantener(Parametro)}.
 * 
 * @author Fernando Fonseca
 */
public interface IPINEntryCapabilityMapper extends IMantenibleMapper<PINEntryCapability> {
    @Select(value = { "{call MANT_PIN_ENTRY_CAPABILITY ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<PINEntryCapability> mantener(Parametro parametro);
}
