package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.seguridad.PoliticaSeguridad;

public interface IPoliticaSeguridadService extends IMantenibleService<PoliticaSeguridad>
{
    public List<PoliticaSeguridad> buscarTodos();
    
    public int buscarLongitudMinimaContrasenia();
    
    public boolean buscarAutenticacionActiveDirectory();
    
    public boolean buscarComplejidadContrasenia();

	public List<PoliticaSeguridad> obtenerPoliticaSeguridad();

	public void actualizarPoliticaSeguridad(PoliticaSeguridad politicaSeguridad);

	public List<PoliticaSeguridad> buscarPorCodigoPoliticaSeguridad(Integer numeroMaximoIntentos);
}
