package ob.debitos.simp.configuracion;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@MapperScan("ob.debitos.simp.mapper")
public class PersistenceConfiguration
{
    private static Logger logger = LoggerFactory.getLogger(PersistenceConfiguration.class);
    
    @Value("${driver}")
    private String driver;
    @Value("${url}")
    private String url;
    @Value("${usuario}")
    private String usuario;
    @Value("${clave}")
    private String clave;
    
    @Bean
    public DataSource dataSource()
    {
        logger.info("Inicio de configuración de datasource.");
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        /*dataSource.setDriverClassName(env.getProperty("driver"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("usuario"));
        dataSource.setPassword(env.getProperty("clave"));*/
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(usuario);
        dataSource.setPassword(clave);
        logger.info("Fin de configuración de datasource.");
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager transactionManager()
    {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setTypeAliasesPackage("ob.debitos.simp.model");
        return sessionFactory.getObject();
    }
}