package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.PoliticaSeguridad;

public interface IPoliticaSeguridadMapper extends IMantenibleMapper<PoliticaSeguridad>
{	
	@Select(value = { "{call MANT_POLITICA_SEGURIDAD ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.numeroMaximoIntentos, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.complejidadContrasenia, jdbcType = VARCHAR, mode = IN},"
            + "#{objeto.cantidadDiasParaCaducidadContrasenia, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.longitudMinimaContrasenia, jdbcType = INTEGER, mode = IN},"
            + "#{objeto.autenticacionActiveDirectory, jdbcType = BIT, mode = IN},"
            + "#{userAudit, jdbcType = VARCHAR, mode = IN})}" })
    @Options(statementType = StatementType.CALLABLE)
    public List<PoliticaSeguridad> mantener(Parametro parametro);
}