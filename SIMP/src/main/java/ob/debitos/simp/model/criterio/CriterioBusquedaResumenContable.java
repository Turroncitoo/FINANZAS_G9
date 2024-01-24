package ob.debitos.simp.model.criterio;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Representa el criterio de búsqueda del reporte de balance contable.
 * 
 * @author Carla Ulloa
 */
@Data
public class CriterioBusquedaResumenContable
{
	private String tipoReporte;
	
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaFin;
    private String descripcionRangoFechas;
    
    private Integer idInstitucion;
    private String descripcionInstitucion;
}