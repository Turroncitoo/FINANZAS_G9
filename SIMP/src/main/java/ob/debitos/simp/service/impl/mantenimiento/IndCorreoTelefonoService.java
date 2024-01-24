package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IIndCorreoTelefonoMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.IndCorreoTelefono;
import ob.debitos.simp.service.IIndCorreoTelefonoService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class IndCorreoTelefonoService extends MantenibleService<IndCorreoTelefono>
        implements IIndCorreoTelefonoService
{
    @SuppressWarnings("unused")
    private IIndCorreoTelefonoMapper indCorreoTelefonoMapper;

    public IndCorreoTelefonoService(
            @Qualifier("IIndCorreoTelefonoMapper") IMantenibleMapper<IndCorreoTelefono> mapper)
    {
        super(mapper);
        this.indCorreoTelefonoMapper = (IIndCorreoTelefonoMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<IndCorreoTelefono> buscarTodos()
    {
        return this.buscar(new IndCorreoTelefono(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<IndCorreoTelefono> buscarPorIndCorreoTelefono(String codigoIndCorreoTelefono)
    {
        IndCorreoTelefono indCorreoTelefono = IndCorreoTelefono.builder()
                .codigoIndCorreoTelefono(codigoIndCorreoTelefono).build();
        return this.buscar(indCorreoTelefono, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeIndCorreoTelefono(String codigoIndCorreoTelefono)
    {
        return !this.buscarPorIndCorreoTelefono(codigoIndCorreoTelefono).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarIndCorreoTelefono(IndCorreoTelefono indCorreoTelefono)
    {
        this.registrar(indCorreoTelefono);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarIndCorreoTelefono(IndCorreoTelefono indCorreoTelefono)
    {
        this.actualizar(indCorreoTelefono);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarIndCorreoTelefono(IndCorreoTelefono indCorreoTelefono)
    {
        this.eliminar(indCorreoTelefono);
    }
}