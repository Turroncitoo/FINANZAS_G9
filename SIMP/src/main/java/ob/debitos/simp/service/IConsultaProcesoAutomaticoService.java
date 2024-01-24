package ob.debitos.simp.service;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.model.proceso.ConsultaProcesoAutomatico;

public interface IConsultaProcesoAutomaticoService
{
    public List<ConsultaProcesoAutomatico> buscarProcesosAutomaticos();

    public List<ConsultaProcesoAutomatico> buscarPorFechaProceso(Date fechaProceso);
}