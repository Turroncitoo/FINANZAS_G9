package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Logo;

public interface ILogoService extends IMantenibleService<Logo>
{

    public List<Logo> buscarTodos();

    public List<Logo> buscarTodosInstitucion();

    public List<Logo> buscarPorIdLogo(String idLogo);

    public List<Logo> buscarPorCodigoInstitucion(Integer codigoInstitucion);

    public List<Logo> buscarPorCodigoMembresiaCodigoClaseServicio(String idMembresia, String idClaseServicio, Integer idInstitucion);

    public boolean existeLogo(String idLogo);

    public void registrarLogo(Logo logo);

    public void actualizarLogo(Logo logo);

    public void eliminarLogo(Logo logo);

    public List<Logo> buscarPorCodigoBIN(String codigoBIN);

    public boolean existeBin(String codigoBIN);

}
