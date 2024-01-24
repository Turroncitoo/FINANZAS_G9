package ob.debitos.simp.model.reporte;

import lombok.Data;
import ob.debitos.simp.model.paginacion.ItemPagina;

@Data
public class ReporteResumenCuadreBancoAdministrador extends ItemPagina
{
    
    private String codigoMembresia;
    private String descripcionMembresia;
    private String codigoClaseServicio;
    private String descripcionClaseServicio;
    private int codigoOrigen;
    private String descripcionOrigen;
	private Integer claseTransaccion;
    private String descripcionClaseTransaccion;
    private Integer codigoTransaccion;
    private String descripcionCodigoTransaccion;
    private int cantidad;
    private double monto;
    private double comisionTHB;
    private double comisionEST;
    private double comisionREC;
    private double comisionOPE;
    private double comisionISA;
    private double comisionOIF;
    private double comisionGAS;
    private double comisionDIS;
    private double comisionING;
    private double comisionCOI;
    private double comisionTIC;
    private double comisionINT;
    private double total;
   
}