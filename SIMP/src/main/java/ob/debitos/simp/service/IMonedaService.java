package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.Moneda;

public interface IMonedaService extends IMantenibleService<Moneda>
{
    
    public List<Moneda> buscarTodos();

    public List<Moneda> buscarPorCodigoMoneda(int codigoMoneda);

    public boolean existeMoneda(int codigoMoneda);
    
}
