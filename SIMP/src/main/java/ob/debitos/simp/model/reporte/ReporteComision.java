package ob.debitos.simp.model.reporte;

import lombok.Data;

@Data
public class ReporteComision
{
    private Integer idConceptoComision;
    private String descripcionConceptoComision;
    private String registroContable;
    private double comision;
}