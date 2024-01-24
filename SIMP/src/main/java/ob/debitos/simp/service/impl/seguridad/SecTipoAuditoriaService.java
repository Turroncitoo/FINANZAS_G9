package ob.debitos.simp.service.impl.seguridad;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ISecTipoAuditoriaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.seguridad.SecTipoAuditoria;
import ob.debitos.simp.service.ISecTipoAuditoriaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class SecTipoAuditoriaService extends MantenibleService<SecTipoAuditoria>
        implements ISecTipoAuditoriaService
{
    @SuppressWarnings("unused")
    private ISecTipoAuditoriaMapper secTipoAuditoriaMapper;

    public SecTipoAuditoriaService(
            @Qualifier("ISecTipoAuditoriaMapper") IMantenibleMapper<SecTipoAuditoria> mapper)
    {
        super(mapper);
        this.secTipoAuditoriaMapper = (ISecTipoAuditoriaMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<SecTipoAuditoria> buscarTodos()
    {
        return this.buscar(new SecTipoAuditoria(), Verbo.GETS);
    }
}