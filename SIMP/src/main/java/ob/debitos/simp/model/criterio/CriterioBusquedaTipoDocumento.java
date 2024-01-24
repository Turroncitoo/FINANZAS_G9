package ob.debitos.simp.model.criterio;

import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.MultiTablaUtil;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.MultitabDet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CriterioBusquedaTipoDocumento
{

    @Pattern(regexp = Regex.SOLO_DIGITOS, message = "{Pattern.CriterioBusquedaTipoDocumento.tipoDocumento}")
    @MultitabDet(idTabla = MultiTablaUtil.TABLA_TIPO_DOCUMENTO, existe = true, campoIdItem = "tipoDocumento", message = "{NoExiste.CriterioBusquedaTipoDocumento.tipoDocumento}")
    private String tipoDocumento;

    @Length(min = 1, max = 12, message = "{Length.CriterioBusquedaTipoDocumento.numeroDocumento}")
    private String numeroDocumento;

    private List<String> tiposDocumento;

    private List<String> numerosDocumento;

    private String xmlString;

    private String descripcionTipoDocumento;

    private Integer cuentaCompensacion;

    private Integer idInstitucion;

    private Integer codigoInstitucion;

    private String descripcionInstitucion;

    private String modo;

}
