package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.MotivoReclamo;

public interface IMotivoReclamoService extends IMantenibleService<MotivoReclamo>
{

    public List<MotivoReclamo> buscarTodos();

    public List<MotivoReclamo> buscarPorIdMotivoReclamo(int idMotivo);

    public boolean existeMotivoReclamo(int idMotivo);

    public void registrarMotivoReclamo(MotivoReclamo motivoReclamo);

    public void actualizarMotivoReclamo(MotivoReclamo motivoReclamo);

    public void eliminarMotivoReclamo(MotivoReclamo motivoReclamo);
}
