package ob.debitos.simp.model.tarjetas;

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
public class Lote
{

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
    private Integer idInstitucion;
    private String descInstitucion;
    private String idEmpresa;
    private String descEmpresa;
    private String idCliente;
    private String descCliente;
    private String idLogo;
    private String descLogo;
    private String descLogoBin;
    private String idBin;
    private String codigoProducto;
    private String descProducto;
    private String idAfinidad;
    private String descAfinidad;
    private String idCategoria;
    private String descCategoria;
    private Integer idLote;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRegistro;
    private String fechaRegistroTexto;
    private String horaRegistro;
    private String tipoLote;
    private String descTipoLote;
    private Integer indActivado;
    private Integer indRecargado;
    private String tipoAfiliacion;
    private String desctipoAfiliacion;
    private String tipoTarjetas;
    private String descTipoTarjetas;
    private String estadoLote;
    private String descEstadoLote;
    private Integer registros;
    private Integer enviadoUBA;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaEmisionUBA;
    private Integer recibioRespuesta;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaRespuesta;
    private Integer idLotePadre;
    private String idLoteUBA;

    private String modo;
    private String usuario;

    // Solo se usa en lotes de nominadas, para innominadas es null
    List<LoteDetalle> detalleNominadas;

}
