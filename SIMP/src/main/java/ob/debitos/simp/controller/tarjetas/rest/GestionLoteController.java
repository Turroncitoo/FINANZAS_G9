package ob.debitos.simp.controller.tarjetas.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaLoteTarjetas;
import ob.debitos.simp.model.proceso.AfiliacionesCarga;
import ob.debitos.simp.model.tarjetas.Lote;
import ob.debitos.simp.model.tarjetas.LoteDetalle;
import ob.debitos.simp.model.tarjetas.LoteFormulario;
import ob.debitos.simp.model.tarjetas.LoteRecargaDebito;
import ob.debitos.simp.service.IGestionLotesService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.AFILIACIONES)
public @RestController class GestionLoteController
{

    public @Autowired IGestionLotesService gestionLotesService;

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '1')")
    @PostMapping(value = "/afiliaciones/innominadas")
    public ResponseEntity<String> registroAfiliacionesLoteInnominadas(@RequestBody Lote lote, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.gestionLotesService.registroAfiliacionesLoteInnominadas(lote);
        return new ResponseEntity<>("Registro de Lote de Afiliaciones Inominadas exitoso: <b>Lote: " + lote.getIdLote() + "</b>", HttpStatus.OK);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '1')")
    @PostMapping(value = "/afiliaciones/nominadas/validar")
    public List<AfiliacionesCarga> validarAfiliacionesLoteNominadas(@RequestParam("archivos") MultipartFile file)
    {
        return this.gestionLotesService.validarArchivoAfiliacionesNominadas(file);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '1')")
    @PostMapping(value = "/afiliaciones/nominadas")
    public ResponseEntity<String> registroAfiliacionesLotNominadas(@RequestBody LoteFormulario loteFormulario)
    {
        String msj = this.gestionLotesService.registroAfiliacionesLoteNominadas(loteFormulario);
        return new ResponseEntity<>(msj, HttpStatus.OK);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '2')")
    @GetMapping(value = "/consulta/lote", params = "accion=buscarPorCriterios")
    public List<Lote> consultaLotePorCriterios(CriterioBusquedaLoteTarjetas criterio)
    {
        return this.gestionLotesService.consultaLotesPorCriterios(criterio);
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '2')")
    @GetMapping(value = "/consulta/lote", params = "accion=buscarDetalleAfiliacion")
    public List<LoteDetalle> consultaLoteDetalleAfiliacion(CriterioBusquedaLoteTarjetas criterio)
    {
        return this.gestionLotesService.consultaLotesDetalleAfiliacion(criterio);
    }
    
    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '2')")
    @GetMapping(value = "/consulta/lote", params = "accion=buscarDetalleRecargaDebito")
    public List<LoteDetalle> consultaLoteDetalleRecargaDebito(CriterioBusquedaLoteTarjetas criterio)
    {
        return this.gestionLotesService.consultaLotesDetalleRecargaDebito(criterio);
    }
    
    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '3')")
    @PutMapping(value = "/lote")
    public List<Lote> actualizarLote(@RequestBody Lote lote, Errors error)
    {
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        this.gestionLotesService.actualizarLote(lote);
        return this.gestionLotesService.consultaLotesPorCriterios(CriterioBusquedaLoteTarjetas.builder().idLote(lote.getIdLote()).build());
    }

    @Audit(accion = Accion.Consulta, comentario = Comentario.Consulta)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '3')")
    @DeleteMapping(value = "/lote/{idLote}")
    public void eliminarLote(@PathVariable Integer idLote)
    {
        this.gestionLotesService.eliminarLote(idLote);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '1')")
    @PostMapping(value = "/recargaDebito/validar")
    public List<LoteRecargaDebito> validarLoteRecargaDebitos(@RequestParam("archivos") MultipartFile file)
    {
        return this.gestionLotesService.validarLoteRecargaDebitos(file);
    }

    @Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('EJEC_AFILIACIONES', '1')")
    @PostMapping(value = "/recargaDebito/proceso")
    public ResponseEntity<String> registroRecargaYDebitos(@RequestBody LoteFormulario loteFormulario)
    {
        this.gestionLotesService.registroRecargaYDebitos(loteFormulario);
        return new ResponseEntity<>("Registro de Lote de Recarga y Débitos con éxito", HttpStatus.OK);
    }

}
