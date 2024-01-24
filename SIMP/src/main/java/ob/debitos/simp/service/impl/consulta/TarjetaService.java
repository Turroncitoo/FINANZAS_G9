package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import ob.debitos.simp.aspecto.anotacion.Truncable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ITarjetaMapper;
import ob.debitos.simp.model.consulta.administrativa.Tarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTarjeta;
import ob.debitos.simp.model.criterio.CriterioBusquedaTipoDocumento;
import ob.debitos.simp.service.ITarjetaService;

@Service
public class TarjetaService implements ITarjetaService
{

    private @Autowired ITarjetaMapper tarjetaMapper;

    @Truncable(clase = Tarjeta.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Tarjeta> buscarTodos()
    {
        return tarjetaMapper.buscarTodos();
    }

    @Truncable(clase = Tarjeta.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Tarjeta> buscarPorTipoDocumento(CriterioBusquedaTipoDocumento criterioBusqueda)
    {
        return this.tarjetaMapper.buscarPorTipoDocumento(criterioBusqueda);
    }

    @Truncable(clase = Tarjeta.class)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Tarjeta> buscarPorCriterios(CriterioBusquedaTarjeta criterioBusqueda)
    {
        return this.tarjetaMapper.buscarPorCriterios(criterioBusqueda);
    }

}