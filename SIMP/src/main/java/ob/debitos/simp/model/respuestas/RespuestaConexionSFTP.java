package ob.debitos.simp.model.respuestas;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.jquery.jstree.JsTreeObject;
import ob.debitos.simp.model.sftp.LsArchivosSFTP;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaConexionSFTP {
	String mensaje;
	Integer exito;
	List<LsArchivosSFTP> archivos;
	JsTreeObject jsTree;
}
