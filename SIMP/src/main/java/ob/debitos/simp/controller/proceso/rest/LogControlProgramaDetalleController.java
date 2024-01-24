package ob.debitos.simp.controller.proceso.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.proceso.LogControlProgramaDetalle;
import ob.debitos.simp.service.ILogControlProgramaDetalleService;

@Audit(tipo = Tipo.CON_LOG_PROC_AUTOM)
public @RestController class LogControlProgramaDetalleController
{
    private @Autowired ILogControlProgramaDetalleService logControlProgramaDetalleService;

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaDetalle)
    @PreAuthorize("hasPermission('CON_LOGPROCAUTOM', '2')")
    @GetMapping(value = "/proceso/logControlPrograma/{secuencia}/detalle", params = "accion=buscar")
    public List<LogControlProgramaDetalle> buscarPorIdSecuenciaLogControlPrograma(
            @PathVariable int secuencia)
    {
        return this.logControlProgramaDetalleService
                .buscarPorIdSecuenciaLogControlPrograma(secuencia);
    }
}