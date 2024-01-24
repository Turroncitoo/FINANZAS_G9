package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.RespuestaConcilUba;

public interface IRptaConcilUbaService extends IMantenibleService<RespuestaConcilUba>
{
    public List<RespuestaConcilUba> buscarTodos();

    public List<RespuestaConcilUba> buscarPorIdRptaConcilUba(String idRespuestaConcilUba);

    public boolean existeRptaConcilUba(String idRespuestaConcilUba);

    public void registrarRptaConcilUba(RespuestaConcilUba rptaConcilUba);

    public void actualizarRptaConcilUba(RespuestaConcilUba rptaConcilUba);

    public void eliminarRptaConcilUba(RespuestaConcilUba rptaConcilUba);
}