package ob.debitos.simp.service.impl.mantenimiento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IPaisMapper;
import ob.debitos.simp.model.mantenimiento.Pais;
import ob.debitos.simp.service.IPaisService;

@Service
public class PaisService implements IPaisService
{

    private @Autowired IPaisMapper paisMapper;

    @Override
    public List<Pais> buscarTodos()
    {
        return paisMapper.buscarTodos();
    }

}
