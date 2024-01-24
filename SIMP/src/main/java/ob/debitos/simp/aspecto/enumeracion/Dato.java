package ob.debitos.simp.aspecto.enumeracion;

public enum Dato
{
    /*Mantenimientos*/
	AFINIDAD("Afinidad = #afinidad.codigo, Descripción = #afinidad.descripcionAfinidad, Id_BIN = #afinidad.idBIN"),
    AFINIDAD_CLTE("Afinidad = #clienteAfinidad.idAfinidad, Cliente = #clienteAfinidad.idCliente, Empresa = #clienteAfinidad.idEmpresa"),
    BIN("BIN = #bin.codigoBIN, Descripción = #bin.descripcion"),
    PRODUCTO("BIN = #producto.idProducto, Descripción = #producto.descripcion"),
    EMPRESA("Empresa = #empresa.idEmpresa, Descripción = #empresa.descripcion"),
    INSTITUCION("Institución = #institucion.codigoInstitucion, Descripción = #institucion.descripcion, Institucion_empresa = #institucion.institucionEmpresa"),
    CLIENTE("Cliente = #cliente.idCliente, Empresa = #cliente.idEmpresa, Descripción = #cliente.descripcion"),
    CANALSEGUROWS("Canal = #canalesSegurosWS.idCanal, Contrasenia = #canalesSegurosWS.contrasenia, Activo = #canalesSegurosWS.activo, Empresa = #canalesSegurosWS.idEmpresa, Cliente = #canalesSegurosWS.idCliente"),
    SUB_BIN("SubBIN = #subBin.codigoSubBIN, BIN = #subBin.codigoBIN, Descripción = #subBin.descripcion"),
    SUB_BIN_CLTE("SubBIN = #subBin.codigoSubBIN, BIN = #subBin.codigoBIN, Cliente = #subBin.idCliente"),
    MEMBRESIA("Membresía = #membresia.codigoMembresia, Descripción = #membresia.descripcion"),
    CLASE_SERVICIO("Clase_de_Servicio = #claseServicio.codigoClaseServicio, Membresía=#claseServicio.codigoMembresia,Descripción = #claseServicio.descripcion"),
    ORIGEN("Origen = #origen.codigoOrigen, Descripción = #origen.descripcion"),
    CLASE_TRANSACCION("Clase_Transacción = #claseTransaccion.codigoClaseTransaccion, Descripción = #claseTransaccion.descripcion"),
    COD_TRANSACCION("Clase_Transacción = #codigoTransaccion.codigoClaseTransaccion, Código_de_Transacción = #codigoTransaccion.codigoTransaccion, Descripción = #codigoTransaccion.descripcion"),
    CANAL("Canal = #canal.idCanal, Descripción = #canal.descripcion"),
    METODO_ID_THB("Método_THB = #metodoIdThb.idMetodo, Descripción = #metodoIdThb.descripcion"),
    MODO_ENTRADA_POS("Modo_de_Entrada_POS = #modoEntradaPos.codigoModoEntradaPOS, Descripción = #modoEntradaPos.descripcion"),
    RPTA_CONCIL_UBA("Rpta._Conciliacion_UBA = #rptaConcilUba.idRespuestaConcilUba ,Descripción = #rptaConcilUba.descripcion"),
    TIPO_TERMINAL_POS("Tipo_de_Terminal_POS = #tipoTerminalPos.codigoTipoTerminalPOS, Descripción = #tipoTerminalPos.descripcion"),
    TIPO_EMISION("TipoEmision = #tipoEmision.codigo, Descripción = #tipoEmision.descripcion"),
    IND_CORREO_TELF("Ind_Correo_Telefono = #indCorreoTelefono.codigoIndCorreoTelefono, Descripción = #indCorreoTelefono.descripcion"),
    MOTIVO_RECLAMO("Motivo_de_Reclamo = #motivoReclamo.idMotivo, Descripción = #motivoRecla.descripcion"),
    COD_PROC_SWITCH("Código_de_Proceso_Switch = #codigoProcesoSwitch.codigoProcesoSwitch, Descripción = #codigoProcesoSwitch.descripcion"),
    COD_RPTA_SWITCH("Código_de_Respuesta_Switch = #codigoRptaSwitch.codigoRespuestaSwitch, Descripción = #codigoRptaSwitch.descripcion"),
    COD_RPTA_VISA("Código_de_Respuesta_Visa = #codigoRptaVisa.codigoRespuestaVisa, Descripción = #codigoRptaVisa.descripcion, Atribuible=#codigoRptaVisa.atribuible"),
    CANAL_EMISOR("Canal_Emisor = #canalEmisor.codigoCanalEmisor, Descripción = #canalEmisor.descripcion"),
    COD_PROC_BEVERTEC("Código_de_Proceso_Bevertec = #codigoProcBevertec.codigoCanalEmisor , Tipo_Transacción = #codigoProcBevertec.tipoTransaccion, Descripción = #codigoProcBevertec.descripcion"),
    COD_TRANSACCION_BEVERTEC("Canal_Emisor = #codigoTxnBevertec.codigoCanalEmisor, Tipo_Transacción = #codigoTxnBevertec.tipoTransaccion, Código_de_Transacción = #codigoTxnBevertec.codTransaccion"),
    MULTI_TAB_CAB("Id_de_Tabla = #multiTabCab.idTabla, Descripción = #multiTabCab.descripcion"),
    MULTI_TAB_DET("Id_Detalle = #multiTabDet.idItem,Id_Tabla = #multiTabDet.idTabla, Descripción = #multiTabDet.descripcionItem"),
    PARAM_GRAL("Fecha_Proceso = #parametroGeneral.fechaProceso, Bin_de_Ruteo = #parametroGeneral.binRuteoSwitch, Institución = #parametroGeneral.codigoInstitucion, Surcharge_Soles = #parametroGeneral.surchargeSoles, Surcharge_Dolares = #parametroGeneral.surchargeDolares,Empresa = #parametroGeneral.idEmpresa, IGV = #parametroGeneral.porcentajeIgv"),
    PARAM_WS("PathHostWS = #parametroWS.pathHostWS, LlaveInstalacion = #parametroWS.llaveInstalacion, LlaveTransporteZMK = #parametroWS.llaveTransporteZMK, LlaveTrabajoZPK = #parametroWS.llaveTrabajoZPK, TokenParaHabilitarRestWS = #parametroWS.tokenParaHabilitarRestWS,TiempoExpiracionTokenEnMinutos = #parametroWS.tiempoExpiracionTokenEnMinutos, TiempoTimeOutUbaEnSegundos = #parametroWS.tiempoTimeOutUbaEnSegundos, PathBaseParaConsultaDesdeSIMPWeb = #parametroWS.pathBaseParaConsultaDesdeSIMPWeb"),
    CODIGO_FACTURACION("Id_codigo_facturacion = #codigoFacturacion.idCodigoFacturacion, descripcion=#codigoFacturacion.descripcion"), 
    CTA_CONTABLE_EMI("Empresa = #cuentaContableEmisor.idEmpresa, Cliente = #cuentaContableEmisor.idCliente, Moneda = #cuentaContableEmisor.codigoMoneda, Membresía = #cuentaContableEmisor.codigoMembresia, Clase_Servicio = #cuentaContableEmisor.codigoClaseServicio, BIN = #cuentaContableEmisor.codigoBIN, SubBIN = #cuentaContableEmisor.codigoSubBIN, Origen = #cuentaContableEmisor.codigoOrigen, Rol = #cuentaContableEmisor.idRolTransaccion, Clase_Transacción = #cuentaContableEmisor.codigoClaseTransaccion, Código_Transacción = #cuentaContableEmisor.codigoTransaccion"),
    CTA_CONTABLE_REC("ATM = #cuentaContableReceptor.idATM, Moneda = #cuentaContableReceptor.codigoMoneda, Membresía = #cuentaContableReceptor.codigoMembresia, Clase_Servicio = #cuentaContableReceptor.codigoClaseServicio, Origen = #cuentaContableReceptor.codigoOrigen, Clase_Transacción = #cuentaContableReceptor.codigoClaseTransaccion, Código_Transacción = #cuentaContableReceptor.codigoTransaccion"), 
    CTA_CONTABLE_MISC("Empresa = #cuentaContableMiscelaneo.idEmpresa, Cliente = #cuentaContableMiscelaneo.idCliente, Moneda = #cuentaContableMiscelaneo.codigoMoneda, Membresía = #cuentaContableMiscelaneo.codigoMembresia, Clase_Servicio = #cuentaContableMiscelaneo.codigoClaseServicio, Origen = #cuentaContableMiscelaneo.codigoOrigen, Rol = #cuentaContableMiscelaneo.idRolTransaccion, Clase_Transacción = #cuentaContableMiscelaneo.codigoClaseTransaccion, Código_Transacción = #cuentaContableMiscelaneo.codigoTransaccion"),
    CTA_CONTABLE("Cuenta_Contable = #cuentaContable.idCuenta, Número = #cuentaContable.numeroCuentaContable, Descripción = #cuentaContable.descripcion, Codigo_Moneda = #cuentaContable.codigoMoneda, Cuenta_ATM = #cuentaContable.cuentaATM, Cuenta_Base = #cuentaContable.cuentaBase, Cuenta_Ajuste = #cuentaContable.cuentaBase"),
    CTA_AJUSTE("Tipo_de_Movimiento = #cuentaAjuste.tipoMovimiento, Registro_Contable = #cuentaAjuste.registroContable, Moneda_Compensación = #cuentaAjuste.monedaCompensacion, Rol_Transacción = #cuentaAjuste.rolTransaccion"),
    PARAM_SFTP_ARCHIVO("Codigo=#parametroSFTP.codigoArchivo, Codigo_Proceso=#parametroSFTP.codigoProceso, Tipo_Operacion=#parametroSFTP.tipoOperacion, Origen=#parametroSFTP.origenOriginal, Extension_Origen=#parametroSFTP.extensionOrigenOriginal, Habilitado=#parametroSFTP.habilitado, Valida_header=#parametroSFTP.validaHeader, Numero_Bytes=#parametroSFTP.numeroBytes, Carga_incremental=#parametroSFTP.cargaIncremental"),
    PARAM_SFTP("Host=#parametroSFTP.host, Usuario=#parametroSFTP.usuario, Contrasenia=#parametroSFTP.contrasenia, Puerto=#parametroSFTP.puerto"),
    PARAM_SFTP_DIRECTORIO("Codigo_Proceso=#parametroSFTP.codigoProceso,Tipo_Operacion=#parametroSFTP.tipoOperacion, Directorio_Origen=#parametroSFTPDirectorio.directorioOrigen, Directorio_Destino=#parametroSFTPDirectorio.directorioDestino, Borra_Fichero=#parametroSFTPDirectorio.borraFichero"),
    PARAM_SFTP_PROCESO("Codigo_Proceso=#parametroSFTP.codigo, Descripcion=#parametroSFTP.descripcion"),
    PARAM_INT_CONT("Usuario_Alegra=#parametroIntCon.usuarioAlegra, Token_Alegra=#parametroIntCon.tokenAlegra, Cuenta_Contable_URI=#parametroIntCon.cuentaContableURI, Activo=#parametroIntCon.activo, Pago_URI=#parametroIntCon.pagoURI"),
    LOGO("LOGO = #logo.codigoBIN, Descripción = #logo.descripcion"),
    
