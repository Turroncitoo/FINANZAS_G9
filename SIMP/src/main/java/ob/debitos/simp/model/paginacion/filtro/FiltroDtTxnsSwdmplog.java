package ob.debitos.simp.model.paginacion.filtro;

import lombok.Data;

@Data
public class FiltroDtTxnsSwdmplog
{
    private String fechaProceso;

    private String institucion;

    private String empresa;

    private String cliente;

    private String logo;

    private String producto;

    private String bin;

    private String subBin;

    private String rolTransaccion;

    private String codigoProceso;

    private String codigoRespuesta;

    private String canal;

    private String fechaTransmision;

    private String fechaTransaccion;

    private String tipoMensaje;

    private String tipoDocumento;

    private String numeroDocumento;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    private String numeroTarjeta;

    private String codigoSeguimiento;

    private String identificadorCuenta;

    private String estadoTarjeta;

    private String moneda;

    private String importe;

    private String giroNegocio;

    private String trace;

    private String codigoAutorizacion;

    private String descripcionAdquirente;

    private String institucionAdquirente;

    private String institucionSolicitante;

    private String fechaSwitch;

    private String fechaTransaccionLocal;

    private String modoIngresoPan;

    private String capacidadIngresoPan;

    private String cuentaCargo;

    private String cuentaAbono;

    private String codigoAnalitico;
}
