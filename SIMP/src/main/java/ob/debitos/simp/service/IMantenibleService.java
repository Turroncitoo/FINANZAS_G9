package ob.debitos.simp.service;

import java.util.List;

public interface IMantenibleService<T>
{
    public void registrar(T dto);
    
    public List<T> registrarAutoIncrementable(T dto);

    public void actualizar(T dto);

    public void eliminar(T dto);

    public List<T> buscar(T dto, String verbo);
    
    public boolean existe(T dto);

    public void mantener(T dto, String verbo);
}