package ob.debitos.simp.controller.mantenimiento.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.mantenimiento.Moneda;
import ob.debitos.simp.service.IMonedaService;

@Audit(tipo = Tipo.INST, datos = Dato.INSTITUCION)
@RequestMapping("/moneda")
public @RestController class MonedaController {
	
	private @Autowired IMonedaService monedaService;
	
	@Audit(accion = Accion.Consulta, comentario = Comentario.ConsultaTodos)
    @GetMapping(params = "accion=buscarTodos")
    public List<Moneda> buscarTodos()
    {
        return this.monedaService.buscarTodos();
    }
}
