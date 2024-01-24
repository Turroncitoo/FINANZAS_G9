package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.model.mantenimiento.CuentaContableMiscelaneo;

public interface ICuentaContableMiscelaneoService
        extends IMantenibleService<CuentaContableMiscelaneo>
{
    public List<CuentaContableMiscelaneo> buscarTodos();

    public List<CuentaContableMiscelaneo> buscarPorEscenario(
            CuentaContableMiscelaneo cuentaContableMiscelaneo);
    
    public List<CuentaContableMiscelaneo> registrarCuentaContable(
            CuentaContableMiscelaneo cuentaContableMiscelaneo);
    
    public List<CuentaContableMiscelaneo> actualizarCuentaContable(
            CuentaContableMiscelaneo cuentaContableMiscelaneo);
    
    public void eliminarCuentaContable(CuentaContableMiscelaneo cuentaContableMiscelaneo);
    
    public boolean existeEscenario(CuentaContableMiscelaneo cuentaContableMiscelaneo);
}