    PAN_ENTRY_MODE("Codigo=#panEntryMode.codigo, Descripción=#panEntryMode.descripcion"),
    PIN_ENTRY_CAP("Codigo=#pinEntryCapability.codigo, Descripción=#pinEntryCapability.descripcion"),
    
    CONCEPTO_COMISION("Concepto_Comisión=#conceptoComision.idConceptoComision, Descripción=#conceptoComision.descripcion, Tipo_Comisión=#conceptoComision.tipoComision, Abreviatura = #conceptoComision.abreviatura, Rol_Emisor = #conceptoComision.rolEmisor, Rol_Receptor = #conceptoComision.rolReceptor, Activado = #conceptoComision.activado"),
    
    /*Ajustes*/
    TRACE("Trace = #trace.trace, Secuencia = #trace.secuencia"),
    
    /*Ejecucion Proceso Manual*/
    LOG_CTRL_PROG_RES("Grupo = #logControlProgramaResumen.codigoGrupo, Programa = #logControlProgramaResumen.codigoPrograma, SubModulo = #logControlProgramaResumen.codigoSubModulo"),
    PROCESO_AUTOM("Grupo = #procesoAutomatico.codigoGrupo, Descripción = #procesoAutomatico.descripcion"),
    PROGRAMA("Programa = #programa.codigoPrograma, Grupo = #programa.codigoGrupo, SubModulo = #programa.codigoSubModulo, Descripción = #programa.descripcion"),
    SUB_MODULO("SubModulo = #subModulo.codigoSubModulo, Descripción = #subModulo.descripcion"),
    
