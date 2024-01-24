package ob.debitos.simp.mapper;

import java.util.List;

import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContableEmisor;
import ob.debitos.simp.model.parametro.Parametro;

public interface ICuentaContableEmisorMapper
        extends IMantenibleMapper<CuentaContableEmisor>
{
    public List<CuentaContableEmisor> mantener(Parametro parametro);
}
