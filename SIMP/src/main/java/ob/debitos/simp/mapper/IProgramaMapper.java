package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.proceso.Programa;

public interface IProgramaMapper extends IMantenibleMapper<Programa>
{
    public List<Programa> mantener(Parametro parametro);
}