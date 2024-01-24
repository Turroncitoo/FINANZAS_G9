package ob.debitos.simp.model.consulta.movimiento;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TxnCompensacionComisiones
{

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaProceso;

    private Integer codigoInstitucion;

    private String secuenciaTransaccion;

    private Integer cuentaCompensacion;

    private String descripcionCuentaCompensacion;

    private Integer idConceptoComision;

    private String descripcionConceptoComision;

    private String registroContable;

    private String descripcionRegistroContable;

    private Double valorComision;

    private Double valorComisionRec;

    private Double valorComisionRec2;

    private String cuentaCargo;

    private String cuentaAbono;

    private String codigoAnalitico;

}