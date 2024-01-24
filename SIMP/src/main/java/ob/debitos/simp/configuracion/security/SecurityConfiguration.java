package ob.debitos.simp.configuracion.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Qualifier("customUserDetailsService")
    private @Autowired UserDetailsService userDetailsService;
    private @Autowired CustomFailureLoginHandler customFailureLoginHandler;
    private @Autowired CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception
    {
        webSecurity.ignoring().antMatchers("/websockets/**");
        /*webSecurity.ignoring().antMatchers(HttpMethod.POST,"/cliente","/canalesSegurosWS" );
        webSecurity.ignoring().antMatchers(HttpMethod.PUT,"/cliente","/canalesSegurosWS" );
        webSecurity.ignoring().antMatchers(HttpMethod.DELETE,"/cliente","/canalesSegurosWS" );
        webSecurity.ignoring().antMatchers(HttpMethod.GET,"/cliente/**","/canalesSegurosWS/**" );*/
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.authorizeRequests().antMatchers("/", "/login").permitAll()
                .antMatchers("/resources/css/**", "/resources/fonts/**", "/resources/image/**",
                        "/resources/js/**")
                .permitAll().antMatchers("/**").authenticated().and().formLogin()
                .loginPage("/login").defaultSuccessUrl("/irPaginaInicio", true)
                .failureHandler(customFailureLoginHandler).usernameParameter("login")
                .passwordParameter("clave").and().csrf().and().exceptionHandling()
                .accessDeniedPage("/AccesoDenegado").and().sessionManagement()
                .invalidSessionStrategy(customInvalidSessionStrategy());
    }

    @Bean
    public CustomInvalidSessionStrategy customInvalidSessionStrategy()
    {
        return new CustomInvalidSessionStrategy();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityExpressionHandler<FilterInvocation> webSecurityExpressionHandler()
    {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new BasePermissionEvaluator());
        return expressionHandler;
    }
}