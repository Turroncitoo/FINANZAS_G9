package ob.debitos.simp.aspecto.enumeracion;

public enum Tipo
{
    /*Mantenimiento*/
	AFIN("MANT_AFINIDAD","Afinidad"),
    AFIN_CLTE("MANT_CLTEAFIN","asociación Afinidad-Cliente"),
    EMP("MANT_EMP","Empresa"),
    INST("MANT_INSTc","Institución"),
    CLTE("MANT_CLTE","Cliente"),
    BIN("MANT_BIN","BIN"),
    PRODUCTO("MANT_PRODUCT","PRODUCTO"),
    SUB_BIN("MANT_SUBBIN","SUBBIN"),
    SUB_BIN_CLTE("MANT_SUBBINCLTE","asociación SUBBIN-CLIENTE"),
    MEMB("MANT_MEMB","Membresía"),
    CLS_SERV("MANT_CLSSERV","Clase de Servicio"),
    ORIGEN("MANT_ORIGEN","Origen"),
    CLS_TXN("MANT_CLSTXN","Clase de Transacción"),
    COD_TXN("MANT_CODTXN","Código de Transacción"),
    CANAL("MANT_CANAL","Canal"),
    MET_ID_THB("MANT_METIDTHB","Método Id THB"),
    MODO_ENT_POS("MANT_MODOENTPOS","Modo de Entrada POS"),
    RPTA_CONCIL_UBA("MANT_RPTACONCILUBA","Respuesta de Conciliación UBA"),
    TI_TERM_POS("MANT_TITERMPOS","Tipo de Terminal POS"),
    IND_COL_TEL("MANT_INDCOTEL","Ind. Correo Teléfono"),
    MOT_RECLAMO("MANT_MOTRECLAMO","Motivo de Reclamo"),
    COD_PROC_SW("MANT_PROCSW","Código de Proceso Switch"),
    RPTA_SW("MANT_RPTASW","Código de Respuesta Switch"),
    COD_RPTA_VISA("MANT_RPTAVISA","Código de Respuesta VISA"),
    MANT_CANALSEGUROWS("MANT_CANALSEGUROWS","Canales Seguros Web Services"),
    CANAL_EMI("","Canal Emisor"),
    PROC_BTEC("","Código de Proceso Bevertec"),
    TRANS_BTEC("","Código de Transacción Bevertec"),
    TIPO_EMISION("","Tipo de emision"),
    MUL_TAB_CAB("MANT_MULTABCAB","Tabla de Tablas"),
    MUL_TAB_DET("MANT_MULTABDET","Detalle de Tabla de Tablas"),
    PARAM_GRAL("MANT_PARAMGRAL","Parámetros Generales"),
    PARAM_WS("MANT_PARAMWS","Parámetros Web Services"),
    CODIGO_FACTURACION("MANT_CODIGOFACTURACION", "Código Facturación"),
    CONTAB_EMI("MANT_CONTABEMI","Cuenta Contable Emisor"),
    CONTAB_MIS("MANT_CONTABMIS","Cuenta Contable Misceláneos"),
    CTA_CONTABLE("MANT_CTA", "Cuenta contable"),
    CTA_AJUSTE("MANT_CTAAJUS", "Cuenta Ajuste"),
    PARAM_SFTP("MANT_PARAMSFTP","Parámetros SFTP"),
    PARAM_SFTP_DIRECTORIO("MANT_PARAMSFTPDIR","Parámetros SFTP Directorio"),
    PARAM_SFTP_PROCESO("MANT_PARAMSFTPPRO","Parámetros SFTP Proceso"),
    PARAM_SFTP_ARCHIVO("MANT_PARAMSFTPARC","Parámetros SFTP Archivo"),
    PARAM_INT_CONT("MANT_PARAMINTCONT","Parametro Interface Contable"),
    PAN_ENTRY_MODE("MANT_PANENTRYMODE", "PAN Entry Mode"),
    PIN_ENTRY_CAP("MANT_PINENTRYCAPABILITY", "PIN Entry Capability"),
    INT_CONT("INT_CONT","Interface Contable Alegra"),
    MANT_CONTAC_RECAUDA("MANT_CONTAC_RECAUDA", "Regla Contable"),
    MANT_CONCEPTO_COMISION("MANT_CONCEPTO_COMISION", "Concepto Comision"),
    LOGO("MANT_LOGO","Logo"),
    // MANT_LOGO("MANT_CONCEPTO_COMISION", "Concepto Comision"),
    
