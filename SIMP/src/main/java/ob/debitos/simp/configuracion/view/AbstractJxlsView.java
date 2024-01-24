package ob.debitos.simp.configuracion.view;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.AbstractView;

import net.sf.jxls.transformer.XLSTransformer;
import ob.debitos.simp.utilitario.ReporteUtilYarg;
import ob.debitos.simp.utilitario.StringsUtils;

public abstract class AbstractJxlsView extends AbstractView
{
    private static final String NOMBRE_REPORTE = "Reporte";
    private final static String RUTA_REPORTE = "xlsx/";
    private final static String EXTENSION = ".xlsx";

    public AbstractJxlsView()
    {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        model.putIfAbsent(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, NOMBRE_REPORTE);
        buildExcelDocument(model, null, request, response);
        String templateExcel = (String) model.get(ReporteUtilYarg.PARAM_TEMPLATE);
        Map<String, Object> reporteParametros = (Map<String, Object>) model
                .get(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(
                StringsUtils.concatenarCadena(RUTA_REPORTE, templateExcel, EXTENSION));
        XLSTransformer xLSTransformer = new XLSTransformer();
        Workbook workbook = xLSTransformer.transformXLS(stream, reporteParametros);
        String nombreReporte = (String) model.get(ReporteUtilYarg.PARAM_NOMBRE_REPORTE);
        response.setHeader("Content-Disposition",
                StringsUtils.concatenarCadena("attachment;filename=", nombreReporte, ".xlsx"));
        response.setContentType(getContentType());
        renderReport(workbook, response);
    }

    protected abstract void buildExcelDocument(Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception;

    protected void renderReport(Workbook workbook, HttpServletResponse response) throws IOException
    {
        workbook.write(response.getOutputStream());
    }

    @Override
    protected boolean generatesDownloadContent()
    {
        return true;
    }
}