package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.seguridad.SecTipoAuditoria;

public interface ISecTipoAuditoriaService extends IMantenibleService<SecTipoAuditoria>
{
    public List<SecTipoAuditoria> buscarTodos();
}