    /*Reporte*/
    RPT_AUT_ATM("","Reporte de Autorización de ATM"),
    RPT_AUT_CANAL("RPT_AUTCANAL","Reporte de Autorización de Canal"),
    RPT_AUT_COD_RPTA("RPT_AUTCODRPTA","Reporte de Autorización de Código Respuesta"),
    RPT_AUT_ID_PROC("RPT_AUTIDPROC","Reporte de Autorización de Id Proceso"),
    RPT_CONT_MOV("RPT_CONTMOV","Reporte de Contabilización de Fondos Emisor"),
    RPT_CONT_COMIS("RPT_CONTCOMIS","Reporte de Contabilización de Comisiones"),
    RPT_CONT_DEFAULT("RPT_CONTDEFAULT", "Reporte de Contabilización en Cuentas Default"),
    RPT_MOV_AUT("","Reporte de Movimiento de Autorización"),
    RPT_MOV_SWDMPLOG("RPT_MOVSWDMPLOG","Reporte de Movimiento de SwDmpLog"),
    RPT_MOV_TRANS_AG("","Reporte de Movimiento de Transacción aprobada por agencia"),
    RPT_MOV_LG_CNT_EMI("RPT_MOVLGCNTEMI","Reporte de Movimiento de Log Contable Emisor"),
    RPT_MOV_LG_CNT_REC("","Reporte de Movimiento de Log Contable Receptor"),
    RPT_COMP_EMI_CANAL("RPT_COMPEMICANAL","Reporte de Compensación de Canal Emisor"),
    RPT_COMP_EMI_INST("RPT_COMPEMIINST","Reporte de Compensación de Institución Emisor"),
    RPT_COMP_EMI_GIRO_NEG("RPT_COMPEMIGIRNEG", "Reporte de Compensación de Giro de Negocio Emisor"),
    RPT_COMP_REC("","Reporte de Compensación de Institución Receptor"),
    RPT_COMIS_CUADRE("RPT_COMISCUADRE","Reporte Control Cuadre Banco Administrador"),
    RPT_CONCIL_OBS("RPT_CONCILOBS","Reporte de Conciliaciones Observadas"),
    RPT_SUMARIO_COMP("RPT_SUMARIOCOMP", "Reporte Sumario Compensación"),
    RPT_CONTABLE("RPT_CONT", "Reporte de Balance Contable"),
    RPT_EXTORNOS_CONTR("RPT_EXTCONT", "Reporte Extornos Controversias"),
    RPT_CONT_MISC("RPT_CONTMIS", "Reporte de Contabilización de Misceláneos"),
    RPT_CTAS_PAGAR("RPT_CUADREXPAGAR", "Reporte Cuadre Cuentas x Pagar"),
    RPT_FACTURACION_UBA("RPT_FACTURACIONUBA", "Reporte Facturación UNIBANCA"),
    RPT_CONCILIASALDOS("RPT_CONCILIASALDOS", "Reporte conciliación de saldos UNIBANCA"),
    RPT_TXNSTARJETA("RPT_TXNSTARJETA","Reporte transacciones por cantidad de tarjetas"),
	RPT_ESTADOTARJETAS("RPT_ESTADOTARJETAS", "Reportes Estado Tarjetas"),
    RPT_GENERACION_TARJ("RPT_GENERACION_TARJ", "Reporte de Tarjetas Generadas"),
    RPT_INGRESOS_COSTOS_TXN("RPT_INGRESOS_COSTOS_TXN", "Reporte de Ingresos y Costos por Transacción"),
    RPT_RECARGAS("RPT_RECARGAS","Reporte de Recargas"),
    RPT_TARJETASVENDIDAS("RPT_TARJETASVENDIDAS","Reporte de Tarjetas Vendidas"),
    RPT_PERSONAS("RPT_PERSONAS","Reporte de Personas"),
    RPT_CONSALDOSRESUMEN("RPT_CONSALDOSMES","Reporte de Conciliación Resumen de Saldos"),
    RPT_INTER_CONTABLE("RPT_INTER_CONTABLE", "Reporte de Interface Contable"),
    
