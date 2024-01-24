package ob.debitos.simp.model.prepago;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
public class Saldo
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    private String idLogo;

    private String descripcionLogo;

    private String descLogoBin;

    private String codigoProducto;

    private String descProducto;

    private Integer tipoDocumento;

    private String descripcionTipoDocumento;

    private String numeroDocumento;

    private Integer idPersona;

    private String idCuenta;

    private String nombres;

    private String apellidoPaterno;

    private String apellidoMaterno;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String estado;

    private String descEstado;

    private Integer moneda;

    private String descMoneda;

    private Double saldoDisponible;

}
