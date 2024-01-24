package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IErrorMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.mantenimiento.Error;
import ob.debitos.simp.service.IErrorService;
import ob.debitos.simp.service.excepcion.ListaVaciaException;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.StringsUtils;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ErrorService extends MantenibleService<Error> implements IErrorService
{
    @SuppressWarnings("unused")
    private IErrorMapper errorMapper;

    public ErrorService(@Qualifier("IErrorMapper") IMantenibleMapper<Error> mapper)
    {
        super(mapper);
        this.errorMapper = (IErrorMapper) mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Error> buscarTodos()
    {
        return this.buscar(new Error(), Verbo.GETS);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<Error> buscarPorIdError(int idError)
    {
        Error error = Error.builder().idError(idError).build();
        return this.buscar(error, Verbo.GET);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String obtenerDescripcionError(Integer idError)
    {
        List<Error> errores = this.buscarPorIdError(idError);
        if (errores.isEmpty())
        {
            throw new ListaVaciaException(ConstantesExcepciones.ERROR_DESCONOCIDO);
        } else
        {
            Error error = errores.get(0);
            return StringsUtils.concatenarCadena("Código de Error: ", error.getIdError(), " - ",
                    error.getDescripcion());
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean existeError(int iderror)
    {
        return !this.buscarPorIdError(iderror).isEmpty();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarError(Error error)
    {
        this.registrar(error);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void actualizarError(Error error)
    {
        this.actualizar(error);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void eliminarError(Error error)
    {
        this.eliminar(error);
    }
}