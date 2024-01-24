package ob.debitos.simp.model.ajuste;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.utilitario.MultiTablaUtil;
import ob.debitos.simp.utilitario.Regex;
import ob.debitos.simp.validacion.MultitabDet;
import ob.debitos.simp.validacion.grupo.IDevolucion;
import ob.debitos.simp.validacion.grupo.IExtorno;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizacionExtornoDevolucion
{
    private String verbo;

    @MultitabDet(min = 1, max = 1, existe = true, campoIdItem = "idIndicadorDevolucion", idTabla = MultiTablaUtil.TABLA_INDICADOR_DEVOLUCION, groups = IDevolucion.class)
    private Integer idIndicadorDevolucion;

    @MultitabDet(min = 1, max = 1, existe = true, campoIdItem = "idIndicadorExtorno", idTabla = MultiTablaUtil.TABLA_INDICADOR_EXTORNO, groups = IExtorno.class)
    private Integer idIndicadorExtorno;

    @NotNull(message = "{NotNull.ActualizacionExtornoDevolucion.comentario}")
    @NotBlank(message = "{NotBlank.ActualizacionExtornoDevolucion.comentario}")
    @Length(min = 3, max = 250, message = "{Length.ActualizacionExtornoDevolucion.comentario}")
    private String comentario;
    
    @NotNull(message = "{NotNull.ActualizacionExtornoDevolucion.fechaActualizacion}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaActualizacion;

    private String numeroTarjeta;

    @NotNull(message = "{NotNull.ActualizacionExtornoDevolucion.numeroTrace}")
    @Length(min = 6, max = 15, message = "{Length.ActualizacionExtornoDevolucion.numeroTrace}")
    private String numeroTrace;

    @NotNull(message = "{NotNull.ActualizacionExtornoDevolucion.horaTransaccion}")
    @Pattern(regexp = Regex.FORMATO_HHMMSS, message = "{Pattern.ActualizacionExtornoDevolucion.horaTransaccion}")
    private String horaTransaccion;

    @NotNull(message = "{NotNull.ActualizacionExtornoDevolucion.horaActualizacion}")
    @Pattern(regexp = Regex.FORMATO_HHMMSS, message = "{Pattern.ActualizacionExtornoDevolucion.horaActualizacion}")
    private String horaActualizacion;

    @NotNull(message = "{NotNull.ActualizacionExtornoDevolucion.fechaTransaccion}")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fechaTransaccion;
    
    private Date fechaRegistro;
    private Integer idInstitucion;
    private String idSecuencia;
}