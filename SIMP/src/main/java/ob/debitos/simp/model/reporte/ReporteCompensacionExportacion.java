package ob.debitos.simp.model.reporte;

import lombok.Data;

@Data
public class ReporteCompensacionExportacion
{
    private Integer idAtm;
    private String descripcionAtm;
    private String idMembresia;
    private String descripcionMembresia;
    private Integer cantidad;
    private Double monto;
    private Double comisionInt;
    private Double comisionGas;
    private Double comisionOpe;
    private Double comisionSur;
    private Double totalMembresia;     
    private Double totalGeneral;
}