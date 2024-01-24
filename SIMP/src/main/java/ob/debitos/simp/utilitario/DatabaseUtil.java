package ob.debitos.simp.utilitario;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.DatabaseUtil;

public class DatabaseUtil {
	private static final Logger logger = LoggerFactory.getLogger(DatabaseUtil.class);

	private DatabaseUtil()
    {
        throw new UnsupportedOperationException(ConstantesExcepciones.NO_INSTANCIAR_CLASE_ESTATICA);
    }
	
	public static void rollback(Connection conn){
    	if(conn != null){
        	try {
    			logger.info("Transaction is being rolled back.");
    			conn.rollback();
    		} catch (Exception ex) {
    			logger.error(ex.getMessage(), ex);
    		}	
    	}
    }
}
