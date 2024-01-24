package ob.debitos.simp.service.impl.proceso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IArchivoContableMapper;
import ob.debitos.simp.model.proceso.ArchivoContable;
import ob.debitos.simp.service.IArchivoContableService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.utilitario.Constantes;
import ob.debitos.simp.utilitario.StringsUtils;

@Service
@PropertySource("classpath:contable.properties")
public class ArchivoContableService implements IArchivoContableService
{
    private @Autowired IArchivoContableMapper archivoContableMapper;
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired Environment env;

    @Override
    public void generarArchivoContable(Date fechaProceso)
    {
        String fechaProcesoCadena = String.format(Constantes.STR_FMT_FECHA_YYYYMMDD, fechaProceso);
        String nombreArchivo = env.getProperty(Constantes.PROP_NOMBRE_ARCHIVO_CONTABLE);
        nombreArchivo = nombreArchivo.replace(Constantes.PMG_PATRON_FECHA, fechaProcesoCadena);

        String repositorioPatron = parametroGeneralService.buscarRepositorioArchivoContable();
        String rutaCarpetaFecha = StringsUtils.concatenarCadena(StringsUtils.formarRutaCarpetaPorFecha(fechaProceso), File.separator);
        repositorioPatron = repositorioPatron.replace(Constantes.PMG_PATRON_RUTA_FECHA, rutaCarpetaFecha);

        /*
         * Las siguientes líneas se comentan por facilidad de lectura boolean t
         * = true; File directorioDestino =
         * Paths.get(repositorioPatron).toFile(); if
         * (!directorioDestino.exists()) { t = directorioDestino.mkdirs(); }
         */

        File rutaResultadoArchivoContable = new File(repositorioPatron);
        List<ArchivoContable> listaArchivoContable = archivoContableMapper.buscarResumenTransacciones(fechaProceso);

        SimpleDateFormat d = new SimpleDateFormat(Constantes.DATE_FORMAT_YYYYMMDD);
        DecimalFormat df = StringsUtils.obtenerFormateadorDecimal(Constantes.NUM_FMT_DOS_CIFRAS_DECIMAL_DF, Constantes.PUNTO_DECIMAL);

        try
        {
            PrintWriter escritor = new PrintWriter(rutaResultadoArchivoContable);
            escritor.println(StringsUtils.concatenarCadena(Constantes.CARACTER_LINEA_INICIO_ARCHIVO, fechaProcesoCadena, StringsUtils.padLeftZero(listaArchivoContable.size(), 8)));
            for (ArchivoContable i : listaArchivoContable)
            {
                String f01 = d.format(i.getFechaProceso());
                String f02 = d.format(i.getFechaAfectacion());
                String f03 = StringsUtils.padLeft(i.getMoneda().toString(), 3);
                String f04 = StringsUtils.padLeft(i.getCodigoMembresia().toString(), 1);
                String f05 = StringsUtils.padLeft(i.getClaseServicio(), 3);
                String f06 = StringsUtils.padLeft(i.getOrigen().toString(), 2);
                String f07 = StringsUtils.padLeft(i.getClaseTransaccasion().toString(), 1);
                String f08 = StringsUtils.padLeft(i.getCodigoTransaccion().toString(), 2);
                String f09 = StringsUtils.padLeft(i.getTipoTransaccion(), 1);
                String f10 = StringsUtils.padLeft(i.getTipoComision(), 1);
                String f11 = StringsUtils.padLeft(i.getCuentaCargo(), 20);
                String f12 = StringsUtils.padLeft(i.getCuentaAbono(), 20);
                String f13 = StringsUtils.padLeft(i.getCodigoAnalitico() != null ? i.getCodigoAnalitico() : "", 10);
                String f14 = StringsUtils.padLeft(df.format(i.getMontoContable()), 15);

                escritor.println(StringsUtils.concatenarCadena(f01, f02, f03, f04, f05, f06, f07, f08, f09, f10, f11, f12, f13, f14));
            }
            escritor.println(Constantes.CARACTER_LINEA_FINAL_ARCHIVO + StringsUtils.padLeftZero(listaArchivoContable.size(), 8));
            escritor.close();

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}