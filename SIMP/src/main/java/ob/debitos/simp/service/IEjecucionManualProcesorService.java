package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaArchivoContablePrepago;
import ob.debitos.simp.model.proceso.ControlEjecucionPrograma;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.model.proceso.Programa;

public interface IEjecucionManualProcesorService
{

    public int ejecutarProcedimientoDescargaPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);

    public int ejecutarProcedimientoEnvioPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);

    public List<LogControlProgramaDetalle> ejecutarProcedimientoCargaArchivoUBAPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);

    /**
     * Este metodo tiene incluida la anotacion @LogControlPrograma Llama al
     * metodo ejecutarProcedimientoBatch, y registra en el log de control la
     * ejecución
     * 
     * @param controlEjecucionPrograma
     * @return
     */
    public int ejecutarProcedimientoBatchPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma);

    public int ejecutarProcedimientoBatch(ControlEjecucionPrograma controlEjecucionPrograma);

    public int ejecutarDescargaYcargaArchivoPrepagoPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);

    public int ejecutarGeneracionArchivoPrepagoPorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, Programa programa);

    public int ejecutarGeneracionArchivoContablePorInstitucion(ControlEjecucionPrograma controlEjecucionPrograma, CriterioBusquedaArchivoContablePrepago criterioBusqueda);

}
