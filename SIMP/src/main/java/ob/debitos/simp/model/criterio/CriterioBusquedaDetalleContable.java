package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Representa el criterio de búsqueda para el detalle de las transacciónes
 * provenientes del archivo Log Contable.
 * 
 * @author Robert Vega
 */
@Data
public class CriterioBusquedaDetalleContable
{
    private String verbo;
    private List<String> criterios;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private String descripcionRangoFechas;
    private List<String> descripcionCriterios;
    private String tipoVisualizacion;
    private Integer idInstitucion;
    private String descripcionInstitucion;
}