    /*Seguridad*/
    PERFIL("Codigo_Perfil = #perfil.idPerfil, Descripción_Perfil = #perfil.descripcion, Visualiza_PAN = #perfil.visualizaPAN"),
    RECURSO("Codigo_Recurso = #recurso.idRecurso, Descripción_Recurso = #recurso.descripcion"),
    CAMB_CONTRASENIA("Contraseña_actual = #cambioContrasenia.contraseniaActual, Contraseña_Nueva = #cambioContrasenia.contrasenia, Contraseña_Confirmación = #cambioContrasenia.contraseniaConfirmacion"),
    PERFIL_RECURSO("Codigo_PerfilRecurso = #perfil.idPerfil, Descripción_PerfilRecurso = #perfil.descripcion"),
    POLIT_SEG("Numero_Maximo_de_Intentos = #politicaSeguridad.numeroMaximoIntentos, "
            + "Complejidad_Contrasenia = #politicaSeguridad.complejidadContrasenia, "
            + "Cantidad_de_días_para_caducidad_de_contraseña = #politicaSeguridad.cantidadDiasParaCaducidadContrasenia, "
            + "Longitud_mínima_contraseña = #politicaSeguridad.longitudMinimaContrasenia, "
            + "Autenticación_Active_Directory = #politicaSeguridad.autenticacionActiveDirectory"),
    USUARIO("Codigo_de_Usuario = #usuario.idUsuario"),
    LLAVES("Codigo_de_Usuario = #usuario.idUsuario"),
    
    NINGUNO("");
    private final String nombre;

    private Dato(String nombre)
    {
        this.nombre = nombre;
    }

    public String toString()
    {
        return this.nombre;
    }
}