    /*Consultas*/
    CON_ADMIN_CTA("CON_ADMINCTA","Cuentas"),
    CON_ADMIN_CLIENTE("","Clientes"),
    CON_ADMIN_AG("","Agencias"),
    CON_ADMIN_TARJETA("CON_ADMINTARJETA","Tarjetas"),
    CON_MOV_AUT("","Movimiento de Autorizaciones"),
    CON_MOV_NO_CONCIL("CON_MOVNOCONCIL","Movimiento de Autorizaciones No Conciliadas"),
    CON_MOV_SWDMPLOG("CON_MOVSWDMPLOG","Movimiento de SwDmpLog"),
    CON_MOV_LG_CNT("CON_MOVLGCNT","Movimiento de Log Contable"),
    CON_MOV_LIB("CON_MOVLIB","Movimiento de Liberadas"),
    CON_MOV_PEND("CON_MOVPEND","Movimiento de Pendientes"),
    CON_MOV_SAL("CON_MOVSAL","Movimiento de Saldos"),
    CON_BLOQ("CON_BLOQ","Bloqueadas"),
    CON_MOV_CONSOL("CON_MOVCONSOL","Movimiento de Consolidadas"),
    CON_MOV_OBS("CON_MOVOBS","Movimiento de Observadas"),
    CON_MOV_AJUS("CON_MOVAJUS","Movimiento Ajuste"),
    CON_EMBOSSING("CON_EMBOSSING","Embossing"),
    CON_TXNWS("CON_TXNWS","Movimiento Web Services"),
    CON_CAMB_VISA("CON_CAMBVISA","Tipo de Cambio Visa"),
    CON_CAMB_SBS("CON_CAMBSBS","Tipo de Cambio SBS"),
    CON_ADMIN_TARJETA_PP("CON_ADMINTARJETA","Tarjetas Prepago"),
    CON_ADMIN_CUENTA_PP("CON_ADMINCTA","Cuentas Prepago"),
    CON_ADMIN_LOTE_PP("CON_ADMINLOT","Lotes Prepago"),
    CON_ADMIN_RECARGA_PP("CON_ADMINRECARGA","Recargas Prepago"),
    CON_ADMIN_PERSONA_PP("CON_ADMINPER","Personas Prepago"),
    CON_LOGCON_PROGRESUMEN("RPT_LOGCONPROCRES","Consulta Log Control Programa Resumen"),
    CON_LOG_INTER_ALEGRA("CON_LOG_INTER_ALEGRA", "Consulta Log Interface Alegra"),

    /*Ajuste*/
    TRACE("","Trace"),

    /*Proceso Automático*/
    PROG("MANT_PROG","Programa"),
    PROC_AUTO("MANT_PROCAUTO","Proceso Automático"),
    SUB_MOD("MANT_SUBMOD","SubMódulo"),
    EJEC_MAN("EJEC_MANUAL","Ejecución Manual"),
    CTRL_PROC("EJEC_CTRLPROC","Control de Ejecución de Procesos"),
    SERV_WEB("","Servicios Web"),
    COMIS("","Calculo de Comisiones"),
    PROCESO_SFTP("EJEC_PAIU", "Descarga"),
    COMPENSACION("EJEC_COMPENSACION","Compensación"),
    CONCILIACION("EJEC_CONCILIACION","Conciliación"),
    CONTABILIZACION("EJEC_CONTABILIZACION","Contabilización"),
	REQUERIM_PREPAGO("EJEC_PREP","Requerimientos Prepago"),
    AVANCE_FECHA_PROCESO("EJEC_AVANCEFECHAPROCESO","Avanze de Fecha Proceso"),
    CON_LOG_PROC_AUTOM("CON_LOGPROCAUTOM","Consulta de Log de Proceso Automático"),
	CONTABILIZACION_ARCHIVOS("","Contabilizacion Archivos"),
    CARGA_MANUAL_LOTE("","Carga Manual Lote"),

    /*Login*/
    LOGIN("LOGIN","Login"),
    LOGOUT("LOGOUT","Logout"),
    
    /*Tarjetas*/
    AFILIACIONES("Afilaciones","Afiliaciones de tarjetas"),
    
    /*Seguridad*/
    PERFIL("MANT_PERFIL","Perfil"),
    USUARIO("MANT_USUARIO","Usuario"),
    CAMB_CONTRASENIA("CAMB_CONTRASENIA","Cambio de Contraseña"),//OK
    PERFIL_RECURSO("MANT_PERFIL_RECURSO","PerfilRecurso"),
    POLIT_SEG("MANT_POLITICA_SEGURIDAD","Política de Seguridad"), //OK
    LLAVES("SEC_LLAVES","Ceremonia de Llaves"),

    NINGUNO("N","Ninguno"),
    CON_WS_SALDOS_MOV("WS_SALDOS_MOVIMIENTOS","Consulta de WS Saldos y Movimientos"),
    CON_WS_TARJ_CLTES("WS_CLIENTES_TARJETAS","Consulta de Tarjetas y Clientes"),
    CON_WS_SER_WEB("","Consulta Servicio Web "),
    RPT_MOV_COMP("","Reporte de TxnsCompensación"),
	RPT_MOV_AJUSTE("","Reporte de TxnsAjustes"),
	VALID_CLIENTES("","Validación de clientes");

	private final String idRecurso;
    private final String descripcion;

    private Tipo(String idRecurso, String descripcion)
    {
        this.descripcion = descripcion;
        this.idRecurso = idRecurso;
    }

    public String getIdRecurso(){
    	return this.idRecurso.toUpperCase();
    }

    public String getDescripcion(){
    	return this.descripcion;
    }
}
