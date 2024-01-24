package ob.debitos.simp.model.consulta.movimiento;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

import java.util.Date;

@Data
public class Bloqueo
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String idEmpresa;

    private String descEmpresa;

    private String idCliente;

    private String descCliente;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaBloqueo;

    private String fechaBloqueoCadena;

    private String horaBloqueo;

    private Integer tipoDocumento;

    private String descripcionTipoDocumento;

    private String numeroDocumento;

    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;

    private String codigoBloqueo;

    private String tipoBloqueo;

    private String descripcionTipoBloqueo;

    private String comentario;

}
