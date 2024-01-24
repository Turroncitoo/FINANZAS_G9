package ob.debitos.simp.model.consulta.administrativa;

import java.util.Date;

import lombok.Data;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
public class Tarjeta
{
    @NoIdentificable(campo="numeroTarjeta")
    private String numeroTarjeta;
    private String codigoTipoDocumento;
    private String descripcionTipoDocumento;
    private String numeroDocumento;
    private String idCliente;
    private String nombres;
    private String apellidos;
    private String codigoBIN;
    private String descripcionBIN;
    private String codigoTipoTarjeta;
    private String descripcionTipoTarjeta;
    private Integer idAgencia;
    private String descripcionAgencia;
    private String codigoEstadoTarjeta;
    private String descripcionEstadoTarjeta;
    private String estado;
    private Date fechaActivacion;
    private Date fechaActualizacion;
    private Date fechaBloqueoUBA;
    private String codigoTipoBloqueo;
    private String descripcionTipoBloqueo;
    private Integer codigoPais;
    private String descripcionPais;
    private Date fechaVencimiento;
    private String codigoFranquicia;
    private String descripcionFranquicia;
    private String codigoAsesor;
    private String codigoCategoria;
    private String descripcionCategoria;
    private Integer idPersona;
    private String grabacionTarjeta;
}