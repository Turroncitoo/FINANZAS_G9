package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IProgramaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.proceso.Programa;
import ob.debitos.simp.service.IProgramaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ProgramaService extends MantenibleService<Programa> implements IProgramaService
{

    @SuppressWarnings("unused")
    private IProgramaMapper programaMapper;

    public ProgramaService(@Qualifier("IProgramaMapper") IMantenibleMapper<Programa> mapper)
    {
        super(mapper);
        this.programaMapper = (IProgramaMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Programa> buscarTodos()
    {
        return this.buscar(new Programa(), Verbo.GETS);
    }

    public List<Programa> buscarPorGrupo(String codigoGrupo)
    {
        Programa programa = Programa.builder().codigoGrupo(codigoGrupo).build();
        return this.buscar(programa, Verbo.GETS_G);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Programa> buscarPorCodigo(String codigoGrupo, String codigoPrograma, String codigoSubModulo)
    {
        Programa programa = Programa.builder().codigoGrupo(codigoGrupo).codigoPrograma(codigoPrograma).codigoSubModulo(codigoSubModulo).build();
        return this.buscar(programa, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeCodigoPrograma(String codigoGrupo, String codigoPrograma, String codigoSubModulo)
    {
        return !this.buscarPorCodigo(codigoGrupo, codigoPrograma, codigoSubModulo).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarPrograma(Programa programa)
    {
        this.registrar(programa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarPrograma(Programa programa)
    {
        this.actualizar(programa);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarPrograma(Programa programa)
    {
        this.eliminar(programa);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean procesaSabado(String codigoGrupo, String codigoPrograma, String codigoSubModulo)
    {
        List<Programa> programas = this.buscarPorCodigo(codigoGrupo, codigoPrograma, codigoSubModulo);
        Validate.notEmpty(programas, ConstantesExcepciones.PROGRAMA_NO_ENCONTRADO, codigoGrupo, codigoPrograma, codigoSubModulo);
        Integer procesaSabado = programas.get(0).getProcesaSabado();
        Validate.notNull(procesaSabado);
        return procesaSabado == 1;
    }

}