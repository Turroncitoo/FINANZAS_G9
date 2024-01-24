package ob.debitos.simp.configuracion;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class SimpServletInitializer
{
    private static final String OB_SIMP_CFG = "OB_SIMP_CFG";
    private static final String ARCHIVO_APP_PROPERTIES = "database.properties";
    private static Logger logger = LoggerFactory.getLogger(SimpServletInitializer.class);

    @Bean
    public PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() 
    {
      PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();

      /*Probando configuración*/
      String rutaArchivoConfigSIMP = System.getenv(OB_SIMP_CFG);
      if (rutaArchivoConfigSIMP != null) 
      {
          logger.info("La variable de entorno {} tiene el valor de {}", OB_SIMP_CFG, rutaArchivoConfigSIMP);
          rutaArchivoConfigSIMP = rutaArchivoConfigSIMP.concat(File.separator);
          String rutaAppProperties = rutaArchivoConfigSIMP.concat(ARCHIVO_APP_PROPERTIES);
          if (new File(rutaAppProperties).exists()) 
          {
              logger.info("El archivo de configuración {} existe", rutaAppProperties);
              logger.info("Configurando spring.config.location: {}", rutaArchivoConfigSIMP);
              properties.setLocation(new FileSystemResource(rutaAppProperties));
          } else 
          {
              logger.info("El archivo de configuración {} no existe", rutaAppProperties);
              logger.info("Cargando archivos de configuración del empaquetado WAR");
              properties.setLocation(new ClassPathResource(ARCHIVO_APP_PROPERTIES));
          }
      } else 
      {
          logger.info("La variable de entorno {} no existe", OB_SIMP_CFG);
          logger.info("Cargando archivos locales de configuración del empaquetado WAR");
          properties.setLocation(new ClassPathResource(ARCHIVO_APP_PROPERTIES));
      }
      properties.setIgnoreResourceNotFound(false);

      return properties;
    }
}
