package ob.debitos.simp.model.seguridad;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.mantenimiento.MultiTabDet;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaRecurso
{
    private int idCategoria;
    
    @NotNull(message = "{NotNull.Categoria.categoria}")
	@NotBlank(message = "{NotBlank.Categoria.categoria}")
    @Length(min = 3, max = 20, message = "{Length.Categoria.categoria}")
    private String categoria;
    
    @NotNull(message = "{NotNull.Categoria.accionCategoria}")
	@NotBlank(message = "{NotBlank.Categoria.accionCategoria}")
    @Length(min = 1, max = 20, message = "{Length.Categoria.accionCategoria}")
    private String accionCategoria;
    
    private List<SecRecurso> recursos;
    private List<MultiTabDet> acciones;
}