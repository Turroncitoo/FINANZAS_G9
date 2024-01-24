package ob.debitos.simp.utilitario;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class StringsUtils
{

    public static String obtenerCadenaDespuesDePunto(String cadenaConPunto)
    {
        return cadenaConPunto.substring(cadenaConPunto.lastIndexOf('.') + 1);
    }

    public static String concatenarCadena(Object... objetos)
    {
        StringBuilder sb = new StringBuilder();
        for (Object objeto : objetos)
        {
            sb.append(objeto.toString());
        }
        return sb.toString();
    }

    public static String[] obtenerSubCadenas(String cadena, String separador)
    {
        return cadena.split(separador, 2);
    }

    public static List<String> obtenerTokens(String cadena, String separador)
    {
        String cadenaSinEspacios = cadena.replaceAll(Regex.ESPACIOS_EN_BLANCO, "");
        return Collections.list(new StringTokenizer(cadenaSinEspacios, separador)).stream().map(token -> (String) token).collect(Collectors.toList());
    }

    public static String obtenerCadenaDespuesDe(String cadena, String separador)
    {
        if (cadena != null && cadena.length() > 0)
        {
            return cadena.substring(cadena.lastIndexOf(separador) + 1);
        }
        return cadena;
    }

    public static String obtenerCadenaAntesDe(String cadena, String separador)
    {
        if (cadena != null && cadena.length() > 0)
        {
            return cadena.substring(0, cadena.lastIndexOf(separador));
        }
        return cadena;
    }

    public static String removerUltimoCaracter(String cadena)
    {
        if (cadena != null && cadena.length() > 0)
        {
            cadena = cadena.substring(0, cadena.length() - 1);
        }
        return cadena;
    }

    public static String concatenar(Object... a)
    {
        StringBuilder result = new StringBuilder();
        for (Object el : a)
        {
            result.append(el);
        }
        return result.toString();
    }

    public static String padRight(String s, int n)
    {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n)
    {
        return String.format("%1$" + n + "s", s);
    }

    public static String padLeftZeroDouble(double num, int decimals, int n)
    {
        double round = Math.round(num * Math.pow(10, decimals)) / Math.pow(10, decimals);
        return String.format("%0" + n + "d", Integer.parseInt(String.format("%." + decimals + "f", round).replace(".", "").replace(",", "")));
    }

    public static String formarRutaCarpetaPorFecha(Date fechaProceso)
    {
        LocalDate fechaProcesoConvertida = DatesUtils.aLocalDate(fechaProceso);

        return StringsUtils.concatenarCadena(fechaProcesoConvertida.getYear(), File.separator, formatearNumero(Constantes.NUM_FMT_DOS_CIFRAS, fechaProcesoConvertida.getMonthValue()), File.separator,
                formatearNumero(Constantes.NUM_FMT_DOS_CIFRAS, fechaProcesoConvertida.getDayOfMonth()));
    }

    public static String formatearNumero(String fmt, Number i)
    {
        return String.format(fmt, i);
    }

    public static String padLeftZero(int s, int n)
    {
        return String.format("%0" + n + "d", s);
    }

    public static DecimalFormat obtenerFormateadorDecimal(String fmt, String delimitador)
    {
        DecimalFormat df = new DecimalFormat("#####0.00");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
        return df;
    }

    /**
     * Reemplaza un patron en la cadena por algún valor.
     *
     * @param cadena      la cadena con patrones
     * @param patronValor arreglo con los patrones y valores a reemplazar
     * @return la cadena resultante del proceso de reemplazo
     */
    public static String reemplazarCadena(String cadena, Object... patronValor)
    {
        Validate.isTrue(patronValor.length % 2 == 0, ConstantesExcepciones.REEMPLAZO_PATRON_VALOR_IMPAR, cadena);
        for (int i = 0; i < patronValor.length; i += 2)
        {
            cadena = cadena.replace(String.valueOf(patronValor[i]), String.valueOf(patronValor[i + 1]));
        }
        return cadena;
    }

    /**
     * Enmascara una cadena reemplazando los caracteres por astericos (*).
     *
     * @param cadena   la cadena a enmascarar
     * @param startlen posicion inicial a enmarcarar
     * @param endlen   posición final a enmascarar
     * @return la cadena sin el último caracter
     */
    public static String maskCCNumber(String cadena, int startlen, int endlen)
    {
        if (cadena == null)
        {
            return "";
        }
        cadena = cadena.trim();
        int total = cadena.length();
        int masklen = total - (startlen + endlen);
        StringBuffer maskedbuf = new StringBuffer(cadena.substring(0, startlen));
        for (int i = 0; i < masklen; i++)
        {
            maskedbuf.append('*');
        }
        maskedbuf.append(cadena.substring(startlen + masklen, total));
        return maskedbuf.toString();
    }

    public static String validarString(String value)
    {
        return value != null ? value : "";
    }

    public static String validarInteger(Integer value)
    {
        return value != null ? String.valueOf(value) : " ";
    }

    public static String validarDouble(Double value)
    {
        return value != null ? String.valueOf(value) : " ";
    }

    public static String validarDate(Date value)
    {
        return value != null ? String.valueOf(value) : " ";
    }

    public static String concatenarCodigoDescripcion(String codigo, String descripcion)
    {
        return StringUtils.join(validarString(codigo), " - ", validarString(descripcion));
    }

    public static List<String> agregarComillaSimpleLista(List<String> lista)
    {
        List<String> result = new ArrayList<String>();

        lista.forEach(l -> result.add(concatenarCadena("'", l, "'")));

        return result;
    }

}
