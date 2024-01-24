package ob.debitos.simp.model.consulta;

import lombok.Data;

@Data
public class LogAutorizacionNoConciliadaPorFecha
{
    private String fechaTxn;
    private Integer numeroTxnConciliadas;
    private Integer numeroTxnNoConciliadas;
    private Integer numeroDias;
    private String numeroTotal;
    
    
}