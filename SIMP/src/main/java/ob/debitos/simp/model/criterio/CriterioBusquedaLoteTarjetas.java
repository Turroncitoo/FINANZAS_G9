package ob.debitos.simp.model.criterio;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaLoteTarjetas
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProcesoInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProcesoFin;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaRegistroInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaRegistroFin;

    private String tipoTarjetas;

    private Integer idInstitucion;

    private String idEmpresa;

    private List<String> clientes;
    private String clientesTexto;

    private String idLogo;

    private List<String> productos;
    private String productosTexto;

    private Integer idLote;

    private String descripcionRangoFechasProceso;
    private String descripcionRangoFechasRegistro;

    private String descripcionCliente;

    private String descripcionProducto;

    private String descripcionTipoTarjeta;
    
    private String descripcionEmpresa;
    
    private String descripcionLogo;
    
    private String descripcionInstitucion;
    
    private String tipoDetalle;
    
}
