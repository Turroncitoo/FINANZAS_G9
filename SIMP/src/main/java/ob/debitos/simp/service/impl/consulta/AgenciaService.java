package ob.debitos.simp.service.impl.consulta;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IAgenciaMapper;
import ob.debitos.simp.model.consulta.administrativa.Agencia;
import ob.debitos.simp.service.IAgenciaService;

@Service
public class AgenciaService implements IAgenciaService
{

    private @Autowired IAgenciaMapper agenciaMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Agencia> buscarTodos()
    {
        return agenciaMapper.buscarTodos();
    }

}