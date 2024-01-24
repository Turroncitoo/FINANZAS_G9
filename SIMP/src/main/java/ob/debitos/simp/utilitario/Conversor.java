package ob.debitos.simp.utilitario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Conversor
{
    public static Integer integerValueOf(Object o)
    {
        return Integer.valueOf(String.valueOf(o));
    }

    public static String stringValueOf(Object o)
    {
        return String.valueOf(o);
    }

    public static Date dateValueOf(String date) throws ParseException
    {
        return (date.isEmpty() || date == null) ? new Date()
                : new SimpleDateFormat("dd-MM-yyyy").parse(date);
    }

    public static java.sql.Date dateValueOfOrCurrentDate(String fecha)
    {
        return (fecha.isEmpty()) ? new java.sql.Date(new Date().getTime())
                : new java.sql.Date(Conversor.StringToDate(fecha).getTime());
    }
    
    public static java.sql.Date utilDateToSqlDate(Date fecha)
    {
        return new java.sql.Date(fecha.getTime());
    }

    public static Double doubleValueOf(Object o)
    {
        return Double.valueOf(String.valueOf(o));
    }

    public static Short shortValueOf(Object o)
    {
        return Short.valueOf(String.valueOf(o));
    }

    public static Byte byteValueOf(Object o)
    {
        return Byte.valueOf(String.valueOf(o));
    }

    public static Boolean booleanValueOf(Object o)
    {
        return Boolean.parseBoolean(String.valueOf(o));
    }

    public static Date StringToDate(String date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date fum = null;
        try
        {
            if (date != null)
                fum = format.parse(date);
        } catch (ParseException e)
        {
        }
        return fum;
    }

    public static Map<String, Object> toMapJSON(Map<String, Object> mapOriginal,
            Map<String, Object> mapJSON) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Map.Entry<String, Object> entry : mapOriginal.entrySet())
        {
            mapJSON.put(entry.getKey(), objectMapper.writeValueAsString(entry.getValue()));
        }
        return mapJSON;
    }
}