package ob.debitos.simp.service.impl.proceso;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IProcesoAutomaticoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.proceso.ProcesoAutomatico;
import ob.debitos.simp.service.IProcesoAutomaticoService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ProcesoAutomaticoService extends MantenibleService<ProcesoAutomatico>
        implements IProcesoAutomaticoService
{
    @SuppressWarnings("unused")
    private IProcesoAutomaticoMapper procesoAutomaticoMapper;

    private static final String GET_HORA = "GET_HORA";
    private static final String GET_ORDEN = "GET_ORDEN";

    public ProcesoAutomaticoService(
            @Qualifier("IProcesoAutomaticoMapper") IMantenibleMapper<ProcesoAutomatico> mapper)
    {
        super(mapper);
        this.procesoAutomaticoMapper = (IProcesoAutomaticoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ProcesoAutomatico> buscarTodos()
    {
        return this.buscar(new ProcesoAutomatico(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProcesoAutomatico> buscarPorCodigoGrupo(String codigoGrupo)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder().codigoGrupo(codigoGrupo)
                .build();
        return this.buscar(procesoAutomatico, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProcesoAutomatico> buscarPorHoraProgramada(String horaProgramada)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder()
                .horaProgramada(horaProgramada).build();
        return this.buscar(procesoAutomatico, GET_HORA);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProcesoAutomatico> buscarPorOrdenEjecucion(int ordenEjecucion)
    {
        ProcesoAutomatico procesoAutomatico = ProcesoAutomatico.builder()
                .ordenEjecucion(ordenEjecucion).build();
        return this.buscar(procesoAutomatico, GET_ORDEN);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeProcesoAutomatico(String codigoGrupo)
    {
        return !this.buscarPorCodigoGrupo(codigoGrupo).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeProcesoAutomaticoPorHoraProgramadaRepetido(String codigoGrupo,
            String horaProgramada)
    {
        boolean existeProcesoAutomaticoPorHoraProgramadaRepetido = false;
        List<ProcesoAutomatico> procesosAutomaticos = this.buscarPorHoraProgramada(horaProgramada);
        boolean existeProcesoAutomaticoPorHoraProgramada = !procesosAutomaticos.isEmpty();
        if (existeProcesoAutomaticoPorHoraProgramada)
        {
            String codigoGrupoBusqueda = procesosAutomaticos.get(0).getCodigoGrupo();
            existeProcesoAutomaticoPorHoraProgramadaRepetido = !codigoGrupoBusqueda
                    .equals(codigoGrupo);
        }
        return existeProcesoAutomaticoPorHoraProgramadaRepetido;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeProcesoAutomaticoPorOrdenEjecucionRepetido(String codigoGrupo,
            int ordenEjecucion)
    {
        boolean existeProcesoAutomaticoPorOrdenEjecucionRepetido = false;
        List<ProcesoAutomatico> procesosAutomaticos = this.buscarPorOrdenEjecucion(ordenEjecucion);
        boolean existeProcesoAutomaticoPorOrdenEjecucion = !procesosAutomaticos.isEmpty();
        if (existeProcesoAutomaticoPorOrdenEjecucion)
        {
            String codigoGrupoBusqueda = procesosAutomaticos.get(0).getCodigoGrupo();
            existeProcesoAutomaticoPorOrdenEjecucionRepetido = !codigoGrupoBusqueda
                    .equals(codigoGrupo);
        }
        return existeProcesoAutomaticoPorOrdenEjecucionRepetido;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarProcesoAutomatico(ProcesoAutomatico procesoAutomatico)
    {
        this.registrar(procesoAutomatico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarProcesoAutomatico(ProcesoAutomatico procesoAutomatico)
    {
        this.actualizar(procesoAutomatico);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarProcesoAutomatico(ProcesoAutomatico procesoAutomatico)
    {
        this.eliminar(procesoAutomatico);
    }
}