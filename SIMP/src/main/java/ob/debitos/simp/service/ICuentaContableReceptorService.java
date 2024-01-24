package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CuentaContableReceptor;

public interface ICuentaContableReceptorService extends IMantenibleService<CuentaContableReceptor>
{

    public List<CuentaContableReceptor> buscarTodos();

    public List<CuentaContableReceptor> buscarPorEscenario(
            CuentaContableReceptor cuentaContableReceptor);

    public List<CuentaContableReceptor> buscarConceptosCuentasPorEscenario(
            CuentaContableReceptor cuentaContableReceptor);

    public List<CuentaContableReceptor> registrarCuentaContable(
            CuentaContableReceptor cuentaContableReceptor);

    public List<CuentaContableReceptor> actualizarCuentaContable(
            CuentaContableReceptor cuentaContableReceptor);

    public void eliminarCuentaContable(CuentaContableReceptor cuentaContableReceptor);

}
