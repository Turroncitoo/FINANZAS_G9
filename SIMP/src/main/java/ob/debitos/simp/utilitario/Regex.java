package ob.debitos.simp.utilitario;

public class Regex
{
    public static final String SOLO_DIGITOS = "\\d+";
    public static final String SOLO_DIGITOS_O_VACIO = "^$|\\d+";
    public static final String FORMATO_HHMMSS = "^(?:2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]$";
    public static final String FORMATO_HHMM = "^(?:2[0-3]|[01][0-9]):[0-5][0-9]";
    public static final String SOLO_LETRAS_A_a = "^[A-Za-z]+$";
    public static final String LETRAS_AND_ESPACIO_BLANCO = "^[^[A-Za-z]+$]+(\\s+[^[A-Za-z]+$]+)*$";
    public static final String LETRAS_GUION_BAJO = "^[A-Za-z_]+$";
    public static final String SEGURIDAD_PASS = "^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{1,}$";
    public static final String NOMBRE_EXTENSION_ARCHIVO = "^[A-Za-z\\(\\)\\-]+$";

    public static final String ALFANUMERICO = "^[A-Za-z0-9]+$";
    public static final String VACIO_O_ALFANUMERICO = "^$|^[A-Za-z0-9]+$";

    public static final String SOLO_1_o_0 = "0|1";
    public static final String VACIO_O_NO_SOLO_ESPACIOS_EN_BLANCO = "^$|^(?=.*\\S).+$";
    public static final String NO_ESPACIOS_EN_BLANCO = "^\\S+$";
    public static final String ESPACIOS_EN_BLANCO = "\\s+";
    public static final String TIPO_COMPENSACION = ConstantesGenerales.COMPENSACION_FONDO + "|"
            + ConstantesGenerales.COMPENSACION_COMISION;
    
    public static final String DESCRIPCION = "^[a-zA-Z\\d\\.\\-,:_\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+(\\s+[a-zA-Z\\d\\.\\-,:_\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+)*$";
    public static final String VACIO_O_DESCRIPCION = "^$|^[a-zA-Z\\d\\.\\-,:_\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+(\\s+[a-zA-Z\\d\\.\\-,:_\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+)*$";
    
    public static final String RUTA = "^[a-zA-Z\\d\\*\\.\\-\\[\\]\\(\\)\\,:_%\\u005c\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+(\\s+[a-zA-Z\\d\\*\\.\\-\\[\\]\\(\\)\\,:_%\\u005c\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+)*$";
    public static final String DESCRIPCION_SIN_ESPACIOS = "^[a-zA-Z\\d\\*\\.\\-\\[\\]\\,:_%\\u005c\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+(\\s+[a-zA-Z\\d\\*\\.\\-\\[\\]\\,:_%\\u005c\u00f1\u00e1\u00e9\u00ed\u00f3\u00fa\u00c1\u00c9\u00cd\u00d3\u00da\u00d1\u002f\u0020]+)*$";
	public static final String NOMBRE_USUARIO = "^[A-Za-z0-9_]+$";
	
}