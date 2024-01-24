package ob.debitos.simp.configuracion.security;

import static ob.debitos.simp.utilitario.ConstantesGenerales.AJAX_HEADER;
import static ob.debitos.simp.utilitario.ConstantesGenerales.URL_LOGIN;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.session.InvalidSessionStrategy;

public class CustomInvalidSessionStrategy implements InvalidSessionStrategy
{
    @Override
    public void onInvalidSessionDetected(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        String ajaxHeader = request.getHeader("X-Requested-With");
        if (AJAX_HEADER.equals(ajaxHeader))
        {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), URL_LOGIN);
        } else
        {
            request.getSession(true);
            redirectStrategy.sendRedirect(request, response, URL_LOGIN);
        }
    }
}