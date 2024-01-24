package ob.debitos.simp.model.reporte;

import lombok.Data;

@Data
public class ReporteCompensacionInstitucion
{
    private Integer idInstitucion;
    private String descripcionInstitucion;
    private String idEmpresa;
    private String descEmpresa;
    private String idCliente;
    private String descCliente;
    private Integer cantidadInstitucion;
    private double montoInstitucion;
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