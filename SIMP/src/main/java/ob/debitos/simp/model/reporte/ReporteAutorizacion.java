package ob.debitos.simp.model.reporte;

import java.util.List;

import lombok.Data;

@Data
public class ReporteAutorizacion
{
    private Integer id;
    private String descripcion;
    private List<ReporteTransaccion> transacciones;
}