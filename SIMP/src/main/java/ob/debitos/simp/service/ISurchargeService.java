package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.Surcharge;

public interface ISurchargeService extends IMantenibleService<Surcharge>
{
    public List<Surcharge> buscarTodos();

    public List<Surcharge> buscarPorCodigo(Surcharge surcharge);

    public List<Surcharge> registrarSurcharge(Surcharge surcharge);

    public List<Surcharge> actualizarSurcharge(Surcharge surcharge);

    public void eliminarSurcharge(Surcharge surcharge);
}