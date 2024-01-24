package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoRespuestaVisa;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoRptaVisaMapper extends IMantenibleMapper<CodigoRespuestaVisa>
{
    @Select(value = { "{call MANT_CODIGO_RPTA_VISA ( " 
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoRespuestaVisa, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.atribuible, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoRespuestaVisa> mantener(Parametro parametro);
}