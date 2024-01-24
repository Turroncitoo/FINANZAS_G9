package ob.debitos.simp.model.seguridad;

import lombok.Data;

@Data
public class SecTipoAuditoria
{
    private Integer idTipoAuditoria;
    private String descripcion;
    private String codigoAuditoria;
}