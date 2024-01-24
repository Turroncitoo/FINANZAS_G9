package ob.debitos.simp.service.impl.proceso;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.IConsultaProcesoAutomaticoMapper;
import ob.debitos.simp.model.proceso.ConsultaProcesoAutomatico;
import ob.debitos.simp.service.IConsultaProcesoAutomaticoService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class ConsultaProcesoAutomaticoService implements IConsultaProcesoAutomaticoService
{
    
    private @Autowired IParametroGeneralService parametroGeneralService;
    private @Autowired IConsultaProcesoAutomaticoMapper consultaProcesoAutomaticoMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<ConsultaProcesoAutomatico> buscarProcesosAutomaticos()
    {
        Date fechaProceso = parametroGeneralService.buscarFechaProceso();
        List<ConsultaProcesoAutomatico> procesosAutomaticos = this.buscarPorFechaProceso(fechaProceso);
        if (procesosAutomaticos.isEmpty())
        {
            procesosAutomaticos = this.buscarTodos();
        }
        return procesosAutomaticos;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ConsultaProcesoAutomatico> buscarPorFechaProceso(Date fechaProceso)
    {
        return consultaProcesoAutomaticoMapper.buscarProcesoAutomaticos(Verbo.GET_DATE, fechaProceso);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<ConsultaProcesoAutomatico> buscarTodos()
    {
        return consultaProcesoAutomaticoMapper.buscarProcesoAutomaticos(Verbo.GET, new Date());
    }
    
}