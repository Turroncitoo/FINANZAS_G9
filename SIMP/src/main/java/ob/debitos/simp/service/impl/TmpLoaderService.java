package ob.debitos.simp.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.service.ITmpLoaderService;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.DatabaseUtil;
import ob.debitos.simp.utilitario.DatesUtils;

@Service
public class TmpLoaderService implements ITmpLoaderService {
	private @Autowired Logger logger;
	private @Autowired DataSource dataSource;
	
	@Override
    public void eliminarCargaTemporal()
    {
		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fin;
		logger.info("@I Truncamiento de TmpLoader");
		try (Connection conn = dataSource.getConnection();) {
			try (PreparedStatement stmt = conn
					.prepareStatement("DELETE TmpLoader");) {
				stmt.executeUpdate();
				conn.commit();
				logger.info("El truncamiento de la TmpLoader se ha realizado.");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("El truncamiento de la TmpLoader ha finalizado.");
		fin = LocalDateTime.now();
		logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
    }
	
	public void elimianrCargaTemportalPorInstitucion(Integer idInstitucion){
		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fin;
		logger.info("@I Truncamiento de TmpLoader por institucion");
		try (Connection conn = dataSource.getConnection();) {
			try (PreparedStatement stmt = conn
					.prepareStatement("DELETE TmpLoader WHERE nIdInstitucion = ?");) {
				stmt.setInt(1, idInstitucion);
				stmt.executeUpdate();
				conn.commit();
				logger.info("El truncamiento de la TmpLoader por institucion se ha realizado.");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("El truncamiento de la TmpLoader por institucion ha finalizado.");
		fin = LocalDateTime.now();
		logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
	}
	
	/**
     * Recorre cada trama del archivo de UNIBANCA y lo almacena en la tabla
     * temporal.
     * 
     * @param filePath
     *            ruta de ubicación del archivo de UNIBANCA
     * @param size
     *            el tamaño de cada trama del archivo de UNIBANCA
     * @param idInstitucion
     * 			  codigo de institucion a insertar
     */
	public void uploadFileBatch(String filePath, int size, int idInstitucion) {
    	LocalDateTime inicio;
    	LocalDateTime fin;
		logger.info("Inicio de carga batch del archivo {}", filePath);
		inicio = LocalDateTime.now();
		if(size != 0){
			this.uploadFileBySize(filePath, size, idInstitucion);
		} else {
			this.uploadFileByLine(filePath, idInstitucion);
		}
		logger.info("Finaliza carga masiva");
		fin = LocalDateTime.now();
		logger.info("Resultado {}", DatesUtils.tiempoEntre(inicio, fin));
    }
	
    public void uploadFileBySize(String filePath, int size, int idInstitucion) {
		logger.info("Carga de archivo por tamanio de registro {}", filePath);
		try (Connection conn = dataSource.getConnection()) {
			int batchSize = 10000;
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO TMPLOADER(vCampo, vIdentificador, nIdInstitucion) VALUES (?,?,?)");) {
					try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
						int nline = (int) file.length() / size;
						byte[] bytes = new byte[size];
						for (int nb = 0; nb < nline;) {
							file.seek(nb * (long) size);
							file.read(bytes);
							String reg = new String(bytes);
							stmt.setString(1, reg);
							stmt.setString(2, reg.charAt(0) + "");
							stmt.setInt(3, idInstitucion);
							stmt.addBatch();
							nb++;
							if (nb % batchSize == 0 || nb == nline) {
								stmt.executeBatch();
								conn.commit();
								logger.info("Inserto {}", nb);
							}
						}
					} catch (FileNotFoundException e) {
						logger.error(e.getMessage(), e);
						throw new EjecucionManualException(
								String.format(ConstantesExcepciones.ARCHIVO_NO_ENCONTRADO, filePath));
					} catch (IOException e) {
						logger.error(e.getMessage(), e);
						throw new EjecucionManualException(
								String.format(ConstantesExcepciones.ERROR_LECTURA_ARCHIVO, filePath));
					}
				logger.info("Transaction is commited successfully.");
			} catch (SQLException e) {
				logger.error(e.getMessage());
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}
    }
    
    public void uploadFileByLine(String filePath, int idInstitucion) {
		logger.info("Carga de archivo por linea {}", filePath);
		try (Connection conn = dataSource.getConnection()) {
			int batchSize = 10000;
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO TMPLOADER(vCampo, vIdentificador, nIdInstitucion) VALUES (?,?,?)");) {
					logger.info("Lee por linea");
					try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {
						int nline = (int) br.lines().count();
						logger.info("Default charset {}", Charset.defaultCharset());
						try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.ISO_8859_1)) {
							int[] idx = { 0 };
							stream.forEach(reg -> {
								try {
									stmt.setString(1, reg);
									stmt.setString(2, reg.charAt(0) + "");
									stmt.setInt(3, idInstitucion);
									stmt.addBatch();
									idx[0]++;
									if (idx[0] % batchSize == 0 || idx[0] == nline) {
										stmt.executeBatch();
										conn.commit();
										logger.info("Inserto {}", idx[0]);
									}
								} catch (SQLException e) {
									logger.error(e.getMessage(), e);
									DatabaseUtil.rollback(conn);
								}
							});
						}
					} catch (FileNotFoundException e) {
						logger.error(e.getMessage(), e);
						throw new EjecucionManualException(
								String.format(ConstantesExcepciones.ERROR_LECTURA_ARCHIVO, filePath));
					} catch (IOException e1) {
						logger.error(e1.getMessage(), e1);
						throw new EjecucionManualException(
								String.format(ConstantesExcepciones.ERROR_LECTURA_ARCHIVO, filePath));
					}				
				logger.info("Transaction is commited successfully.");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
    }
 
}
