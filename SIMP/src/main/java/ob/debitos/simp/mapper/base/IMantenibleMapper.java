package ob.debitos.simp.mapper.base;

import java.util.List;

import ob.debitos.simp.model.parametro.Parametro;

public interface IMantenibleMapper<T>
{
    public List<T> mantener(Parametro parametro);
}
