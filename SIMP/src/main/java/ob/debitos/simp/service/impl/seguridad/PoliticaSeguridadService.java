package ob.debitos.simp.service.impl.seguridad;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IPoliticaSeguridadMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.seguridad.PoliticaSeguridad;
import ob.debitos.simp.service.IPoliticaSeguridadService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class PoliticaSeguridadService extends MantenibleService<PoliticaSeguridad>
        implements IPoliticaSeguridadService
{
    @SuppressWarnings("unused")
    private IPoliticaSeguridadMapper politicaSeguridadMapper;

    private static final String GET_LONG_MIN = "GET_LONG_MIN";
    private static final String GET_AUTENTICACION_AD = "GET_AUTENTICACION_AD";
    private static final String GET_COMPLEJIDAD_CONTRASENIA = "GET_COMPLEJIDAD_CONTRASENIA";

    public PoliticaSeguridadService(
            @Qualifier("IPoliticaSeguridadMapper") IMantenibleMapper<PoliticaSeguridad> mapper)
    {
        super(mapper);
        this.politicaSeguridadMapper = (IPoliticaSeguridadMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<PoliticaSeguridad> buscarTodos()
    {
        return this.buscar(new PoliticaSeguridad(), Verbo.GETS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int buscarLongitudMinimaContrasenia()
    {
        List<PoliticaSeguridad> politicasSeguridad = this.buscar(new PoliticaSeguridad(),
                GET_LONG_MIN);
        Validate.notEmpty(politicasSeguridad,
                ConstantesExcepciones.POLITICA_SEGURIDAD_NO_ENCONTRADO);
        PoliticaSeguridad politicaSeguridad = politicasSeguridad.get(0);
        Validate.notNull(politicaSeguridad.getLongitudMinimaContrasenia(),
                ConstantesExcepciones.LONGITUD_MINIMA_CONTRASENIA_NO_ENCONTRADO);
        return politicaSeguridad.getLongitudMinimaContrasenia();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean buscarAutenticacionActiveDirectory()
    {
        List<PoliticaSeguridad> politicasSeguridad = this.buscar(new PoliticaSeguridad(),
                GET_AUTENTICACION_AD);
        Validate.notEmpty(politicasSeguridad,
                ConstantesExcepciones.POLITICA_SEGURIDAD_NO_ENCONTRADO);
        PoliticaSeguridad politicaSeguridad = politicasSeguridad.get(0);
        Validate.notNull(politicaSeguridad.getAutenticacionActiveDirectory(),
                ConstantesExcepciones.AUTENTICACION_ACTIVE_DIRECTORY_NO_ENCONTRADO);
        return politicaSeguridad.getAutenticacionActiveDirectory() == 1;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean buscarComplejidadContrasenia()
    {
        List<PoliticaSeguridad> politicasSeguridad = this.buscar(new PoliticaSeguridad(),
                GET_COMPLEJIDAD_CONTRASENIA);
        Validate.notEmpty(politicasSeguridad,
                ConstantesExcepciones.POLITICA_SEGURIDAD_NO_ENCONTRADO);
        PoliticaSeguridad politicaSeguridad = politicasSeguridad.get(0);
        Validate.notNull(politicaSeguridad.getComplejidadContrasenia(),
                ConstantesExcepciones.COMPLEJIDAD_CONTRASENIA_NO_ENCONTRADO);
        return politicaSeguridad.getComplejidadContrasenia() == 1;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarPoliticaSeguridad(PoliticaSeguridad politicaSeguridad)
    {
        this.registrar(politicaSeguridad);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarPoliticaSeguridad(PoliticaSeguridad politicaSeguridad)
    {
        this.actualizar(politicaSeguridad);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<PoliticaSeguridad> obtenerPoliticaSeguridad()
    {
        return this.buscar(new PoliticaSeguridad(), Verbo.GETS);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<PoliticaSeguridad> buscarPorCodigoPoliticaSeguridad(Integer numeroMaximoIntentos)
    {
        PoliticaSeguridad politicaSeguridad = PoliticaSeguridad.builder()
                .numeroMaximoIntentos(numeroMaximoIntentos).build();
        return this.buscar(politicaSeguridad, Verbo.GET);
    }
}