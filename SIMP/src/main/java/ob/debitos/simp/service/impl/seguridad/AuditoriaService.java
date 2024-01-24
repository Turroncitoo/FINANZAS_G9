package ob.debitos.simp.service.impl.seguridad;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.mapper.IAuditoriaMapper;
import ob.debitos.simp.mapper.base.IMantenibleMapper;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuditoria;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.model.seguridad.Auditoria;
import ob.debitos.simp.service.IAuditoriaService;
import ob.debitos.simp.service.impl.MantenibleService;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.Verbo;

@Service
public class AuditoriaService extends MantenibleService<Auditoria> implements IAuditoriaService
{
    private IAuditoriaMapper auditoriaMapper;

    public AuditoriaService(@Qualifier("IAuditoriaMapper") IMantenibleMapper<Auditoria> mapper)
    {
        super(mapper);
        this.auditoriaMapper = (IAuditoriaMapper) mapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Auditoria> buscarPistasAuditoriaPorCriterio(
            CriterioBusquedaAuditoria criterioBusqueda)
    {
        return this.auditoriaMapper.buscarPistasAuditoriaPorCriterio(criterioBusqueda);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAuditoria(Auditoria auditoria)
    {
        auditoria.setFecha(new Date());
        auditoria.setHora(DatesUtils.obtenerFechaEnFormato(new Date(), DatesUtils.FORMATO_HHMMSS));
        this.registrar(auditoria);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAuditoria(Tipo tipo, Comentario comentario, Accion accion, int exito,
            String nombreUsuario, String direccionIp)
    {
        Auditoria auditoria = Auditoria.builder().idRecurso(tipo.getIdRecurso())
                .idAccion(accion.toString()).comentario(comentario.toString()).exito(exito)
                .direccionIp(direccionIp).nombreUsuario(nombreUsuario).fecha(new Date())
                .hora(DatesUtils.obtenerFechaEnFormato(new Date(), DatesUtils.FORMATO_HHMMSS))
                .build();
        this.auditoriaMapper.mantener(new Parametro(Verbo.INSERT, auditoria));
    }
}