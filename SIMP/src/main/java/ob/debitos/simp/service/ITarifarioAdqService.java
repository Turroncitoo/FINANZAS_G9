package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.tarifario.TarifarioAdq;

public interface ITarifarioAdqService extends IMantenibleService<TarifarioAdq>
{
    public List<TarifarioAdq> buscarTodos();

    public List<TarifarioAdq> buscarPorCodigo(TarifarioAdq adquirente);

    public List<TarifarioAdq> registrarTarAdq(TarifarioAdq adquirente);

    public List<TarifarioAdq> actualizarTarAdq(TarifarioAdq adquirente);

    public void eliminarTarAdq(TarifarioAdq adquirente);
}