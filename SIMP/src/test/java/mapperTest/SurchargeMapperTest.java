package mapperTest;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.mapper.IInstitucionMapper;
import ob.debitos.simp.model.mantenimiento.Institucion;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.utilitario.Verbo;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class SurchargeMapperTest
{

    private @Autowired IInstitucionMapper institucionMapper;

    @Test
    public void mantenerTipoGetTest()
    {
        Parametro parametro = new Parametro();
        parametro.setVerbo(Verbo.GETS);
        parametro.setObjeto(new Institucion());
        List<Institucion> instituciones = institucionMapper.mantener(parametro);
        instituciones.stream().forEach(institucion -> {
            System.out.println(institucion.toString());
        });
    }

}
