package ob.debitos.simp.utilitario;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.time.format.DateTimeFormatter;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import org.apache.commons.lang3.StringUtils;

public class DatesUtils
{
    public static final String FORMATO_YYYYMMDD = "yyyy-MM-dd";
    public static final String FORMATO_YYYYMMDD_SIN_GUION = "yyyyMMdd";
    public static final String FORMATO_HHMMSS = "HH:mm:ss";
    public static final String PATTERN_YYYYMMDD_HHMMSS = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String PATTERN_MMdd = "MMdd";
    public static final String FORMATO_DD_MM_YYYY = "dd/MM/yyyy";
    
    private DatesUtils()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }

    public static Date obtenerFechaDeMilisegundos(long tiempoMilisegundos)
    {
        DateTime fecha = new DateTime(tiempoMilisegundos);
        return fecha.toDate();
    }

    public static String obtenerFechaEnFormato(Date date, String formato)
    {
        DateTime fecha = new DateTime(date);
        return DateTimeFormat.forPattern(formato).print(fecha);
    }

    public static String obtenerFechaDeMilisegundosEnFormato(long tiempoMilisegundos,
            String formato)
    {
        DateTime fecha = new DateTime(tiempoMilisegundos);
        return fecha.toString(formato);
    }

    public static LocalTime obtenerLocalTimeHHMMSS(String cadena)
    {
        return LocalTime.of(
                IntegerUtil.aEntero(cadena.substring(0, 2)),
                IntegerUtil.aEntero(cadena.substring(2, 4)),
                IntegerUtil.aEntero(cadena.substring(4, 6)));
        
    }

    public static LocalDate obtenerLocalDateYYYYMMDD(String cadena)
    {
        return LocalDate.of(
                IntegerUtil.aEntero(cadena.substring(0, 4)),
                IntegerUtil.aEntero(cadena.substring(4, 6)),
                IntegerUtil.aEntero(cadena.substring(6, 8)));
    }
    
    
    public static String obtenerDuracionDesdeMilisegundos(long tiempoMilisegundos)
    {
        Period periodo = new Duration(tiempoMilisegundos).toPeriod();
        PeriodFormatter hm = new PeriodFormatterBuilder().printZeroAlways().minimumPrintedDigits(2)
                .appendHours().appendSeparator(":").appendMinutes().appendSeparator(":")
                .appendSeconds().toFormatter();
        return hm.print(periodo);
    }

    public static LocalDate aLocalDate(Date fecha)
    {
        LocalDate localDate = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }
    
    public static boolean esSabado(Date fecha)
    {
        LocalDate fechaLocal = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return fechaLocal.getDayOfWeek() == DayOfWeek.SATURDAY;
    }
    
    public static Date convertDateStringToDate(String sDate){
    	org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMATO_YYYYMMDD);
    	DateTime dt = formatter.parseDateTime(sDate);
    	return dt.toDate();
    }
    
    public static Integer obtenerMesActual() {
        Calendar fecha = Calendar.getInstance();
        int mes = fecha.get(Calendar.MONTH) + 1;
        return mes;
    }

    public static Integer obtenerAnioActual() {
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        return anio;
    }
    
    public static Date obtenerPrimeraFechaDiaDelMes(int mes, int anio) {
        String sDate = anio + "-" + addZeros(mes) + "-" + "01";
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMATO_YYYYMMDD);
        DateTime dt = formatter.parseDateTime(sDate);
        return dt.toDate();
    }
    
    public static String addZeros(int num) {
        String str = "";
        if (num < 10) {
            str = '0' + String.valueOf(num);
        }else{
            str = String.valueOf(num);
        }
        return str;
    }
    
    public static Date obtenerFechaActualSistema() {
        return new Date();
    }
    
    public static Date obtenerUltimaFechaUltimoDiaDelMes(int mes, int anio) {
        String sDate = anio + "-" + addZeros(mes) + "-" + DatesUtils.obtenerUltimoDiaDelMes(mes, anio);
        org.joda.time.format.DateTimeFormatter formatter = DateTimeFormat.forPattern(FORMATO_YYYYMMDD);
        DateTime dt = formatter.parseDateTime(sDate);
        return dt.toDate();
    }
    
    public static Integer obtenerMesAnteriorFromMesAnio(int mes, int anio) {
        Integer mesAnterior = 0;
        if(mes == 1){
            mesAnterior = 12;
        }else{
            mesAnterior = mes - 1;
        }
        return mesAnterior;
    }
    
    public static Integer obtenerAnioAnteriorFromMesAnio(int mes, int anio) {
        Integer anioAnterior = 0;
        if(mes == 1){
            anioAnterior = anio - 1;
        }else{
            anioAnterior = anio;
        }
        return anioAnterior;
    }
    
    public static Integer obtenerUltimoDiaDelMes(int mes, int anio) {
        Calendar calendario = Calendar.getInstance();
        calendario.set(anio, mes - 1, 1);
        int ultimoDiaMes = calendario.getActualMaximum(Calendar.DAY_OF_MONTH);
        return ultimoDiaMes;
    }
    
    /**
     * Obtiene el tiempo entre fechas en seg
     * @param inicio
     * 			fecha inicio
     * @param fin
     * 			fecha fin
     * @return tiempo entre fechas  			
     */
    public static String tiempoEntre(LocalDateTime inicio, LocalDateTime fin) {
		java.time.Duration dur = java.time.Duration.between(inicio, fin);
		long millis = dur.toMillis();
		return String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
				TimeUnit.MILLISECONDS.toMinutes(millis)
						- TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
				TimeUnit.MILLISECONDS.toSeconds(millis)
						- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	}

    public static java.sql.Date convertUtilToSql(java.util.Date uDate) {
        return uDate == null ? null : new java.sql.Date(uDate.getTime());
    }
    
    public static String getLocalDateTimeToString(String format){
        try {
            LocalDateTime date = LocalDateTime.now ();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(format);
            return date.format(formatDate);
        }
        catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }
    
    public static String getFullNameMonthFromNumberMonth(Integer numMes) {
        String strMes = "";
        switch (numMes) {
        case 1:
            strMes = "ENERO";
            break;
        case 2:
            strMes = "FEBRERO";
            break;
        case 3:
            strMes = "MARZO";
            break;
        case 4:
            strMes = "ABRIL";
            break;
        case 5:
            strMes = "MAYO";
            break;
        case 6:
            strMes = "JUNIO";
            break;
        case 7:
            strMes = "JULIO";
            break;
        case 8:
            strMes = "AGOSTO";
            break;
        case 9:
            strMes = "SEPTIEMBRE";
            break;
        case 10:
            strMes = "OCTUBRE";
            break;
        case 11:
            strMes = "NOVIEMBRE";
            break;
        case 12:
            strMes = "DICIEMBRE";
            break;
        }
        return strMes;
    }
    
    public static String obtenerFechaDelSistemaEnDateTimeString(){
        try {
            LocalDateTime date = LocalDateTime.now ();
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern(PATTERN_YYYYMMDD_HHMMSS);
            return date.format(formatDate);
        }
        catch (Exception e) {
            return StringUtils.EMPTY;
        }
    }
}

