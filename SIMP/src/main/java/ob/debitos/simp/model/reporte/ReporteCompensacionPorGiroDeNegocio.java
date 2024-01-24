package ob.debitos.simp.model.reporte;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * Representa el reporte resumen de transacciones de compensación, informado en
 * el archivo Log Contable, por giro de negocio.
 * 
 * @author Hanz Llanto
 */
@Data
public class ReporteCompensacionPorGiroDeNegocio
{
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "EST")
    private Date fechaProceso;
	private Integer idInstitucion;
	private String descripcionInstitucion;
    private String monedaCompensacion;
    private String descMonedaCompensacion;
    private String giroNegocio;
    private String descGiroNegocio;
    private String idMembresia;
    private String descMembresia;
    private String idClaseServicio;
    private String descClaseServicio;
    private String idOrigen;
    private String descOrigen;
    private String idBin;
    private String idLogo;
    private String descLogo;
    private String descLogoBin;
    private String codigoProducto;
    private String descProducto;
    private Integer cantidad;
    private double monto;
    private double comision;
    private String idEmpresa;
    private String descEmpresa;
    private String idCliente;
    private String descCliente;
    
}