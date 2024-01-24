package ob.debitos.simp.service;

import java.util.Date;
import java.util.List;

import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.LogControlProgramaResumen;

public interface ILogControlProgramaResumenService extends IMantenibleService<LogControlProgramaResumen>
{

    public List<LogControlProgramaResumen> buscarPorFechaProceso(Date fechaProceso);

    public boolean existeFechaProcesoCargada(Date fechaProceso);

    public void actualizarLogControlProgramaResumen(ControlEjecucionPrograma controlEjecucionPrograma);

    public void actualizarEstado(ControlEjecucionPrograma controlEjecucionPrograma, int estado);

    public void existeProgramaAnteriorNoEjecutadoPorGrupo(String codigoGrupo, String codigoPrograma);

}