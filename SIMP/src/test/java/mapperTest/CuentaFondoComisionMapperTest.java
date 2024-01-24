package mapperTest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ob.debitos.simp.configuracion.PersistenceConfiguration;
import ob.debitos.simp.configuracion.ServiceConfiguration;
import ob.debitos.simp.mapper.ICuentaContableEmisorMapper;
import ob.debitos.simp.model.mantenimiento.CuentaContableEmisor;
import ob.debitos.simp.model.parametro.Parametro;
import ob.debitos.simp.utilitario.Verbo;

@ContextConfiguration(classes = { ServiceConfiguration.class, PersistenceConfiguration.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class CuentaFondoComisionMapperTest
{
    private @Autowired ICuentaContableEmisorMapper cuentaFondoComisionMapper;

    @Test
    public void test()
    {
        CuentaContableEmisor cuentaFondoComision = new CuentaContableEmisor();
        cuentaFondoComision.setCodigoInstitucion(20);
        cuentaFondoComisionMapper.mantener(new Parametro(Verbo.GETS_T, cuentaFondoComision))
                .stream().forEach(System.out::println);
    }
}