package ob.debitos.simp.configuracion.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

public class JxlsView extends AbstractJxlsView
{
    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        // String templateExcel = (String)
        // model.get(ReporteUtilYarg.PARAM_TEMPLATE);
        // InputStream stream = getClass().getClassLoader().getResourceAsStream(
        // StringsUtils.concatenarCadena(RUTA_REPORTE, templateExcel,
        // EXTENSION));
        // XLSTransformer xLSTransformer = new XLSTransformer();
        // workbook = xLSTransformer.transformXLS(stream, model);
    }
}