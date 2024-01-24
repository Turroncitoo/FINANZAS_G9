package ob.debitos.simp.service;

import ob.debitos.simp.model.proceso.LogControlProgramaResumen;

public interface IContabilizacionAutomaticaService
{
    public Integer contabilizarAutomaticamente(LogControlProgramaResumen logControlProgramaResumen);
}
