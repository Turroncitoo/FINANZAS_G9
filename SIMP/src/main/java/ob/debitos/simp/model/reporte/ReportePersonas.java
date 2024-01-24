package ob.debitos.simp.model.reporte;

import lombok.Data;


@Data
public class ReportePersonas {
private Integer idInstitucion;
private String descripcionInstitucion;
private Integer idOperacion;
private String descripcionOperacion;
private Integer cantidadTransacciones;
private Double montoTotal;
private Double promedioMonto;
}
