package ob.debitos.simp.model.prepago.criterio;

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
@AllArgsConstructor
@NoArgsConstructor
public class CriterioBusquedaTipoDocumentoPrepago
{

    @Pattern(regexp = Regex.SOLO_DIGITOS, message = "{Pattern.CriterioBusquedaTipoDocumento.tipoDocumento}")
    @MultitabDet(idTabla = MultiTablaUtil.TABLA_TIPO_DOCUMENTO, existe = true, campoIdItem = "tipoDocumento", message = "{NoExiste.CriterioBusquedaTipoDocumento.tipoDocumento}")
    private String tipoDocumento;

    @Length(min = 1, max = 20, message = "{Length.CriterioBusquedaTipoDocumento.numeroDocumento}")
    private String numeroDocumento;

    private String codigoSeguimiento;

    private String descripcionTipoDocumento;

    private String tipoBusqueda;

}
