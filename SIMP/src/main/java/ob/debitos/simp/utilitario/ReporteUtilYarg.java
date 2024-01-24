package ob.debitos.simp.utilitario;

import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.impl.BandBuilder;

public class ReporteUtilYarg
{	
	public static final String PARAM_TEMPLATE = "template";
    public static final String PARAM_NOMBRE_REPORTE = "nombreReporte";
    public static final String PARAM_REPORTE_PARAMETERS = "reporteParametros";
    public static final String PARAM_SHEET_NAMES = "shettNames";
    public static final String PARAM_MAPS = "maps";
    
    private ReporteUtilYarg()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    /**
     * Construye la banda del reporte.
     * <p>
     * Generalmente es utilizado cuando se desea insertar datos en la banda.
     * 
     * @param name
     *            el nombre de la banda del reporte
     * @param queryName
     *            el nombre del query asociado a la banda del reporte
     * @param script
     *            el script de extraccion de datos para la banda, esto depende
     *            del tipo de dato que se desea insertar en la banda
     * @param loaderType
     *            el tipo de dato a insertar en la banda
     * @return la banda del reporte construida
     */
    public static ReportBand buildReportBand(String name, String queryName, String script,
            String loaderType)
    {
        BandBuilder bandBuilder = new BandBuilder();
        return bandBuilder.name(name).query(queryName, script, loaderType).build();
    }

    /**
     * Construye la banda del reporte.
     * <p>
     * Generalmente es utilizado cuando se desea insertar una banda sin datos.
     * 
     * @param name
     *            el nombre de la banda del reporte
     * @return la banda del reporte construida
     */
    public static ReportBand buildReportBand(String name)
    {
        BandBuilder bandBuilder = new BandBuilder();
        return bandBuilder.name(name).build();
    }
}