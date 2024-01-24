package ob.debitos.simp.configuracion.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component("customEntryPoint")
public class CustomEntryPoint implements AuthenticationEntryPoint
{
    private @Autowired HandlerExceptionResolver handler;
    private @Autowired Logger logger;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException ex) throws IOException, ServletException
    {
        logger.trace("Autenticando");
        handler.resolveException(request, response, null, ex);
    }
}
