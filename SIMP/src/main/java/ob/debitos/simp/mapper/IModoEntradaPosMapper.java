package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ModoEntradaPos;
import ob.debitos.simp.model.parametro.Parametro;

public interface IModoEntradaPosMapper extends IMantenibleMapper<ModoEntradaPos>
{
    @Select(value = { "{call MANT_MODO_ENTRADA_POS ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.codigoModoEntradaPOS, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<ModoEntradaPos> mantener(Parametro parametro);
}