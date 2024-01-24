package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Bin;

public interface IBinService extends IMantenibleService<Bin>
{
    public List<Bin> buscarTodos();
    
    public List<Bin> buscarTodosBinOcho();

    public List<Bin> buscarPorCodigoBIN(String codigoBIN);
    
    public List<Bin> buscarTodosConSubBIN();

    public List<Bin> buscarPorIdInstitucion(Integer idInstitucion);
    
    public List<Bin> buscarPorCodigoMembresiaCodigoClaseServicio(String codigoMembresia,
            String codigoClaseServicio, Integer idInstitucion);

    public List<Bin> buscarPorMembresia(String idMembresia);

    public boolean existeBin(String codigoBIN);

    public void registrarBin(Bin bin);

    public void actualizarBin(Bin bin);

    public void eliminarBin(Bin bin);
}