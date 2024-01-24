package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.service.impl.reporte.ReporteCompensacionService;

/**
 * Representa el criterio de búsqueda para los reporte resumen de transacciones
 * de compensación agrupadas por giro de negocio.
 * 
 * @author Hanz Llanto
 * @see ReporteCompensacionService
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaCompensacionPorGiroDeNegocio
{
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicioProceso;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFinProceso;
    
    private String descripcionRangoDeFechas;

    private Integer idInstitucion;
    private String descripcionInstitucion;

    private String idEmpresa;
    private String descripcionEmpresa;

    private String idCliente;
    private List<String> clientes;
    private String descripcionCliente;
}