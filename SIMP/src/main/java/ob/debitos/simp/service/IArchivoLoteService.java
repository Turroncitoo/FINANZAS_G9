package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.parametro.ParametroLote;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.model.proceso.ArchivoAfiliacionDeUBA;
import ob.debitos.simp.model.proceso.ArchivoLote;
import ob.debitos.simp.model.proceso.TmpRequerimiento;

public interface IArchivoLoteService
{

    public void registrarLote(ArchivoLote archivoLote);

    public void registrarControlLote(Integer idLote);

    public void insertarCSVAfiliacionBatch(List<TmpRequerimiento> tmpRequerimientos, Integer idLote);

    public void descargarArchivoAfiliaciones(String ruta, Integer idInstitucion);

    public void insertarRespuestaAfiliacionUBABatch(List<ArchivoAfiliacionDeUBA> afiliacionesDeUBA, Integer idInstitucion);

    // public int generarArchivoAfiliaciones(String ruta, Integer idInstitucion);

    public int generarArchivoPrepago(String ruta, Integer idInstitucion, String codigoArchivo);

    public int generarArchivoActivaciones(String ruta, Integer idInstitucion);

    public void eliminarTmpAfiliacionesDeUba();

    public List<LotePP> actualizarLote(ParametroLote parametro);

}
