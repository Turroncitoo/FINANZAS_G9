package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CodigoProcesoBevertec;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICodigoProcBevertecMapper extends IMantenibleMapper<CodigoProcesoBevertec>
{
    @Select(value = {
            "{call MANT_CODIGO_PROC_BEVERTEC ("
                    + "#{verbo, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.codigoCanalEmisor, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.tipoTransaccion, jdbcType = VARCHAR, mode = IN},"
                    + "#{objeto.descripcion, jdbcType = VARCHAR, mode = IN},"
                    + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<CodigoProcesoBevertec> mantener(Parametro parametro);
}