package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.ModoEntradaPos;

public interface IModoEntradaPosService extends IMantenibleService<ModoEntradaPos>
{

    public List<ModoEntradaPos> buscarTodos();

    public List<ModoEntradaPos> buscarPorCodigoModoEntradaPos(String codigoModoEntradaPOS);

    public boolean existeModoEntradaPos(String codigoModoEntradaPOS);

    public void registrarModoEntradaPos(ModoEntradaPos modoEntradaPos);

    public void actualizarModoEntradaPos(ModoEntradaPos modoEntradaPos);

    public void eliminarModoEntradaPos(ModoEntradaPos modoEntradaPos);
}
