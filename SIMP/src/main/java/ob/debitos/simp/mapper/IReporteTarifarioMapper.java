package ob.debitos.simp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

import ob.debitos.simp.mapper.base.IReporteMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarifario;
import ob.debitos.simp.model.reporte.ReporteTarifario;

public interface IReporteTarifarioMapper extends IReporteMapper<ReporteTarifario>
{
    @Select("{call REPORT_TARIFARIO ( "
            + "#{verbo, jdbcType = VARCHAR, mode = IN},"
            + "#{codigoInstitucion, jdbcType = NUMERIC, mode = IN},"
            + "#{fechaInicio, jdbcType = DATE, mode = IN},"
            + "#{fechaFin, jdbcType = DATE, mode = IN})}")
    @Options(statementType = StatementType.CALLABLE)
    public List<ReporteTarifario> reporte(CriterioBusquedaTarifario criterio);
}