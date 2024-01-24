package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CanalesSegurosWS;


public interface ICanalesSegurosWSService extends IMantenibleService<CanalesSegurosWS>
{

    public List<CanalesSegurosWS> buscarTodos();
    
    public List<CanalesSegurosWS> buscarPorIdCanal(String idCanal);
    
    public boolean existeCanalSeguro(String canal);

    public void registrarCanalSeguroWS(CanalesSegurosWS canalesSegurosWS);

    public void actualizarCanalSeguroWS(CanalesSegurosWS canalesSegurosWS);

    public void eliminarCanalSeguroWS(CanalesSegurosWS canalesSegurosWS);
}
