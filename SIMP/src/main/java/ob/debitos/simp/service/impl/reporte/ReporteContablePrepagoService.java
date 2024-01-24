package ob.debitos.simp.service.impl.reporte;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IReporteContablePrepagoMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.proceso.ArchivoContableFondosPrepago;
import ob.debitos.simp.model.proceso.ReporteContableLogCont;
import ob.debitos.simp.service.IReporteContablePrepagoService;
import ob.debitos.simp.utilitario.ReporteYarg;
import ob.debitos.simp.utilitario.Util;

@Service
public class ReporteContablePrepagoService implements IReporteContablePrepagoService
{

    private @Autowired IReporteContablePrepagoMapper reporteContablePrepagoMapper;
    private @Autowired Environment env;
    private @Autowired Logger logger;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer generarArchivoContable(CriterioBusquedaArchivoContablePrepago criterioBusquedaArchivoContablePrepago)
    {
        List<ArchivoContableFondosPrepago> archivoContableFondosPrepago = reporteContablePrepagoMapper.generarArchivoContable(criterioBusquedaArchivoContablePrepago);
        ClassLoader classLoader = getClass().getClassLoader();
        logger.info("{}", archivoContableFondosPrepago);
        logger.info("{}", env.getProperty("rutaResultadoArchivoContable" + criterioBusquedaArchivoContablePrepago.getTipo()));
        String rutaPatron = env.getProperty("rutaResultadoArchivoContable".concat(criterioBusquedaArchivoContablePrepago.getTipo()));
        rutaPatron = rutaPatron.replace("[EEE]", String.format("%03d", criterioBusquedaArchivoContablePrepago.getIdInstitucion()));
        rutaPatron = rutaPatron.replace("[FECHA]", String.format("%1$tY%1$tm%1$td", criterioBusquedaArchivoContablePrepago.getFechaProceso()));

        Date fp = criterioBusquedaArchivoContablePrepago.getFechaProceso();
        String r = Util.formarRutaCarpetaPorFecha(fp);
        rutaPatron = rutaPatron.replace("[RUTA_FECHA]", r);

        File directorioDestino = Paths.get(rutaPatron).toFile().getParentFile();
        if (!directorioDestino.exists())
        {
            directorioDestino.mkdirs();
        }

        File rutaModeloArchivoContable = new File(classLoader.getResource(env.getProperty("rutaModeloArchivoContable" + criterioBusquedaArchivoContablePrepago.getTipo())).getFile());

        File rutaResultadoArchivoContable = new File(rutaPatron);
        try
        {
            ReporteYarg.generarReporteCSV(archivoContableFondosPrepago, rutaModeloArchivoContable.getAbsolutePath(), rutaResultadoArchivoContable.getAbsolutePath(),
                    env.getProperty("areaModeloArchivoContable".concat(criterioBusquedaArchivoContablePrepago.getTipo())));

            Scanner lector = new Scanner(rutaResultadoArchivoContable);
            List<String> lista = new ArrayList<>();

            while (lector.hasNextLine())
            {
                String linea = lector.nextLine();
                String lineaNueva = linea.substring(0, linea.length()) + "*#%";
                lista.add(lineaNueva);
            }
            lector.close();

            PrintWriter escritor = new PrintWriter(rutaResultadoArchivoContable);
            lista.stream().forEach(l -> escritor.println(l));
            escritor.close();

        } catch (IOException e)
        {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Integer generarReporteContable(CriterioBusquedaArchivoContablePrepago criterioBusquedaReporteContablePrepago)
    {

        List<ReporteContableLogCont> reporteContableFondosPrepago = reporteContablePrepagoMapper.generarReporteContable(criterioBusquedaReporteContablePrepago);

        String rutaPatron = env.getProperty("rutaResultadoReporteContable".concat(criterioBusquedaReporteContablePrepago.getTipo()));
        rutaPatron = rutaPatron.replace("[FECHA]", String.format("%1$tY%1$tm%1$td", criterioBusquedaReporteContablePrepago.getFechaProceso()));

        ClassLoader classLoader = getClass().getClassLoader();

        File rutaModeloReporteContable = new File(classLoader.getResource(env.getProperty("rutaModeloReporteContable" + criterioBusquedaReporteContablePrepago.getTipo())).getFile());

        File rutaResultadoReporteContable = new File(rutaPatron);

        try
        {
            ReporteYarg.generarReporteXLSX(reporteContableFondosPrepago, rutaModeloReporteContable.getAbsolutePath(), rutaResultadoReporteContable.getAbsolutePath(),
                    env.getProperty("areaModeloReporteContable".concat(criterioBusquedaReporteContablePrepago.getTipo())));
        } catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<ReporteContableLogCont> buscarReporteContable(CriterioBusquedaArchivoContablePrepago criterioBusquedaReporteContablePrepago)
    {
        return reporteContablePrepagoMapper.generarReporteContable(criterioBusquedaReporteContablePrepago);
    }

}
