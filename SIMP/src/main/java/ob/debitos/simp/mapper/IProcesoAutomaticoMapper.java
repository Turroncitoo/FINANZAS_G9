package ob.debitos.simp.mapper;

import java.util.List;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.proceso.ProcesoAutomatico;

public interface IProcesoAutomaticoMapper extends IMantenibleMapper<ProcesoAutomatico>
{
    public List<ProcesoAutomatico> mantener(Parametro parametro);
}
