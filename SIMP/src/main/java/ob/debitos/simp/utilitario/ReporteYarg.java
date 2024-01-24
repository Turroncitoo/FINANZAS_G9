package ob.debitos.simp.utilitario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import com.haulmont.yarg.formatters.factory.DefaultFormatterFactory;
import com.haulmont.yarg.loaders.factory.DefaultLoaderFactory;
import com.haulmont.yarg.loaders.impl.JsonDataLoader;
import com.haulmont.yarg.reporting.ReportOutputDocument;
import com.haulmont.yarg.reporting.Reporting;
import com.haulmont.yarg.reporting.RunParams;
import com.haulmont.yarg.structure.Report;
import com.haulmont.yarg.structure.ReportBand;
import com.haulmont.yarg.structure.ReportOutputType;
import com.haulmont.yarg.structure.impl.BandBuilder;
import com.haulmont.yarg.structure.impl.ReportBuilder;
import com.haulmont.yarg.structure.impl.ReportTemplateBuilder;

public class ReporteYarg {

	public static void generarReporteCSV(List<?> listaElementos, String rutaModelo, String ruta, String area) throws IOException {
		ReportBuilder reportBuilder = new ReportBuilder();
		ReportTemplateBuilder template = new ReportTemplateBuilder()
				.documentPath(rutaModelo.toString()).documentName(rutaModelo)
				.outputType(ReportOutputType.csv).readFileFromPath();
		reportBuilder.template(template.build());

		BandBuilder band = new BandBuilder();
		ReportBand staff = band.name(area).query(area, "parameter=param1 $", "json").build();
		
		BandBuilder header = new BandBuilder();
        ReportBand reportHeader = header.name("Encabezado").build();
        
        reportBuilder.band(reportHeader).band(staff);      
        Report report = reportBuilder.build();
		Reporting reporting = new Reporting();
		reporting.setFormatterFactory(new DefaultFormatterFactory());
		reporting.setLoaderFactory(new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));

		reporting.runReport(
				new RunParams(report).param("param1", JsonUtil.convertirAJSON(listaElementos)),
				new FileOutputStream(ruta.toString()));
		
	}
	
	public static void generarReporteXLSX(List<?> listaElementos, String rutaModelo, String ruta, String area) throws IOException {
		ReportBuilder reportBuilder = new ReportBuilder();

		ReportTemplateBuilder template = new ReportTemplateBuilder()
				.documentPath(rutaModelo.toString()).documentName(rutaModelo)
				.outputType(ReportOutputType.xlsx).readFileFromPath();
		reportBuilder.template(template.build());

		BandBuilder pre_header = new BandBuilder();
        ReportBand reportPreHeader = pre_header.name("SuperEncabezado").build();
		BandBuilder header = new BandBuilder();
        ReportBand reportHeader = header.name("Encabezado").build();
        BandBuilder total = new BandBuilder();
        ReportBand reportTotal = total.name("Total").build();
		BandBuilder band = new BandBuilder();
		ReportBand staff = band.name(area).query(area, "parameter=param1 $", "json").build();

		reportBuilder.band(reportPreHeader).band(reportHeader).band(staff).band(reportTotal);

		Report report = reportBuilder.build();
		Reporting reporting = new Reporting();
		reporting.setFormatterFactory(new DefaultFormatterFactory());
		reporting.setLoaderFactory(new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));

		ReportOutputDocument reportOutputDocument = reporting.runReport(
				new RunParams(report).param("param1", JsonUtil.convertirAJSON(listaElementos)),
				new FileOutputStream(ruta.toString()));

	}

}
