package ob.debitos.simp.service.impl.mantenimiento;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IParametroGeneralMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.ParametroGeneral;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.service.excepcion.ListaVaciaException;
import ob.debitos.simp.service.excepcion.ValorNoEncontradoException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ParametroGeneralService extends MantenibleService<ParametroGeneral> implements IParametroGeneralService
{
    
    @SuppressWarnings("unused")
    private IParametroGeneralMapper parametroGeneralMapper;

    public ParametroGeneralService(@Qualifier("IParametroGeneralMapper") IMantenibleMapper<ParametroGeneral> mapper)
    {
        super(mapper);
        this.parametroGeneralMapper = (IParametroGeneralMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int buscarCodigoInstitucion()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_INST);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getCodigoInstitucion() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.CODIGO_INSTITUCION_NO_ENCONTRADO);
        }
        return parametroGeneral.getCodigoInstitucion();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String buscarIdEmpresa()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_EMP);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getIdEmpresa() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.ID_EMPRESA_NO_ENCONTRADO);
        }
        return parametroGeneral.getIdEmpresa();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Date buscarFechaProceso()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_DATE);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getFechaProceso() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.FECHA_PROCESO_NO_ENCONTRADO);
        }
        return parametroGeneral.getFechaProceso();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ParametroGeneral> buscarTodos()
    {
        return this.buscar(new ParametroGeneral(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ParametroGeneral> buscarinstitucionParametroGeneral()
    {
        return this.buscar(new ParametroGeneral(), Verbo.GET_INST);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarParametroGeneral(ParametroGeneral parametroGeneral)
    {
        this.registrar(parametroGeneral);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarParametroGeneral(ParametroGeneral parametroGeneral)
    {
        this.actualizar(parametroGeneral);
    }

    @Override
    public String buscarRepositorioArchivoContable()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_RAC);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getRepositorioArchivoContable() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.REPOSITORIO_ARCHIVO_CONTABLE_NO_ENCONTRADO);
        }
        return parametroGeneral.getRepositorioArchivoContable();
    }

    @Override
    public int buscarProcesoObservadasManual()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_POM);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PROCESA_OBSERVADAS_MANUAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getProcesaObservadasManual() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.PROCESA_OBSERVADAS_MANUAL_NO_ENCONTRADO);
        }
        return parametroGeneral.getProcesaObservadasManual();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String buscarBIN()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_BIN);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.BIN_RUTEO_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getBinRuteoSwitch() == null)
        {
            throw new ListaVaciaException(ConstantesExcepciones.BIN_RUTEO_NO_ENCONTRADO);
        }
        return parametroGeneral.getBinRuteoSwitch();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Byte[] buscarLogo()
    {
        List<ParametroGeneral> parametrosGenerales = this.buscar(new ParametroGeneral(), Verbo.GET_LOGO);
        if (parametrosGenerales.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.PARAMETRO_GENERAL_NO_ENCONTRADO);
        }
        ParametroGeneral parametroGeneral = parametrosGenerales.get(0);
        if (parametroGeneral.getLogoReportes() == null)
        {
            throw new ValorNoEncontradoException(ConstantesExcepciones.LOGO_REPORTES_NO_ENCONTRADO);
        }
        return parametroGeneral.getLogoReportes();
    }

}