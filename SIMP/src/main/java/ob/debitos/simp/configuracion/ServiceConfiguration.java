package ob.debitos.simp.configuracion;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "ob.debitos.simp.service.impl", "ob.debitos.simp.mapper" })
public class ServiceConfiguration
{

}
