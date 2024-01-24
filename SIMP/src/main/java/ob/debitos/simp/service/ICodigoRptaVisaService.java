package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CodigoRespuestaVisa;

public interface ICodigoRptaVisaService extends IMantenibleService<CodigoRespuestaVisa>
{

    public List<CodigoRespuestaVisa> buscarTodos();

    public List<CodigoRespuestaVisa> buscarPorCodigoRptaVisa(String codigoRespuestaVisa);

    public boolean existeCodigoRptaVisa(String codigoRespuestaVisa);

    public void registrarCodigoRptaVisa(CodigoRespuestaVisa codigoRptaVisa);

    public void actualizarCodigoRptaVisa(CodigoRespuestaVisa codigoRptaVisa);

    public void eliminarCodigoRptaVisa(CodigoRespuestaVisa codigoRptaVisa);
}