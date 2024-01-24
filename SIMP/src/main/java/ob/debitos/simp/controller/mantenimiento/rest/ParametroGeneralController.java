package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.ParametroGeneral;
import ob.debitos.simp.service.IParametroGeneralService;
import ob.debitos.simp.utilitario.DatesUtils;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.PARAM_GRAL, datos = Dato.PARAM_GRAL)
@RequestMapping("/parametroGeneral")
public @RestController class ParametroGeneralController
{

    private @Autowired IParametroGeneralService parametroGeneralService;

    private static final Logger logger = LoggerFactory.getLogger(ParametroGeneralController.class);

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMGRAL', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ParametroGeneral> buscarTodos()
    {
        return this.parametroGeneralService.buscarTodos();
    }

    @PreAuthorize("hasPermission('MANT_PARAMGRAL', '2')")
    @GetMapping(value = "/fechaProceso", params = "accion=buscar")
    public String buscarFechaProceso()
    {
        logger.info("{}", parametroGeneralService.buscarFechaProceso());
        return DatesUtils.obtenerFechaEnFormato(parametroGeneralService.buscarFechaProceso(), "dd/MM/yyyy");
    }

    @PreAuthorize("hasPermission('MANT_PARAMGRAL', '2')")
    @GetMapping(value = "/procesaObservadasManual", params = "accion=buscar")
    public Integer buscarProcesaObservadasManual()
    {
        return parametroGeneralService.buscarProcesoObservadasManual();
    }

    @PreAuthorize("hasPermission('MANT_PARAMGRAL', '2')")
    @GetMapping(value = "/institucionParametroGeneral", params = "accion=buscar")
    public List<ParametroGeneral> institucionParametroGeneral()
    {
        return parametroGeneralService.buscarinstitucionParametroGeneral();
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PARAMGRAL', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarParametroGeneral(@Validated @RequestBody ParametroGeneral parametroGeneral, Errors error)
    {
        logger.info("{}", parametroGeneral);
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroGeneralService.actualizarParametroGeneral(parametroGeneral);
        return ResponseEntity.ok(parametroGeneralService.buscarTodos());
    }

}