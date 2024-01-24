package ob.debitos.simp.model.consulta.administrativa;

import lombok.Data;

@Data
public class Agencia
{
    private Integer idAgencia;
    private String descripcion;
    private String direccion;
    private String codigoPlaza;
    private String codigoSubPlaza;
    private String codigoUbigeo;
    private String descripcionDepartamento;
    private String descripcionProvincia;
    private String descripcionDistrito;
}