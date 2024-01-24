package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReporteTarifario
{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date fechaProceso;
    private Integer idInstitucionEmi;
    private String descripcionInstitucionEmisor;
    private Integer idInstitucionRecep;
    private String descripcionInstitucionReceptor;
    private String idMembresia;
    private String descripcionMembresia;
    private String idClaseServicio;
    private String descripcionClaseServicio;
    private Integer idOrigen;
    private String descripcionOrigen;
    private Integer idMonedaTransaccion;
    private String descripcionMonedaTransaccion;
    private Integer idMonedaCompensacion;
    private String descripcionMonedaCompensacion;
    private Integer transaccion;
    private String descripcionCodigoTransaccion;
    private Integer secuenciaTransaccion;
    private Integer tipoTarifaRecOpe;
    private Integer nivelCompensacion;
    private Integer nivelRecOpe;
    private Integer nivelRecSc;
    private int cantidad;

    private double sumaValorCompensacion;
    private double sumaValorComision;
    private double sumaSurcharge;
    private double sumaSurchargeRec;
    private double sumaValorComisionRec;
    private double sumaValorComisionRec2;

    private double valorCompensacion;
    private double surcharge;
    private double surchargeRec;
    private double ctValorComision;
    private double ciValorComision;
    private double cgValorComision;
    private double coValorComision;
    private double coValorComisionRec;
    private double coValorComisionRec2;
    private String cuadra;
}