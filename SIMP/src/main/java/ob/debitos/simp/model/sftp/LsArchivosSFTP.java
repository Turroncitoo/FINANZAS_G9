package ob.debitos.simp.model.sftp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LsArchivosSFTP {
	String nombre;
	String extension;
	String permisos;
	String peso;
}
