package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.IdConceptoComision;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConceptoComision
{
    @IdConceptoComision(min = 1, max = 999, existe = true, groups = IActualizacion.class, incluirCero = false)
    @IdConceptoComision(min = 1, max = 999, existe = false, message = "{Existe.ConceptoComision.idConceptoComision}", groups = IRegistro.class, incluirCero = false)
    private Integer idConceptoComision;
    @NotNull(message = "{NotNull.ConceptoComision.descripcion}")
    @NotBlank(message = "{NotBlank.ConceptoComision.descripcion}")
    @Length(min = 3, max = 30, message = "{Length.ConceptoComision.descripcion}")
    private String descripcion;
    @NotNull(message = "{NotNull.ConceptoComision.abreviatura}")
    @NotBlank(message = "{NotBlank.ConceptoComision.abreviatura}")
    @Length(max = 3, message = "{Length.ConceptoComision.abreviatura}")
    private String abreviatura;
    private String aplicaComision;
    private Integer tipoComision;
    @NotNull(message = "{NotNull.ConceptoComision.rolEmisor}")
    @Range(min = 0, max = 1, message = "{Range.ConceptoComision.rolEmisor}")
    private Integer rolEmisor;
    @NotNull(message = "{NotNull.ConceptoComision.rolReceptor}")
    @Range(min = 0, max = 1, message = "{Range.ConceptoComision.rolReceptor}")
    private Integer rolReceptor;
    @NotNull(message = "{NotNull.ConceptoComision.activado}")
    @Range(min = 0, max = 1, message = "{Range.ConceptoComision.activado}")
    private Integer activado;
}