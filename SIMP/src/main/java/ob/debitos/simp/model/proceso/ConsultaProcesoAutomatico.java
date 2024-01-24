package ob.debitos.simp.model.proceso;

import java.util.List;

import lombok.Data;

@Data
public class ConsultaProcesoAutomatico
{
    private String codigoGrupo;
    private String descripcionGrupo;
    private Integer ordenEjecucion;
    private String tipo;
    private List<ConsultaPrograma> programas;
}