package ob.debitos.simp.model.proceso;

import lombok.Data;

@Data
public class ConsultaPrograma
{
    private String codigoPrograma;
    private String codigoSubModulo;
    private String descripcionPrograma;
    private String descripcionSubModulo;
    private Integer ordenEjecucion;
    private Integer estado;
    private String procedimiento;
    private String urlSistema;
}
