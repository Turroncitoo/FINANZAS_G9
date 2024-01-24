package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ICanalEmisorMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CanalEmisor;
import ob.debitos.simp.service.ICanalEmisorService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CanalEmisorService extends MantenibleService<CanalEmisor>
        implements ICanalEmisorService
{
    @SuppressWarnings("unused")
    private ICanalEmisorMapper canalEmisorMapper;

    public CanalEmisorService(
            @Qualifier("ICanalEmisorMapper") IMantenibleMapper<CanalEmisor> mapper)
    {
        super(mapper);
        this.canalEmisorMapper = (ICanalEmisorMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CanalEmisor> buscarTodos()
    {
        return this.buscar(new CanalEmisor(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CanalEmisor> buscarPorCodigoCanalEmisor(String codigoCanalEmisor)
    {
        CanalEmisor canalEmisor = CanalEmisor.builder().codigoCanalEmisor(codigoCanalEmisor)
                .build();
        return this.buscar(canalEmisor, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCanalEmisor(String codigoCanalEmisor)
    {
        return !this.buscarPorCodigoCanalEmisor(codigoCanalEmisor).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarCanalEmisor(CanalEmisor canalEmisor)
    {
        this.registrar(canalEmisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarCanalEmisor(CanalEmisor canalEmisor)
    {
        this.actualizar(canalEmisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCanalEmisor(CanalEmisor canalEmisor)
    {
        this.eliminar(canalEmisor);
    }
}