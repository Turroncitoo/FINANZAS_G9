package ob.debitos.simp.model.reporte;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import ob.debitos.simp.aspecto.anotacion.NoIdentificable;

@Data
public class ReporteContabilizacion
{
    private Integer codigoInstitucion;
    private String idCliente;
    private String descripcionCliente;
    private String idEmpresa;
    private String descripcionEmpresa;
    private Integer codigoMonedaCompensacion;
    private String descripcionMonedaCompensacion;
    private String codigoMembresia;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private String descripcionClaseServicio;
    private String idBin;
    private String idLogo;
    private String descLogoBin;
    private String descripcionLogo;
    private String codigoProducto;
    private String descProducto;
    private Integer codigoOrigen;
    private String descripcionOrigen;
    private Integer codigoClaseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer codigoTransaccion;
    private String descripcionCodigoTransaccion;
    private List<ReporteComision> comisiones;
    private String cuentaCargo;
    private String cuentaAbono;
    private String codigoAnalitico;
    private Integer indicadorContabilizacion;
    private String descripcionIndicadorContabilizacion;
    private Integer cantidad;
    private Double monto;

    // Campos adicionales ReporteContabilizacionEnCuentaDefault
    private String tipoTransaccion;
    private String descripcionTransaccion;
    private String secuenciaTransaccion;
    @NoIdentificable(campo = "numeroTarjeta")
    private String numeroTarjeta;
    private String numeroCuenta;
    private String idRolTransaccion;
    private String descripcionRolTransaccion;
    private Integer codigoATM;
    private String descripcionATM;
    private String idTipoDocumento;
    private String descripcionTipoDocumento;
    private String numeroDocumento;
    private String apellidos;
    private String nombres;
    private Integer fondoCargo;
    private String descFondoCargo;
    private Integer fondoAbono;
    private String descFondoAbono;
    private String descripcionInstitucion;
    private Integer codigoMoneda;
    private String descripcionMoneda;
    private String idConceptoComision;
    private String descripcionConceptoComision;
    private String registroContable;
    private String descripcionRegistroContable;
    private Double montoOComis;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;

    // Para exportacion
    private Double comision1;
    private Double comision2;
    private Double comision3;
    private Double comision4;
    private Double comision5;
    private Double comision6;
    private Double comision7;
    private Double comision8;
    private Double comision9;
    private Double comision10;
    private Double comision11;
    private Double comision12;
    private Double comision13;
    private Double comision14;
    private Double totalComisiones;
}