package serviceTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.service.impl.seguridad.CategoriaRecursoService;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class CategoriaRecursoTest
{
    private @Autowired CategoriaRecursoService categoriaRecursoService;
    
    @Test
    public void test()
    {
        System.out.println(categoriaRecursoService.buscarTodosCategoriaRecurso());
    }
}