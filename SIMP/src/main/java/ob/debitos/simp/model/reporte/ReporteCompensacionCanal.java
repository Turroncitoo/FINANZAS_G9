package ob.debitos.simp.model.reporte;

import lombok.Data;

@Data
public class ReporteCompensacionCanal
{
    private Integer idInstitucion;
	private String descripcionInstitucion;
    private Integer idCanal;
    private String descripcionCanal;
    private String idEmpresa;
    private String descEmpresa;
    private String idCliente;
    private String descCliente;
    private Integer cantidadCanal;
    private double montoCanal;
    private double comisionTHB;
    private double comisionEST;
    private double comisionREC;
    private double comisionOPE;
    private double comisionISA;
    private double comisionOIF;
    private double comisionING;
    private double comisionCOI;
    private double comisionTIC;
    private double comisionINT;
    private double comisionTOTAL;
}