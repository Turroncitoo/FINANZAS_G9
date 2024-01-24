package ob.debitos.simp.model.reporte;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReporteGeneracionTarjetasPorAfinidad {
    private String idMembresia;
    private String descripcionMembresia;

    private String idAfinidad;
    private String codigoAfinidad;
    private String descripcionAfinidad;

    private String idBin;
    private String descripcionBin;

    private Integer cantidadTarjetas;
}
