package ob.debitos.simp.model.tarjetas;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoteRecargaDebito
{

    private String codigoSeguimiento;
    private Integer tipoDocumento;
    private String descTipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String operacion;
    private String descOperacion;
    private Integer moneda;
    private String descMoneda;
    private Double monto;
    private Integer exitoRegistro;
    public String comentario;

    public Integer existe;
    public String usandoTabla;

}
