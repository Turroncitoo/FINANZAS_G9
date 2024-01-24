package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;

public interface ILogControlProgramaDetalleService
        extends IMantenibleService<LogControlProgramaDetalle>
{
    public void registrarLogControlProgramaDetalle(
            List<LogControlProgramaDetalle> logControlProgramaDetalles,
            int idSecuenciaLogControlPrograma);

    public List<LogControlProgramaDetalle> buscarPorIdSecuenciaLogControlPrograma(
            int idSecuenciaLogControlPrograma);
}