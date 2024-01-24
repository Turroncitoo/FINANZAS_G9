package ob.debitos.simp.service.impl.consulta;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILotePPMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaLote;
import ob.debitos.simp.model.mantenimiento.Cliente;
import ob.debitos.simp.model.prepago.LoteInnominadoPP;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.service.ILotePPService;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.XmlStreamUtil;

@Service
public class LotePPService implements ILotePPService
{

    private @Autowired ILotePPMapper loteMapper;

    private @Autowired IParametroGeneralService iParametroGeneralService;

    @Override
    public List<LotePP> buscarTodos()
    {

        return loteMapper.buscarTodos();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarLote(LotePP lotePP)
    {
        XmlStreamUtil streamUtil = new XmlStreamUtil();
        StringBuilder sbFecHora = new StringBuilder();
        Calendar cal = Calendar.getInstance();
        sbFecHora.append(String.format("%02d", cal.get(Calendar.MONTH) + 1));
        sbFecHora.append(String.format("%02d", cal.get(Calendar.DAY_OF_MONTH)));
        sbFecHora.append(String.format("%02d", cal.get(Calendar.HOUR_OF_DAY)));
        sbFecHora.append(String.format("%02d", cal.get(Calendar.MINUTE)));
        sbFecHora.append(String.format("%02d", cal.get(Calendar.SECOND)));
        for (int i = 0; i < lotePP.getLstControlLote().size(); i++)
        {
            lotePP.getLstControlLote().get(i).setFecHora(sbFecHora.toString());
        }
        String idEmpresa = lotePP.getClCliente().getIdEmpresa();
        if (!lotePP.getLstControlLote().isEmpty())
        {
            lotePP.setClCliente(Cliente.builder().idCliente(lotePP.getLstControlLote().get(0).getPersona().getCodCliente()).descripcion(lotePP.getLstControlLote().get(0).getPersona().getNomCliente()).idEmpresa(idEmpresa).build());
        }
        lotePP.setNIdInstitucion(iParametroGeneralService.buscarCodigoInstitucion());
        loteMapper.insertarLote(streamUtil.serializarLote(lotePP));
    }

    @Override
    public List<LotePP> obtenerLotesAfiliacion()
    {
        return loteMapper.obtenerLotes(ConstantesGenerales.INSTANCIA_AFILIACION);
    }

    @Override
    public void registrarLoteInnominadas(LoteInnominadoPP loteInnominadoPP)
    {
        loteInnominadoPP.setNIdInstitucion(iParametroGeneralService.buscarCodigoInstitucion());
        loteInnominadoPP.setDFechaProceso(new Date());
        loteMapper.insertarLoteInnominadas(loteInnominadoPP);
    }

    @Override
    public void procesarLoteAfiliaciones(Integer nIdLote)
    {
        loteMapper.procesarLoteAfiliaciones(nIdLote);
    }

    @Override
    public boolean existeLoteRecarga(Integer nIdLote)
    {
        if (loteMapper.obtenerLoteRecarga(nIdLote) == null)
            return false;
        else
            return true;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LotePP> obtenerLotesPorCriterios(CriterioBusquedaLote criterioBusquedaLote)
    {
        return loteMapper.obtenerLotesPorCriterios(criterioBusquedaLote);
    }

}
