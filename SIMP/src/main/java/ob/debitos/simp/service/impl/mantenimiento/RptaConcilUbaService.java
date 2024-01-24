package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IRptaConcilUbaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.RespuestaConcilUba;
import ob.debitos.simp.service.IRptaConcilUbaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class RptaConcilUbaService extends MantenibleService<RespuestaConcilUba>
        implements IRptaConcilUbaService
{
    @SuppressWarnings("unused")
    private IRptaConcilUbaMapper rptaConcilUbaMapper;

    public RptaConcilUbaService(
            @Qualifier("IRptaConcilUbaMapper") IMantenibleMapper<RespuestaConcilUba> mapper)
    {
        super(mapper);
        this.rptaConcilUbaMapper = (IRptaConcilUbaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<RespuestaConcilUba> buscarTodos()
    {
        return this.buscar(new RespuestaConcilUba(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<RespuestaConcilUba> buscarPorIdRptaConcilUba(String idRespuestaConcilUba)
    {
        RespuestaConcilUba rptaConcilUba = RespuestaConcilUba.builder()
                .idRespuestaConcilUba(idRespuestaConcilUba).build();
        return this.buscar(rptaConcilUba, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeRptaConcilUba(String idRespuestaConcilUba)
    {
        return !this.buscarPorIdRptaConcilUba(idRespuestaConcilUba).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarRptaConcilUba(RespuestaConcilUba rptaConcilUba)
    {
        this.registrar(rptaConcilUba);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarRptaConcilUba(RespuestaConcilUba rptaConcilUba)
    {
        this.actualizar(rptaConcilUba);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarRptaConcilUba(RespuestaConcilUba rptaConcilUba)
    {
        this.eliminar(rptaConcilUba);
    }
}