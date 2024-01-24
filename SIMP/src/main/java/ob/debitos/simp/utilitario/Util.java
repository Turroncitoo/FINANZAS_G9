package ob.debitos.simp.utilitario;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;

public class Util
{

    private static @Autowired Logger logger;

    public static void msjError(String string)
    {
        logger.info("{}", string);
    }

    public static String formatearNumero(String numFmtDosCifras, Number i)
    {
        return String.format(numFmtDosCifras, i);
    }

    public static String formarRutaCarpetaPorFecha(Date fechaProceso)
    {
        LocalDate fechaProcesoConvertida = aLocalDate(fechaProceso);
        return StringsUtils.concatenar(fechaProcesoConvertida.getYear(), File.separator, formatearNumero(Constantes.NUM_FMT_DOS_CIFRAS, fechaProcesoConvertida.getMonthValue()), File.separator,
                formatearNumero(Constantes.NUM_FMT_DOS_CIFRAS, fechaProcesoConvertida.getDayOfMonth()));
    }

    public static LocalDate aLocalDate(Date fecha)
    {
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    public static String evaluarResultado(boolean evaluacion)
    {
        return evaluacion ? Constantes.EXITO : Constantes.FALLO;
    }

    public static boolean verificarHeaderTrailerValido(List<LogControlProgramaDetalle> logControlProgramaDetalles)
    {
        int pos = 0;
        boolean headerTrailerValido = true;
        while (headerTrailerValido && pos < logControlProgramaDetalles.size())
        {
            LogControlProgramaDetalle logControlProgramaDetalle = logControlProgramaDetalles.get(pos);
            headerTrailerValido = logControlProgramaDetalle.getEstado() == 1;
            pos++;
        }
        return headerTrailerValido;
    }
}
