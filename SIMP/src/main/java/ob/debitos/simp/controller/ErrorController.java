package ob.debitos.simp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import ob.debitos.simp.controller.excepcion.anotacion.Vista;

@Vista
public @Controller class ErrorController
{
    @GetMapping(value = "/400")
    public String irPagina400()
    {
        return "error/400";
    }

    @GetMapping(value = "/404")
    public String irPagina404()
    {
        return "error/404";
    }

    @GetMapping(value = "/503")
    public String irPagina503()
    {
        return "error/503";
    }

    @GetMapping(value = "/403")
    public String irPagina403()
    {
        return "error/403";
    }

    @GetMapping(value = "/{codigoError:409|500}")
    public String irPagina409_500(@PathVariable String codigoError,
            @ModelAttribute("mensaje") String mensaje, ModelMap model)
    {
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("codigoError", codigoError);
        return "error/409_500";
    }
}
