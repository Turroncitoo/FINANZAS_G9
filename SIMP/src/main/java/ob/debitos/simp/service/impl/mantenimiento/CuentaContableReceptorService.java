package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javaslang.Tuple2;
import ob.debitos.simp.mapper.ICuentaContableReceptorMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ContabConceptoCuenta;
import ob.debitos.simp.model.mantenimiento.CuentaContableReceptor;
import ob.debitos.simp.service.ICodigoTransaccionService;
import ob.debitos.simp.service.ICuentaContableReceptorService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.excepcion.EscenarioException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.ValidatorUtil;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class CuentaContableReceptorService extends
        MantenibleService<CuentaContableReceptor> implements ICuentaContableReceptorService
{
    @SuppressWarnings("unused")
    private ICuentaContableReceptorMapper cuentaContableReceptorMapper;
    
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired ICodigoTransaccionService codigoTransaccionService;

    public CuentaContableReceptorService(
            @Qualifier("ICuentaContableReceptorMapper") IMantenibleMapper<CuentaContableReceptor> mapper)
    {
        super(mapper);
        this.cuentaContableReceptorMapper = (ICuentaContableReceptorMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableReceptor> buscarTodos()
    {
        CuentaContableReceptor cuentaContableReceptor = CuentaContableReceptor.builder()
                .codigoInstitucion(parametroGeneralService.buscarCodigoInstitucion()).build();
        return this.buscar(cuentaContableReceptor, Verbo.GETS_T);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableReceptor> buscarPorEscenario(
            CuentaContableReceptor cuentaContableReceptor)
    {
        return this.buscar(cuentaContableReceptor, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeEscenario(CuentaContableReceptor cuentaContableReceptor)
    {
        return !this.buscarPorEscenario(cuentaContableReceptor).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableReceptor> buscarConceptosCuentasPorEscenario(
            CuentaContableReceptor cuentaContableReceptor)
    {
        return this.buscar(cuentaContableReceptor, Verbo.GETS_CONCEPTO_CUENTA);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableReceptor> registrarCuentaContable(
            CuentaContableReceptor cuentaContableReceptor)
    {
        cuentaContableReceptor
                .setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        if (this.existeEscenario(cuentaContableReceptor))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_YA_EXISTE);
        }
        this.validarDuplicadoDeConceptoComision(cuentaContableReceptor);
        this.validarCompensacionesDeEscenario(cuentaContableReceptor);
        this.ejecutarRegistroCuentaContable(cuentaContableReceptor);
        return this.buscarPorEscenario(cuentaContableReceptor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<CuentaContableReceptor> actualizarCuentaContable(
            CuentaContableReceptor cuentaContableReceptor)
    {
        cuentaContableReceptor
                .setCodigoInstitucion(parametroGeneralService.buscarCodigoInstitucion());
        if (!this.existeEscenario(cuentaContableReceptor))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_NO_ENCONTRADO);
        }
        this.validarDuplicadoDeConceptoComision(cuentaContableReceptor);
        this.validarCompensacionesDeEscenario(cuentaContableReceptor);
        this.eliminar(cuentaContableReceptor);
        this.ejecutarRegistroCuentaContable(cuentaContableReceptor);
        return this.buscarPorEscenario(cuentaContableReceptor);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarCuentaContable(CuentaContableReceptor cuentaContableReceptor)
    {
        if (!this.existeEscenario(cuentaContableReceptor))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_NO_ENCONTRADO);
        }
        this.eliminar(cuentaContableReceptor);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void ejecutarRegistroCuentaContable(CuentaContableReceptor cuentaContableReceptor)
    {
        List<ContabConceptoCuenta> contabConceptosCuentas = cuentaContableReceptor
                .getContabConceptosCuentas();
        for (ContabConceptoCuenta contabConceptoCuenta : contabConceptosCuentas)
        {
            cuentaContableReceptor.setCodigoAnalitico(contabConceptoCuenta.getCodigoAnalitico());
            cuentaContableReceptor.setCuentaCargo(contabConceptoCuenta.getCuentaCargo());
            cuentaContableReceptor.setCuentaAbono(contabConceptoCuenta.getCuentaAbono());
            if (contabConceptoCuenta.getTipoCompensacion()
                    .equals(ConstantesGenerales.COMPENSACION_COMISION))
            {
                cuentaContableReceptor
                        .setIdConceptoComision(contabConceptoCuenta.getIdConceptoComision());
                this.registrar(cuentaContableReceptor, Verbo.INSERT_COMISION);
            } else if (contabConceptoCuenta.getTipoCompensacion()
                    .equals(ConstantesGenerales.COMPENSACION_FONDO))
            {
                cuentaContableReceptor.setCuentaAtm(contabConceptoCuenta.getCuentaAtm());
                this.registrar(cuentaContableReceptor, Verbo.INSERT_FONDO);
            }
        }
    }

    public void validarCompensacionesDeEscenario(CuentaContableReceptor cuentaContableReceptor)
    {
        int numeroDeCuentasFondos = 0;
        int numeroDeCuentasComisiones = 0;
        boolean tieneCompensacionFondo = false;
        boolean tieneCompensacionComision = false;
        Tuple2<Boolean, Boolean> compensacionesComisionFondo = codigoTransaccionService
                .buscarCompensacionesPorCodigoTransaccionCodigoClaseTransaccion(
                        cuentaContableReceptor.getCodigoTransaccion(),
                        cuentaContableReceptor.getCodigoClaseTransaccion());
        tieneCompensacionComision = compensacionesComisionFondo._1;
        tieneCompensacionFondo = compensacionesComisionFondo._2;
        if (!tieneCompensacionComision && !tieneCompensacionFondo)
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_SIN_COMPENSACIONES);
        }
        numeroDeCuentasFondos = cuentaContableReceptor.getContabConceptosCuentas().stream()
                .filter(contabConceptoCuenta -> (contabConceptoCuenta.getTipoCompensacion()
                        .equals(ConstantesGenerales.COMPENSACION_FONDO)))
                .collect(Collectors.toList()).size();
        numeroDeCuentasComisiones = cuentaContableReceptor.getContabConceptosCuentas().stream()
                .filter(contabConceptoCuenta -> (contabConceptoCuenta.getTipoCompensacion()
                        .equals(ConstantesGenerales.COMPENSACION_COMISION)))
                .collect(Collectors.toList()).size();
        if (tieneCompensacionComision && numeroDeCuentasComisiones == 0)
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_SIN_COMISION);
        }
        if (tieneCompensacionFondo && (numeroDeCuentasFondos == 0 || numeroDeCuentasFondos > 1))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_SIN_FONDO);
        }
    }

    public void validarDuplicadoDeConceptoComision(CuentaContableReceptor cuentaContableReceptor)
    {
        if (ValidatorUtil
                .tieneCodigoConceptoDuplicado(cuentaContableReceptor.getContabConceptosCuentas()))
        {
            throw new EscenarioException(ConstantesExcepciones.ESCENARIO_CON_COMISIONES_DUPLICADAS);
        }
    }
}