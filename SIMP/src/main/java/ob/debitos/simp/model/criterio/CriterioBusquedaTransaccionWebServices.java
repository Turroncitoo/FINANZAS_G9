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

public class CriterioBusquedaTransaccionWebServices
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaInicio;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaFin;

    private Integer idInstitucion;

    private String idEmpresa;

    private List<String> clientes;

    private String idBin;

    private String logo;
    
    private String descripcionLogoBin;
    
    private List<String> operaciones;

    private String numeroTarjeta;

    private String codigoSeguimiento;

    private String numeroDocumento;

    private List<String> estados;

    private String descripcionRangoFechas;

    private String descripcionInstitucion;

    private String descripcionEmpresa;

    private String descripcionCliente;

    private String descripcionBin;

    private String descripcionOperacion;

    private String descripcionEstado;

}
