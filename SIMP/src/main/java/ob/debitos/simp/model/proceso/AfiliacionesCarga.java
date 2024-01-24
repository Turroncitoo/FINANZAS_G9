package ob.debitos.simp.model.proceso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AfiliacionesCarga
{

    private String tipoDocumento;
    private String descTipoDocumento;
    private String numeroDocumento;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreEmbossing;
    private String ruc;
    private String nombreCliente;
    private String fechaVencimiento;
    private String direccion;
    private String telefonoMovil;
    private String sexo;
    private String descSexo;
    private String fechaNacimiento;
    private String indicadorDistribucion;
    private String descDistribucion;
    private String nacionalidad;
    private String descNacionalidad;
    private String nombreManda1;
    private String tipoManda1;
    private String descTipoManda1;
    private String numManda1;
    private String fonoMnada1;
    private String nombreManda2;
    private String tipoManda2;
    private String descTipoManda2;
    private String numManda2;
    private String fonoMnada2;

    private String operacion;
    private Integer exitoRegistro;
    private String mensajeExcepcion;

}
