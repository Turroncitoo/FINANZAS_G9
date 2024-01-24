
package ob.debitos.simp.controller.proceso.rest;

import java.util.List;

import ob.debitos.simp.model.criterio.CriterioBusquedaLote;
import ob.debitos.simp.model.parametro.ParametroLote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.prepago.LotePP;
import ob.debitos.simp.model.proceso.ArchivoAfiliacion;
import ob.debitos.simp.model.proceso.ArchivoLote;
import ob.debitos.simp.model.proceso.TmpRequerimiento;
import ob.debitos.simp.service.IArchivoLoteService;
import ob.debitos.simp.service.ILotePPService;
import ob.debitos.simp.utilitario.ConstantesGenerales;

@Audit(tipo = Tipo.PROG, datos = Dato.PROGRAMA)
@RequestMapping("/proceso/requerimiento/afiliacion")
public @RestController class RequerimientoAfiliacionesController
{

    private @Autowired IArchivoLoteService archivoLoteService;
    private @Autowired ILotePPService lotePPService;

    // @PreAuthorize("hasPermission('MANT_PROG', '1')")
    @PostMapping(params = "accion=registrarRequerimiento")
    public ResponseEntity<?> registrarRequerimiento(@RequestBody ArchivoAfiliacion parameter)
    {

        // En esta parte se registra el lote y se obtiene el ID generado,
        // si es innominada, se llena de una vez la tabla MovControlLotePP
        // si es nominada, únicamente se registra el lote (no se llena
        // MovControlLotePP,
        // ni TmpRequerimiento). Además se llena el cliente y la empresa.
        ArchivoLote lote = parameter.getLote();
        archivoLoteService.registrarLote(lote);

        // Si es nominada, entra a lógica y se inserta en la tabla
        // TmpRequerimiento.
        if (lote.getCategoria().compareTo("nominadas") == 0)
        {
            List<TmpRequerimiento> tmpCSV = parameter.getRegistros();
            archivoLoteService.insertarCSVAfiliacionBatch(tmpCSV, lote.getIdLote());
        }

        // Se actualiza el lote, además, si es nominada, se registra en
        // MovControlLotePP
        // y se registra a la persona con la información de la tabla
        // TmpRequerimiento
        // que se llenó en el bloque anterior.
        archivoLoteService.registrarControlLote(lote.getIdLote());
        return ResponseEntity.ok(ConstantesGenerales.REGISTRO_EXITOSO);
    }

    // @PreAuthorize("hasPermission('MANT_PROG', '1')")
    /*
     * @PostMapping(value = "/validarCarga") public List<AfiliacionesCarga>
     * validarExcelArchivoAfiliacionesNominadas(@RequestParam("archivos")
     * MultipartFile file) { return
     * this.requerimientoAfiliacionesService.validarArchivoAfiliacionesNominadas
     * (file); }
     */

    // Las actualizaciones de datos son con el metodo PUT
    // @PreAuthorize("hasPermission('MANT_PROG', '1')")
    @PutMapping
    public ResponseEntity<?> actualizarLote(@RequestBody ParametroLote parametro)
    {
        List<LotePP> lote = this.archivoLoteService.actualizarLote(parametro);
        return ResponseEntity.ok(lote);
    }

    // @PreAuthorize("hasPermission('MANT_PROG', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public ResponseEntity<?> obtenerLotesAfiliaciones()
    {
        List<LotePP> ls = lotePPService.obtenerLotesAfiliacion();
        return ResponseEntity.ok(ls);
    }

    // @PreAuthorize("hasPermission('MANT_PROG', '2')")
    @GetMapping(params = "accion=buscarPorCriterios")
    public ResponseEntity<?> obtenerLotesAfiliacionesPorCriterio(CriterioBusquedaLote criterioBusquedaLote)
    {
        List<LotePP> ls = lotePPService.obtenerLotesPorCriterios(criterioBusquedaLote);
        return ResponseEntity.ok(ls);
    }

}
