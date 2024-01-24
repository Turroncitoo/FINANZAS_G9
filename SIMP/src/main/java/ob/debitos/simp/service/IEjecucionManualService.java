package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.ResultadoProceso;

public interface IEjecucionManualService
{

    public void registrarParaControlProceso(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoBatch(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoDescarga(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoBackup(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoEnvio(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoCargaArchivoUBA(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoGeneracionArchivosContables(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoDescargaYcargaPrepago(ControlEjecucionPrograma controlEjecucionPrograma);

    public List<ResultadoProceso> ejecutarProcedimientoGeneracionArchivoPrepago(ControlEjecucionPrograma controlEjecucionPrograma);

}