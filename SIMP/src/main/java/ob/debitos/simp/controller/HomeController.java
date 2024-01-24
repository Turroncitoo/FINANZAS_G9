package ob.debitos.simp.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ob.debitos.simp.controller.excepcion.anotacion.Vista;
import ob.debitos.simp.service.IParametroGeneralService;

@Vista
public @Controller class HomeController
{
	private @Autowired IParametroGeneralService parametroGeneralService;

    @GetMapping(value = "/irPaginaInicio")
    public String irPageInicio(Principal principal, HttpSession session)
    {
        cargarInformacionUsuario(principal.getName(), session);
        return "redirect:/inicio";
    }

    public void cargarInformacionUsuario(String username, HttpSession session)
    {
        session.setAttribute("nombreUsuario", username);
        session.setAttribute("fechaProceso", parametroGeneralService.buscarFechaProceso());
    }
    
    @GetMapping("/inicio")
	public String irPaginaInicio(Model model, Principal principal) 
    {
		model.addAttribute("usuario",principal.getName());
		return "seguras/inicio";
	}
}