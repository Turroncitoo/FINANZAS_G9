package ob.debitos.simp.model.mantenimiento;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ob.debitos.simp.validacion.AsociacionSubBinCliente;
import ob.debitos.simp.validacion.CodigoBin;
import ob.debitos.simp.validacion.CodigoSubBin;
import ob.debitos.simp.validacion.IdEmpresa;
import ob.debitos.simp.validacion.grupo.IBasico;
import ob.debitos.simp.validacion.grupo.ICampo;
import ob.debitos.simp.validacion.grupo.accion.IActualizacion;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionBasico;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionBasicoActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionClase;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IAsociacionClaseActualizacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.IExisteAsociacion;
import ob.debitos.simp.validacion.grupo.secuencia.asociacionSubBinCliente.INoExisteAsociacion;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@CodigoSubBin(existe = true, groups = { IActualizacion.class, IAsociacionClase.class,
        IAsociacionClaseActualizacion.class })
@CodigoSubBin(existe = false, groups = IRegistro.class)
@CodigoSubBin(existe = true, campoCodigoSubBin = "codigoSubBINAntiguo", campoCodigoBin = "codigoBINAntiguo", groups = IAsociacionClaseActualizacion.class)
@AsociacionSubBinCliente(existe = false, groups = { INoExisteAsociacion.class,
        IAsociacionActualizacion.class })
@AsociacionSubBinCliente(existe = true, groups = IExisteAsociacion.class)
@AsociacionSubBinCliente(existe = true, campoCodigoSubBin = "codigoSubBINAntiguo", campoCodigoBin = "codigoBINAntiguo", groups = IAsociacionActualizacion.class)
public class SubBin
{
    private String codigoSubBIN;

    @CodigoBin(existe = true, groups = { ICampo.class, IAsociacionBasico.class })
    private String codigoBIN;

    @NotNull(message = "{NotNull.SubBin.descripcion}", groups = IBasico.class)
    @NotBlank(message = "{NotBlank.SubBin.descripcion}", groups = IBasico.class)
    @Length(min = 3, max = 50, message = "{Length.SubBin.descripcion}", groups = IBasico.class)
    private String descripcion;

    @NotNull(message = "{NotNull.Cliente.idCliente}", groups = IAsociacionBasico.class)
    @NotBlank(message = "{NotBlank.Cliente.idCliente}", groups = IAsociacionBasico.class)
    @Length(min = 1, max = 4, message = "{Length.Cliente.idCliente}", groups = IAsociacionBasico.class)
    private String idCliente;

    @IdEmpresa(existe = true, groups = IAsociacionBasico.class)
    private String idEmpresa;
    
    @Range(min = 0, max = 99, message = "{Range.SubBin.categoria}", groups = IBasico.class)
    private Integer categoria;

    private String codigoMembresia;
    private String descripcionCortaInstitucion;
    private String descripcionMembresia;
    private String descripcionBIN;

    @CodigoBin(existe = true, groups = IAsociacionBasicoActualizacion.class)
    private String codigoBINAntiguo;
    private String codigoSubBINAntiguo;

    
    private String codigoCliente; //Atributo de SIMP_PRE
    
    private String idRegimen;
    private String descripcionRegimen;
}

