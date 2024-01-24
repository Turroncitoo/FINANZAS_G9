package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContableReceptor;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICuentaContableReceptorMapper extends IMantenibleMapper<CuentaContableReceptor>
{
    public List<CuentaContableReceptor> mantener(Parametro parametro);
}
