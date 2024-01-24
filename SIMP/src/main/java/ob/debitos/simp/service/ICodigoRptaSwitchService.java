package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CodigoRespuestaSwitch;

public interface ICodigoRptaSwitchService extends IMantenibleService<CodigoRespuestaSwitch>
{

    public List<CodigoRespuestaSwitch> buscarTodos();

    public List<CodigoRespuestaSwitch> buscarPorCodigoRespuestaSwitch(String codigoRespuestaSwitch);

    public boolean existeCodigoRespuestaSwitch(String codigoRespuestaSwitch);

    public void registrarCodigoRptaSwitch(CodigoRespuestaSwitch codigoRptaSwitch);

    public void actualizarCodigoRptaSwitch(CodigoRespuestaSwitch codigoRptaSwitch);

    public void eliminarCodigoRptaSwitch(CodigoRespuestaSwitch codigoRptaSwitch);
}