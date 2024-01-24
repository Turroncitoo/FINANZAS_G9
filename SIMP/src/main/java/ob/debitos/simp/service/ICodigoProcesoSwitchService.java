package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CodigoProcSwitch;

public interface ICodigoProcesoSwitchService extends IMantenibleService<CodigoProcSwitch>
{

    public List<CodigoProcSwitch> buscarTodos();

    public List<CodigoProcSwitch> buscarPorCodigoProcesoSwitch(String codigoProcesoSwitch);

    public boolean existeCodigoProcesoSwitch(String codigoProcesoSwitch);

    public void registrarCodigoProcesoSwitch(CodigoProcSwitch codigoProcesoSwitch);

    public void actualizarCodigoProcesoSwitch(CodigoProcSwitch codigoProcesoSwitch);

    public void eliminarCodigoProcesoSwitch(CodigoProcSwitch codigoProcesoSwitch);
}
