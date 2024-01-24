package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoTransaccionBevertec;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoTxnBevertecMapper extends IMantenibleMapper<CodigoTransaccionBevertec>
{
    @Select(value = {"{call MANT_CODIGO_TXN_BEVERTEC ( " 
                    + "#{verbo, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.codigoCanalEmisor, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.tipoTransaccion, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.codTransaccion, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
                    + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoTransaccionBevertec> mantener(Parametro parametro);
}