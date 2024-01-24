package ob.debitos.simp.configuracion.view;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.ModelAndView;

import net.sf.jxls.transformer.XLSTransformer;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.MimeTypeUtil;
import ob.debitos.simp.utilitario.ReporteUtilYarg;
import ob.debitos.simp.utilitario.StringsUtils;

/**
 * La clase {@code JxlsMultipleView} tiene como finalidad proveer una vista
 * aceptable por <b>Spring MVC</b> para la generación reportes de tipo XLSX que
 * contengan múltiples HOJAS o SHEET.
 * <p>
 * Esta clase extiende de {@link AbstractJxlsView} para poder utilizar la
 * librería <b>JXLS</b>
 * <p>
 * <b>NOTA: </b>Para poder acceder a esta vista es necesario que el método
 * retorne un {@link ModelAndView} estableciendo la vista
 * <b>jxlsMultipleView</b> en su constructor.
 * 
 * @author Carla Ulloa
 */
public class JxlsMultipleView extends AbstractJxlsView
{
    private static final String NOMBRE_REPORTE = "Reporte";

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook,
            HttpServletRequest request, HttpServletResponse response)
    {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) throws Exception
    {
        model.putIfAbsent(ReporteUtilYarg.PARAM_NOMBRE_REPORTE, NOMBRE_REPORTE);
        String templateExcel = (String) model.get(ReporteUtilYarg.PARAM_TEMPLATE);
        List<Map<String, Object>> maps = (ArrayList<Map<String, Object>>) model
                .get(ReporteUtilYarg.PARAM_REPORTE_PARAMETERS);
        List<String> sheetNames = (ArrayList<String>) model.get(ReporteUtilYarg.PARAM_SHEET_NAMES);
        InputStream stream = getClass().getClassLoader().getResourceAsStream(
                StringsUtils.concatenarCadena(ConstantesGenerales.RUTA_REPORTE_XLSX, templateExcel,
                        MimeTypeUtil.EXTENSION_XLSX));
        XLSTransformer xLSTransformer = new XLSTransformer();
        Workbook workbook = xLSTransformer.transformMultipleSheetsList(stream, maps, sheetNames,
                "map", new HashMap<>(), 0);
        String nombreReporte = (String) model.get(ReporteUtilYarg.PARAM_NOMBRE_REPORTE);        
        response.setHeader("Content-Disposition", StringsUtils.concatenarCadena(
                "attachment;filename=", nombreReporte, MimeTypeUtil.EXTENSION_XLSX));
        response.setContentType(getContentType());
        renderReport(workbook, response);
    }
}