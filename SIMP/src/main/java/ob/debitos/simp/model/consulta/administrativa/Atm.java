package ob.debitos.simp.model.consulta.administrativa;

import lombok.Data;

@Data
public class Atm
{

    private Integer codigoInstitucion;
    private String descripcionInstitucion;
    private Integer idATM;
    private String descripcionATM;
    private String direccion;
    private String distrito;
    private String provincia;
    private String departamento;
    private String tipoATM;
    private Integer idAgencia;
    private String descripcionAgencia;

}
