package ob.debitos.simp.service.impl.proceso;

import static ob.debitos.simp.utilitario.Verbo.GET;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.mapper.ILogControlProgramaDetalleMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.service.ILogControlProgramaDetalleService;
import ob.debitos.simp.service.impl.MantenibleService;

@Service
public class LogControlProgramaDetalleService extends MantenibleService<LogControlProgramaDetalle>
        implements ILogControlProgramaDetalleService
{
    @SuppressWarnings("unused")
    private ILogControlProgramaDetalleMapper logControlProgramaDetalleMapper;

    public LogControlProgramaDetalleService(
            @Qualifier("ILogControlProgramaDetalleMapper") IMantenibleMapper<LogControlProgramaDetalle> mapper)
    {
        super(mapper);
        this.logControlProgramaDetalleMapper = (ILogControlProgramaDetalleMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarLogControlProgramaDetalle(
            List<LogControlProgramaDetalle> logControlProgramaDetalles,
            int idSecuenciaLogControlPrograma)
    {
        for (LogControlProgramaDetalle logControlProgramaDetalle : logControlProgramaDetalles)
        {
            logControlProgramaDetalle
                    .setIdSecuenciaLogControlPrograma(idSecuenciaLogControlPrograma);
            this.registrar(logControlProgramaDetalle);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<LogControlProgramaDetalle> buscarPorIdSecuenciaLogControlPrograma(
            int idSecuenciaLogControlPrograma)
    {
        LogControlProgramaDetalle logControlProgramaDetalle = LogControlProgramaDetalle.builder()
                .idSecuenciaLogControlPrograma(idSecuenciaLogControlPrograma).build();
        return this.buscar(logControlProgramaDetalle, GET);
    }
}