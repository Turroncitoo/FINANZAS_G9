package securityTest;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;

@ContextConfiguration(classes = { PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class LdapSimpleAuthenticationTest
{
    @Test
    public void test()
    {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:500/DC=Simp,DC=Odybank,DC=PE");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "CN=timoteo,CN=Roles,DC=Simp,DC=Odybank,DC=PE");
        env.put(Context.SECURITY_CREDENTIALS, "1234567");
        try
        {
            new InitialDirContext(env);
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.out.println("HOLA MUNDO");
    }
}