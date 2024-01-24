package ob.debitos.simp.model.seguridad;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Auditoria
{
	private Integer idAuditoria;
    private String idRecurso;
    private String descripcionRecurso;
    private String idAccion;
    private String descripcionAccion;
    private String direccionIp;
    private Integer exito;
    private String comentario;
    private String logError;
    private String nombreUsuario;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fecha;
    private String hora;
}