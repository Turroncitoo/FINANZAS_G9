package ob.debitos.simp.model.consulta.administrativa;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ClientePersona
{
    private String idCliente;
    private String codigoTipoDocumento;
    private String descripcionTipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private Integer codigoPais;
    private String descripcionPais;
    private String idSexo;
    private String descripcionSexo;
    private String idEstadoCivil;
    private String descripcionEstadoCivil;
    private String primerTelefono;
    private String segundoTelefono;
    private String email;
    private String direccion;
    private String clasePersona;
    private String descripcionClasePersona;
    private String codigoConyuge;
    private String codigoUbigeo;
    private String descripcionDepartamento;
    private String descripcionProvincia;
    private String descripcionDistrito;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaNacimiento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaContrato;
}