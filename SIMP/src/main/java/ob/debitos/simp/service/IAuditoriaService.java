package ob.debitos.simp.service;

import java.util.List;

import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaAuditoria;
import ob.debitos.simp.model.seguridad.Auditoria;

public interface IAuditoriaService extends IMantenibleService<Auditoria>
{
    public List<Auditoria> buscarPistasAuditoriaPorCriterio(CriterioBusquedaAuditoria criterio);

    public void registrarAuditoria(Auditoria auditoria);

    public void registrarAuditoria(Tipo tipo, Comentario comentario, Accion accion, int exito,
            String nombreUsuario, String direccionIp);
}