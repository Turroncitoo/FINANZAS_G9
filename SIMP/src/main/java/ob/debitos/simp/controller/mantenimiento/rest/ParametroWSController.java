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
import ob.debitos.simp.model.mantenimiento.ParametroWS;
import ob.debitos.simp.service.IParametroWSService;
import ob.debitos.simp.utilitario.ExcepcionUtil;

@Audit(tipo = Tipo.PARAM_WS, datos = Dato.PARAM_WS)
@RequestMapping("/parametroWS")

public @RestController class ParametroWSController
{

    public @Autowired IParametroWSService parametroWSService;

    private static final Logger logger = LoggerFactory.getLogger(ParametroWSController.class);

    @Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @PreAuthorize("hasPermission('MANT_PARAMWS', '2')")
    @GetMapping(params = "accion=buscarTodos")
    public List<ParametroWS> buscarTodos()
    {
        return this.parametroWSService.buscarTodos();
    }

    @Audit(accion = Accion.Actualizacion, comentario = Comentario.Actualizacion)
    @PreAuthorize("hasPermission('MANT_PARAMWS', '3')")
    @PutMapping
    public ResponseEntity<?> actualizarParametroWS(@Validated @RequestBody ParametroWS parametroWS, Errors error)
    {
        logger.info("{}", parametroWS);
        ExcepcionUtil.lanzarExcepcionSiHayErrores(error);
        parametroWSService.actualizarParametroWS(parametroWS);
        return ResponseEntity.ok(parametroWSService.buscarTodos());
    }

}
