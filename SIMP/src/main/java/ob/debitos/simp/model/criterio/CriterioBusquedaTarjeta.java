package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTarjeta
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaInicioProceso;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaFinProceso;

    private Integer idInstitucion;

    private String idEmpresa;

    private String idLogo;

    private List<String> clientes;

    private String idBin;

    private List<String> codigoProducto;

    private List<String> productos;

    private String codigoSeguimiento;

    private String numeroTarjeta;

    private String nombreCompleto;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionRangoFechasProceso;

    private String descripcionCliente;

    private String descripcionBIN;

    private String descripcionSubBIN;

    private String descripcionLogo;

    private String descripcionProducto;

}
