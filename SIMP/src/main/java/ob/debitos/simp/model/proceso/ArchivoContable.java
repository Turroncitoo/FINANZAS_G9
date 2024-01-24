package ob.debitos.simp.model.proceso;

import java.util.Date;

import lombok.Data;

@Data
public class ArchivoContable
{    
    private Date fechaProceso;
    private Date fechaAfectacion; 
    private Integer moneda;
    private String codigoMembresia;
    private String claseServicio;
    private Integer origen;
    private Integer claseTransaccasion;
    private Integer codigoTransaccion;
    private String  tipoTransaccion;
    private String tipoComision;
    private String cuentaCargo;
    private String cuentaAbono;
    private String codigoAnalitico;
    private Double montoContable;
}