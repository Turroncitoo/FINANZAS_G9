package ob.debitos.simp.controller.seguridad.rest;


import javax.validation.groups.Default;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ob.debitos.simp.aspecto.anotacion.Audit;
import ob.debitos.simp.aspecto.enumeracion.Accion;
import ob.debitos.simp.aspecto.enumeracion.Comentario;
import ob.debitos.simp.aspecto.enumeracion.Dato;
import ob.debitos.simp.aspecto.enumeracion.Tipo;
import ob.debitos.simp.model.criterio.CriterioBusquedaKCV;
import ob.debitos.simp.model.seguridad.ComponenteLlaveZMK;
import ob.debitos.simp.model.seguridad.Kcv;
import ob.debitos.simp.model.seguridad.LLaveZMK;
import ob.debitos.simp.model.seguridad.LLaveZPK;
import ob.debitos.simp.model.seguridad.Usuario;
import ob.debitos.simp.service.ILlavesService;
import ob.debitos.simp.service.IUsuarioService;
import ob.debitos.simp.utilitario.ConstantesGenerales;
import ob.debitos.simp.utilitario.TDESUtil;
import ob.debitos.simp.validacion.grupo.accion.IRegistro;

@Audit(tipo = Tipo.LLAVES, datos = Dato.LLAVES)
@RequestMapping("/seguridad/llaves")
public @RestController class LlavesController {
	
	private @Autowired ILlavesService llavesService;
	private @Autowired IUsuarioService usuarioService;
	
	@Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('SEC_LLAVES', '1')")
	@PostMapping(value ="/llaveTransporte")
    public String registrarLLaveTransporte(@Validated({ IRegistro.class, Default.class }) @RequestBody ComponenteLlaveZMK componenteLlaveZMK, Errors error)
    {
        this.llavesService.registrarLLaveTransporte(componenteLlaveZMK);
		return ConstantesGenerales.REGISTRO_EXITOSO;
    }
	
	@Audit(accion = Accion.Registro, comentario = Comentario.Registro)
    @PreAuthorize("hasPermission('SEC_LLAVES', '1')")
	@PostMapping(value ="/llaveTrabajo")
    public String registrarLLaveTrabajo(@Validated({ IRegistro.class, Default.class }) @RequestBody LLaveZPK llaveZPK, Errors error)
    {	
        this.llavesService.registrarLLaveTrabajo(llaveZPK);
		return ConstantesGenerales.REGISTRO_EXITOSO;
    }
	
	@Audit(accion = Accion.Registro, comentario = Comentario.Registro)
	@PreAuthorize("hasPermission('SEC_LLAVES','2')")
    @GetMapping(params = "accion=buscarLLave")
    public boolean registrarCeremoniaLlaves()
    {	
        return this.llavesService.existeLlaveTransporte();
    }
	
	@Audit(accion = Accion.Acceso, comentario = Comentario.LlaveUba)
	@PreAuthorize("hasPermission('SEC_LLAVES','2')")
	@PostMapping(value ="/auth")
    public boolean validarUsuario(@RequestBody Usuario usuario, Errors error)
    {	
        return this.usuarioService.esContraseniaCorrectaPorIdUsuario(usuario.getIdUsuario(),usuario.getContrasenia());
    }
	
	@Audit(accion = Accion.Consulta, comentario = Comentario.Kcv)
	@PreAuthorize("hasPermission('SEC_LLAVES','2')")
    @GetMapping(params = "accion=calcularKCVComponente")
    public Kcv calcularKCVComponente(@Validated CriterioBusquedaKCV criterio, Errors error) throws Exception
    {	
		TDESUtil tDESUtil = new TDESUtil();
		Kcv kcv = new Kcv( tDESUtil.obtenerKCV(criterio.getComponente()));
		return kcv;
    }
	
	@Audit(accion = Accion.Consulta, comentario = Comentario.Kcv)
	@PreAuthorize("hasPermission('SEC_LLAVES','2')")
    @GetMapping(params = "accion=calcularKCVLlaveTrabajo")
    public Kcv calcularKCVLlaveTrabajo(@Validated CriterioBusquedaKCV criterio, Errors error)
    {	
		TDESUtil tDESUtil = new TDESUtil();
		tDESUtil.setKey("F5A0100C277F4823C820FBDC0DC7F239");
		LLaveZMK llaveZMK = this.llavesService.obtenerLlaveTransporte();
		String llaveZMKDecripted = null;
		String llaveZPKDecript = null;
		Kcv kcv = null;
		try {
			llaveZMKDecripted = tDESUtil.desencriptar(llaveZMK.getLlaveZMK());
			tDESUtil.setKey(llaveZMKDecripted);
			llaveZPKDecript = tDESUtil.desencriptar(criterio.getPKZ());
			kcv= new Kcv(tDESUtil.obtenerKCV(llaveZPKDecript));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kcv;
    }
}
