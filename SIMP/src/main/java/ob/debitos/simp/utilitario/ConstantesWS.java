package ob.debitos.simp.utilitario;

public class ConstantesWS
{
    
    
    public enum RecursosWS {
        SALDO,
        MOVIMIENTOS
    }
    
    public enum AccionesWS {
        REASIGNAR,
        RECARGAR,
        ACTIVAR,
        BLOQUEAR,
        ASOCIAR,
        REGISTRAR,
        CONSULTAR_POR_TARJETA
    }
    
    //Configuración
    public static final String RUTA_SIMP_HUB = "ruta-simp-hub";

    //Operaciones disponibles
    public static final String OP_WS_CONSULTA_SALDO = "ws-consulta-saldo" ;
    public static final String OP_WS_CONSULTA_MOVIMIENTOS = "ws-consulta-movimientos";
    public static final String OP_WS_REASIGNAR_TARJETA = "ws-reasignar-tarjeta";
    public static final String OP_WS_RECARGAR_TARJETA = "ws-recargar-tarjeta";
    public static final String OP_WS_ACTIVAR_TARJETA = "ws-activar-tarjeta";
    public static final String OP_WS_BLOQUEAR_TARJETA = "ws-bloquear-tarjeta";
    public static final String OP_WS_ASOCIAR_TARJETA = "ws-asociar-tarjeta";
    public static final String OP_WS_CONSULTAR_CLIENTE_POR_TARJETA = "ws-consultar-cliente-por-tarjeta";

    public static final String OP_WS_REGISTRAR_CLIENTE = "ws-registrar-cliente";
    
    public static final String SEC_HASH = "00000000000000000000000000000000"; 
    
    
    
    
}
