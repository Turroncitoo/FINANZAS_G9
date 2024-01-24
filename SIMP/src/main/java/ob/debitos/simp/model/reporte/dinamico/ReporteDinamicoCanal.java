package ob.debitos.simp.model.reporte.dinamico;

import java.util.Date;

import lombok.Data;

@Data
public class ReporteDinamicoCanal
{
    private Integer id_canal;
    private Date fecha_txn;
    private Integer codigo_respuesta_txn;
    private Integer cantidad_txn;
}