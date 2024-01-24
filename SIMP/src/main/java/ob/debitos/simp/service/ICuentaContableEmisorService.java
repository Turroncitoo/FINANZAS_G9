package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CuentaContableEmisor;

public interface ICuentaContableEmisorService extends IMantenibleService<CuentaContableEmisor>
{

    public List<CuentaContableEmisor> buscarTodos();

    public List<CuentaContableEmisor> buscarPorEscenario(
            CuentaContableEmisor cuentaContableEmisor);

    public List<CuentaContableEmisor> buscarConceptosCuentasPorEscenario(
            CuentaContableEmisor cuentaContableEmisor);

    public List<CuentaContableEmisor> registrarCuentaContable(
            CuentaContableEmisor cuentaContableEmisor);

    public List<CuentaContableEmisor> actualizarCuentaContable(
            CuentaContableEmisor cuentaContableEmisor);

    public void eliminarCuentaContable(CuentaContableEmisor cuentaContableEmisor);
    
    public List<CuentaContableEmisor> buscarTodosConceptosCuentas();

}
