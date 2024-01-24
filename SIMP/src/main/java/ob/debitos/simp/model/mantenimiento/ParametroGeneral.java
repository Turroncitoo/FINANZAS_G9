package ob.debitos.simp.model.mantenimiento;

import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.CodigoBin;
import ob.debitos.simp.validacion.CodigoInstitucion;
import ob.debitos.simp.validacion.IdEmpresa;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametroGeneral
{
	
	private String sFechaProceso;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "EST")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "{NotNull.ParametroGeneral.fechaProceso}")
    private Date fechaProceso;
    
    @CodigoBin(existe = true)
    private String binRuteoSwitch;

    @CodigoInstitucion(existe = true)
    private Integer codigoInstitucion;
    
    @Digits(integer = 6, fraction = 2, message = "{Digits.ParametroGeneral.surchargeSoles}")
    private Double surchargeSoles;
    
    @Digits(integer = 6, fraction = 2, message = "{Digits.ParametroGeneral.surchargeDolares}")
    private Double surchargeDolares;

    @IdEmpresa(existe = true)
    private String idEmpresa;
    
    @NotNull(message = "{NotNull.ParametroGeneral.porcentajeIgv}")
    @Range(min = 0, max = 100, message = "{Range.ParametroGeneral.porcentajeIgv}")
    private Integer porcentajeIgv;
    
    @NotNull(message = "{NotNull.ParametroGeneral.tiempoReprogramacion}")
    @Range(min = 0, max = 99, message = "{Range.ParametroGeneral.tiempoReprogramacion}")
    private Integer tiempoReprogramacion;
    
    @Range(min = 0, max = 1, message = "{Range.ParametroGeneral.activoSimpBatch}")
    private Integer activoSimpBatch;
    
    //@NotNull(message = "{NotNull.ParametroGeneral.rutaArchivoContable}")
    //@NotBlank(message = "{NotBlank.ParametroGeneral.rutaArchivoContable}")
    //@Length(min = 3, max = 200, message = "{Length.ParametroGeneral.rutaArchivoContable}")
    private String repositorioArchivoContable;
    
    @Range(min = 0, max = 1, message = "{Range.ParametroGeneral.procesaObservadasManual}")
    private Integer procesaObservadasManual;
    
    private String descripcionInstitucion;
    
    private String descripcionEmpresa;

    private Byte[] logoReportes;

}