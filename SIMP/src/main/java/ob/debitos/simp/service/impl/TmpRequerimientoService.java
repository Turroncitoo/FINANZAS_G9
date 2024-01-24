package ob.debitos.simp.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.service.ITmpRequerimientoService;
import ob.debitos.simp.utilitario.DatabaseUtil;
import ob.debitos.simp.utilitario.DatesUtils;

@Service
public class TmpRequerimientoService implements ITmpRequerimientoService {
	private @Autowired Logger logger;
	private @Autowired DataSource dataSource;

	@Override
    public void eliminarCargaTemporal()
    {
		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fin;
		logger.info("@I Truncamiento de TmpRequerimiento");
		try (Connection conn = dataSource.getConnection();) {
			try (PreparedStatement stmt = conn
					.prepareStatement("DELETE TmpRequerimiento");) {
				stmt.executeUpdate();
				conn.commit();
				logger.info("El truncamiento de la TmpRequerimiento se ha realizado.");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("El truncamiento de la TmpRequerimiento ha finalizado.");
		fin = LocalDateTime.now();
		logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
    }
	
}
