package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javaslang.Tuple2;
import ob.debitos.simp.mapper.ICuentaContableEmisorMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ContabConceptoCuenta;
import ob.debitos.simp.model.mantenimiento.CuentaContableEmisor;
import ob.debitos.simp.service.ICodigoTransaccionService;
import ob.debitos.simp.service.ICuentaContableEmisorService;
import ob.debitos.simp.service.excepcion.EscenarioException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CuentaContableEmisorService
        extends MantenibleService<CuentaContableEmisor>
        implements ICuentaContableEmisorService
{
    @SuppressWarnings("unused")
    private ICuentaContableEmisorMapper cuentaContableEmisorMapper;
    private @Autowired ICodigoTransaccionService codigoTransaccionService;

    public CuentaContableEmisorService(
            @Qualifier("ICuentaContableEmisorMapper") IMantenibleMapper<CuentaContableEmisor> mapper)
    {
        super(mapper);
        this.cuentaContableEmisorMapper = (ICuentaContableEmisorMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableEmisor> buscarTodos()
    {
        return this.buscar(new CuentaContableEmisor(), Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableEmisor> buscarPorEscenario(
            CuentaContableEmisor cuentaContableEmisor)
    {
        return this.buscar(cuentaContableEmisor, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableEmisor> buscarConceptosCuentasPorEscenario(
            CuentaContableEmisor cuentaContableEmisor)
    {
        return this.buscar(cuentaContableEmisor, Verbo.GETS_CONCEPTO_CUENTA);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableEmisor> registrarCuentaContable(
            CuentaContableEmisor cuentaContableEmisor)
    {
        if (this.existeEscenario(cuentaContableEmisor))
        {
            throw new EscenarioException(
                    ConstantesExcepciones.ESCENARIO_YA_EXISTE);
        }
        this.validarDuplicadoDeConceptoComision(cuentaContableEmisor);
        this.validarCompensacionesDeEscenario(cuentaContableEmisor);
        this.ejecutarRegistroCuentaContable(cuentaContableEmisor);
        return this.buscarPorEscenario(cuentaContableEmisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableEmisor> actualizarCuentaContable(
            CuentaContableEmisor cuentaContableEmisor)
    {
        if (!this.existeEscenario(cuentaContableEmisor))
        {
            throw new EscenarioException(
                    ConstantesExcepciones.ESCENARIO_NO_ENCONTRADO);
        }
        this.validarDuplicadoDeConceptoComision(cuentaContableEmisor);
        this.validarCompensacionesDeEscenario(cuentaContableEmisor);
        this.eliminar(cuentaContableEmisor);
        this.ejecutarRegistroCuentaContable(cuentaContableEmisor);
        return this.buscarPorEscenario(cuentaContableEmisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCuentaContable(
            CuentaContableEmisor cuentaContableEmisor)
    {
        this.eliminar(cuentaContableEmisor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableEmisor> buscarTodosConceptosCuentas()
    {
        CuentaContableEmisor cuentaContableEmisor = CuentaContableEmisor
                .builder().build();
        return this.buscar(cuentaContableEmisor, Verbo.GETS_CONCEPTO_CUENTA_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean existeEscenario(CuentaContableEmisor cuentaContableEmisor)
    {
        return !this.buscarPorEscenario(cuentaContableEmisor).isEmpty();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void ejecutarRegistroCuentaContable(
            CuentaContableEmisor cuentaContableEmisor)
    {
        List<ContabConceptoCuenta> contabConceptosCuentas = cuentaContableEmisor
                .getContabConceptosCuentas();
        for (ContabConceptoCuenta contabConceptoCuenta : contabConceptosCuentas)
        {
            cuentaContableEmisor.setCodigoAnalitico(
                    contabConceptoCuenta.getCodigoAnalitico());
            cuentaContableEmisor
                    .setCuentaCargo(contabConceptoCuenta.getCuentaCargo());
            cuentaContableEmisor
                    .setCuentaAbono(contabConceptoCuenta.getCuentaAbono());
            if (contabConceptoCuenta.getTipoCompensacion()
                    .equals(ConstantesGenerales.COMPENSACION_COMISION))
            {
                cuentaContableEmisor.setIdConceptoComision(
                        contabConceptoCuenta.getIdConceptoComision());
                this.registrar(cuentaContableEmisor, Verbo.INSERT_COMISION);
            } else if (contabConceptoCuenta.getTipoCompensacion()
                    .equals(ConstantesGenerales.COMPENSACION_FONDO))
            {
                this.registrar(cuentaContableEmisor, Verbo.INSERT_FONDO);
            }
        }
    }

    public void validarCompensacionesDeEscenario(
            CuentaContableEmisor cuentaContableEmisor)
    {
        int numeroDeCuentasFondos = 0;
        int numeroDeCuentasComisiones = 0;
        boolean tieneCompensacionFondo = false;
        boolean tieneCompensacionComision = false;
        Tuple2<Boolean, Boolean> compensacionesComisionFondo = codigoTransaccionService
                .buscarCompensacionesPorCodigoTransaccionCodigoClaseTransaccion(
                        cuentaContableEmisor.getCodigoTransaccion(),
                        cuentaContableEmisor.getCodigoClaseTransaccion());
        tieneCompensacionComision = compensacionesComisionFondo._1;
        tieneCompensacionFondo = compensacionesComisionFondo._2;
        if (!tieneCompensacionComision && !tieneCompensacionFondo)
        {
            throw new EscenarioException(
                    ConstantesExcepciones.ESCENARIO_SIN_COMPENSACIONES);
        }
        numeroDeCuentasFondos = cuentaContableEmisor.getContabConceptosCuentas()
                .stream()
                .filter(contabConceptoCuenta -> (contabConceptoCuenta
                        .getTipoCompensacion()
                        .equals(ConstantesGenerales.COMPENSACION_FONDO)))
                .collect(Collectors.toList()).size();
        numeroDeCuentasComisiones = cuentaContableEmisor
                .getContabConceptosCuentas().stream()
                .filter(contabConceptoCuenta -> (contabConceptoCuenta
                        .getTipoCompensacion()
                        .equals(ConstantesGenerales.COMPENSACION_COMISION)))
                .collect(Collectors.toList()).size();
        if (tieneCompensacionComision && numeroDeCuentasComisiones == 0)
        {
            throw new EscenarioException(
                    ConstantesExcepciones.ESCENARIO_SIN_COMISION);
        }
        if (tieneCompensacionFondo
                && (numeroDeCuentasFondos == 0 || numeroDeCuentasFondos > 1))
        {
            throw new EscenarioException(
                    ConstantesExcepciones.ESCENARIO_SIN_FONDO);
        }
    }

    public void validarDuplicadoDeConceptoComision(
            CuentaContableEmisor cuentaContableEmisor)
    {
        if (ValidatorUtil.tieneCodigoConceptoDuplicado(
                cuentaContableEmisor.getContabConceptosCuentas()))
        {
            throw new EscenarioException(
                    ConstantesExcepciones.ESCENARIO_CON_COMISIONES_DUPLICADAS);
        }
    }
}