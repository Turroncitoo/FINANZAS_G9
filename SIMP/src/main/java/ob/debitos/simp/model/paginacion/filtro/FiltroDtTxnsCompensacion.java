package ob.debitos.simp.model.paginacion.filtro;

import lombok.Data;

@Data
public class FiltroDtTxnsCompensacion
{
    private String fechaProceso;

    private String institucion;

    private String empresa;

    private String cliente;

    private String logo;

    private String producto;

    private String membresia;

    private String servicio;

    private String origen;

    private String claseTransaccion;

    private String codigoTransaccion;

    private String rolTransaccion;

    private String institucionEmisora;

    private String institucionReceptora;

    private String codigoRespuesta;

    private String canal;

    private String tipoDocumento;

    private String numeroDocumento;

    private String secuenciaTransaccion;

    private String codigoSeguimiento;

    private String numeroTarjeta;

    private String numeroCuenta;

    private String estadoTarjeta;

    private String fechaTransaccion;

    private String numeroVoucher;

    private String codigoAutorizacion;

    private String monedaTransaccion;

    private String valorTransaccion;

    private String monedaAutorizacion;

    private String valorAutorizacion;

    private String monedaCompensacion;

    private String valorCompensacion;

    private String monedaCargadaThb;

    private String valorCargadoThb;

    private String tipoDeCambio;

    private String nombreAfiliado;

    private String numeroDocumentoCompensacion;

    private String compensaFondos;

    private String compensaComisiones;
}
