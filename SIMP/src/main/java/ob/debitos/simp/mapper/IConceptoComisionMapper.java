package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ConceptoComision;
import ob.debitos.simp.model.parametro.Parametro;

public interface IConceptoComisionMapper extends IMantenibleMapper<ConceptoComision>
{
    @Select(value = { "{call MANT_CONCEPTO_COMISION (#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.idConceptoComision, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.abreviatura, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.rolEmisor, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.rolReceptor, jdbcType = NUMERIC, mode = IN},"
            + "#{objeto.activado, jdbcType = NUMERIC, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ConceptoComision> mantener(Parametro parametro);
}