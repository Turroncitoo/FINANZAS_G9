package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.proceso.ProcesoAutomatico;

public interface IProcesoAutomaticoService extends IMantenibleService<ProcesoAutomatico>
{
    public List<ProcesoAutomatico> buscarTodos();

    public List<ProcesoAutomatico> buscarPorCodigoGrupo(String codigoGrupo);

    public boolean existeProcesoAutomatico(String codigoGrupo);

    public boolean existeProcesoAutomaticoPorHoraProgramadaRepetido(String codigoGrupo,
            String horaProgramada);

    public boolean existeProcesoAutomaticoPorOrdenEjecucionRepetido(String codigoGrupo,
            int ordenEjecucion);

    public void registrarProcesoAutomatico(ProcesoAutomatico procesoAutomatico);

    public void actualizarProcesoAutomatico(ProcesoAutomatico procesoAutomatico);

    public void eliminarProcesoAutomatico(ProcesoAutomatico procesoAutomatico);
}