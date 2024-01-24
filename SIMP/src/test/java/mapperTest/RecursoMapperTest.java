package mapperTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.mapper.ISecRecursoMapper;
import ob.debitos.simp.model.seguridad.SecRecurso;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class RecursoMapperTest
{

    private @Autowired ISecRecursoMapper recursoMapper;

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void obtenerRecursosPermitidosPorIdUsuarioTest()
    {
        List<SecRecurso> recursosPermitidos = recursoMapper
                .obtenerRecursosPermitidosPorIdUsuario("MANT01");
        recursosPermitidos.stream().forEach(recurso -> {
            System.out.println("Recurso Permitido: " + recurso.getIdRecurso());
        });
    }
}