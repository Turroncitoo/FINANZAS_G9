package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;

public interface ILogControlProgramaDetalleMapper
        extends IMantenibleMapper<LogControlProgramaDetalle>
{
    public List<LogControlProgramaDetalle> mantener(Parametro parametro);
}