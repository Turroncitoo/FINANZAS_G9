package ob.debitos.simp.model.consulta.administrativa;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Cuenta
{
    private String numeroTarjeta;
    private String numeroCuenta;
    private Integer idAgencia;
    private String descripcionAgencia;
    private String codigoEstadoCuenta;
    private String descripcionEstadoCuenta;
    private String idCliente;
    private String codigoTipoDocumento;
    private String descripcionTipoDocumento;
    private String numeroDocumento;
    private String codigoCuenta;
    private String codigoTipoCuenta;
    private String descripcionTipoCuenta;
    private String codigoProducto;
    private String descripcionProducto;
    private Integer codigoMoneda;
    private String descripcionMoneda;
    private String codigoModalidadDeposito;
    private String descripcionModalidadDeposito;
    private String codigoEstadoRelacion;
    private String descripcionEstadoRelacion;
    private Integer cuentaFastCash;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaAutorizacion;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaActualizacion;
}