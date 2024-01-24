package ob.debitos.simp.configuracion.view;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.impl.ReportBuilder;

import ob.debitos.simp.utilitario.ReporteUtilYarg;
import ob.debitos.simp.utilitario.StringsUtils;

public abstract class AbstractYargXlsxView extends AbstractView
{
    private static final String NOMBRE_REPORTE = "Reporte";
    
    public AbstractYargXlsxView()
    {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        model.putIfAbsent(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, NOMBRE_REPORTE);
        Map<String, Object> parametros = new HashMap<String, Object>();
        ReportBuilder reportBuilder = createReportBuilder();
        buildExcelDocument(model, reportBuilder, parametros, request, response);
        Report report = reportBuilder.build();
        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        reporting.setLoaderFactory(
                new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));
        RunParams runParams = new RunParams(report).params(parametros);
        String nombreReporte = (String) model.get(ReporteUtilYarg.PARAM_NOMBRE_REPORTE);
        response.setHeader("Content-Disposition",
                StringsUtils.concatenarCadena("attachment;filename=", nombreReporte, ".xlsx"));
        response.setContentType(getContentType());
        renderReport(reporting, runParams, response);
    }

    protected abstract void buildExcelDocument(Map<String, Object> model,
            ReportBuilder reportBuilder, Map<String, Object> parametros, HttpServletRequest request,
            HttpServletResponse response) throws Exception;

    protected ReportBuilder createReportBuilder()
    {
        return new ReportBuilder();
    }

    protected void renderReport(Reporting reporting, RunParams runParams,
            HttpServletResponse response) throws IOException
    {
        reporting.runReport(runParams, response.getOutputStream());
    }

    @Override
    protected boolean generatesDownloadContent()
    {
        return true;
    }
}