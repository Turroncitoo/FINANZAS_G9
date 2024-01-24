package reporte;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

public class ReporteYargTest
{

    @Test
    public void test() throws IOException
    {
        ReportBuilder reportBuilder = new ReportBuilder();
        ReportTemplateBuilder template = new ReportTemplateBuilder()
                .documentPath("./src/main/resources/xlsx/staff.xlsx").documentName("staff2.xlsx")
                .outputType(ReportOutputType.csv).readFileFromPath();
        reportBuilder.template(template.build());
        
        BandBuilder header = new BandBuilder();
        ReportBand reportHeader = header.name("Encabezado").build();
        BandBuilder total = new BandBuilder();
        ReportBand reportTotal = total.name("Total").build();
        BandBuilder grafico = new BandBuilder();
        ReportBand reportGrafico = grafico.name("Grafico").build();
        BandBuilder band = new BandBuilder();
        ReportBand staff = band
                .name("Staff")
                .query("Staff", "parameter=param1 $", "json")
                .build();

        reportBuilder.band(reportHeader).band(staff).band(reportTotal).band(reportGrafico);

        Report report = reportBuilder.build();
        Reporting reporting = new Reporting();
        reporting.setFormatterFactory(new DefaultFormatterFactory());
        reporting.setLoaderFactory(
                new DefaultLoaderFactory().setJsonDataLoader(new JsonDataLoader()));
        ReportOutputDocument reportOutputDocument = reporting.runReport(
                new RunParams(report).param("param1", obtenerJSONDePersona()),
                new FileOutputStream("./src/main/resources/xlsx/reporte.csv"));
        System.out.println(reportOutputDocument);
    }

    public String obtenerJSONDePersona() throws JsonProcessingException
    {
        Persona p = new Persona();
        p.setNombre("HANZ");
        p.setApellidos("LLANTO");
        p.setMonto(-3.56);
        Persona p2 = new Persona();
        p2.setNombre("ANDRES");
        p2.setApellidos("LOPEZ");
        p2.setMonto(45.66);
        List<Persona> personas = new ArrayList<>();
        personas.add(p);
        personas.add(p2);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(personas);
    }

}
