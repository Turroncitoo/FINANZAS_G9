package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.consulta.administrativa.Atm;

public interface IAtmService
{
    public List<Atm> buscarTodos();

    public List<Atm> buscarPorCodigoInstitucion(Integer codigoInstitucion);
    
    public List<Atm> buscarPorCodigoInstitucionActual();

    public boolean existeATM(Integer idATM, boolean esATMInstitucional);
}