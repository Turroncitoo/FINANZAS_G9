package ob.debitos.simp.model.parametro;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.model.mantenimiento.ParametroGeneral;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametroCarga {
    private String binRuteo;
    private Date fechaProceso;
    private String codigoPrograma;
    private String nombreArchivoUBA;
    private Integer longitudArchivoUBA;
    private String directorioOrigen;
    private String directorioDestino;
    private Integer codigoInstitucion;
    private String rutaCarpetaPorFecha;
    
    private ParametroGeneral parametroGeneral;
    private Date fechaCreacionArchivo;
    private Integer cargaIncremental;
    private String usuario;
}
