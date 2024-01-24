package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.controller.excepcion.SftpException;
import ob.debitos.simp.mapper.IParametroSFTPMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.jquery.jstree.JsTreeAttribute;
import ob.debitos.simp.model.jquery.jstree.criterio.JsTreeFilter;
import ob.debitos.simp.model.mantenimiento.ParametrosSFTP;
import ob.debitos.simp.model.respuestas.RespuestaConexionSFTP;
import ob.debitos.simp.service.IParametroSFTPService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.SFtp;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ParametroSFTPService extends MantenibleService<ParametrosSFTP>
	implements IParametroSFTPService {
	
    @SuppressWarnings("unused")
    private IParametroSFTPMapper parametrosSFTPMapper;
    private static final String GETS_MANT = "GETS_MANT";

    public ParametroSFTPService(
            @Qualifier("IParametroSFTPMapper") IMantenibleMapper<ParametrosSFTP> mapper)
    {
        super(mapper);
        this.parametrosSFTPMapper = (IParametroSFTPMapper) mapper;
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
	public List<ParametrosSFTP> buscarTodos(){
        return this.buscar(new ParametrosSFTP(), Verbo.GETS);
	}
    
    @Transactional(propagation = Propagation.REQUIRED)
	public RespuestaConexionSFTP probarConexion(){
    	RespuestaConexionSFTP respuesta = RespuestaConexionSFTP.builder().build();
    	List<ParametrosSFTP> lista = this.buscar(new ParametrosSFTP(), Verbo.GETS);
    	if (lista.isEmpty())
        {
    		respuesta.setExito(0);
    		respuesta.setMensaje("No se encontraron parámetros registrados en el BD para la conexión SFTP");
        }

    	ParametrosSFTP parametro = lista.get(0);
    	String host = parametro.getHost();
    	Integer puerto = parametro.getPuerto();
    	String user = parametro.getUsuario();
    	String contra = parametro.getContrasenia();
    	boolean huboConexion = SFtp.conectar(host, user, contra, puerto);
    	if(!huboConexion){
    		respuesta.setExito(0);
    		respuesta.setMensaje("No hay conexión con el SFTP");
    	}else{
    		respuesta.setExito(1);
    		respuesta.setMensaje("Conexión al SFTP exitosa");
    		SFtp.desconectar();
    	}
    	return respuesta;
	}
    
    @Transactional(propagation = Propagation.REQUIRED)
    public List<JsTreeAttribute> verTreeSFTP(JsTreeFilter filtro){
    	String directorio = filtro.getId().equals("#") ? "/" : filtro.getId(); // JsTree envia # si es la raiz
    	List<ParametrosSFTP> lista = this.buscar(new ParametrosSFTP(), Verbo.GETS);
    	if (lista.isEmpty())
        {
    		throw new SftpException("No existen parámetros registrados para SFTP.");
        }	
    	ParametrosSFTP parametro = lista.get(0);
    	String host = parametro.getHost();
    	Integer puerto = parametro.getPuerto();
    	String user = parametro.getUsuario();
    	String contra = parametro.getContrasenia();
    	boolean huboConexion = SFtp.conectar(host, user, contra, puerto);
    	if(!huboConexion){
    		throw new SftpException("Las credenciales para conexión al SFTP no son correctas.");
    	}
    	List<JsTreeAttribute> json = SFtp.revisarAgregarContenidoSFTP(directorio);
		SFtp.desconectar();
		return json;
	}
    
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ParametrosSFTP> buscarTodosParaMantenimiento(){
    	return this.buscar(new ParametrosSFTP(), GETS_MANT);
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarParametrosSFTP(ParametrosSFTP parametrosSFTP){
    	this.actualizar(parametrosSFTP);
    }

}
