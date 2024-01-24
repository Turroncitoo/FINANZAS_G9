package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.mapper.IRecargaPPMapper;
import ob.debitos.simp.model.prepago.RecargaPP;
import ob.debitos.simp.service.IRecargaPPService;

@Service
public class RecargaPPService implements IRecargaPPService
{

    private @Autowired IRecargaPPMapper recargaPPMapper;

    @Override
    public List<RecargaPP> buscarTodos()
    {
        return recargaPPMapper.buscarTodos();
    }

}
