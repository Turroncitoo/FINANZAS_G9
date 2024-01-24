package ob.debitos.simp.utilitario;

public class ReporteUtil
{
    public static final Integer PRESENTACION_POR_DIA = 1;
    public static final Integer PRESENTACION_POR_MES = 2;
    
    public static final String RESUMEN_AUTORIZACION = "autorizacion";
    public static final String RESUMEN_SW_DMP_LOG = "swDmpLog";
    public static final String RESUMEN_LOG_CONTABLE_EMISOR = "logContableEmisro";
    public static final String RESUMEN_LOG_CONTABLE_RECEPTOR = "logContableReceptor";
    public static final String RESUMEN_TRANSACCION_APROBADA_AGENCIA = "transaccionAprobadaAgencia";

    public static String obtenerVerboPorTipoPresentacion(String tipoAutorizacion,
            int tipoPresentacion)
    {
        StringBuffer sb = new StringBuffer();
        String verboTipoPresentacion = (tipoPresentacion == PRESENTACION_POR_DIA)
                ? Verbo.AUTORIZACION_POR_DIA : Verbo.AUTORIZACION_POR_MES;
        return sb.append(tipoAutorizacion.toUpperCase()).append("_").append(verboTipoPresentacion).toString();
    }

    public static String obtenerVerboPorTipoTarifario(String tipoTarifario)
    {
        StringBuffer sb = new StringBuffer();
        String tipoReporte = tipoTarifario.substring(0, 7);
        String tarifario = tipoTarifario.substring(7, tipoTarifario.length());
        return sb.append(tarifario.toUpperCase()).append("_").append(tipoReporte.toUpperCase()).toString();
    }
    
    public static String obtenerVerboPorTipoCompensacion(String tipo)
    {
        return tipo.toUpperCase();
    }
}
