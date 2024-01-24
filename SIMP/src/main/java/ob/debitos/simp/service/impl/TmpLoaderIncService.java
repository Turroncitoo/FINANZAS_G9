package ob.debitos.simp.service.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ob.debitos.simp.service.ITmpLoaderIncService;
import ob.debitos.simp.service.excepcion.EjecucionManualException;
import ob.debitos.simp.utilitario.ConstantesExcepciones;
import ob.debitos.simp.utilitario.DatabaseUtil;
import ob.debitos.simp.utilitario.DatesUtils;

@Service
public class TmpLoaderIncService implements ITmpLoaderIncService {
	private @Autowired Logger logger;
	private @Autowired DataSource dataSource;
	
	@Override
	public void uploadFileBatch(String filePath, int size, int idInstitucion){
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
	
	@Override
	public void uploadFileBySize(String filePath, int size, int idInstitucion) {
		logger.info("Carga de archivo por tamanio de registro {}", filePath);
		try (Connection conn = dataSource.getConnection()) {
			int batchSize = 10000;
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO TmpLoaderInc(nNumeroLinea, vLinea, vIdItem, nIdInstitucion) VALUES (?,?,?,?)");) {
					try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
						int nline = (int) file.length() / size;
						byte[] bytes = new byte[size];
						for (int nb = 0; nb < nline;) {
							file.seek(nb * (long) size);
							file.read(bytes);
							String reg = new String(bytes);
							stmt.setInt(1, nb + 1 );
							stmt.setString(2, reg);
							stmt.setString(3, reg.charAt(0) + "");
							stmt.setInt(4, idInstitucion);
							stmt.addBatch();
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
	
	@Override
	public void uploadFileByLine(String filePath, int idInstitucion) {
		try (Connection conn = dataSource.getConnection()) {
			int batchSize = 10000;
			conn.setAutoCommit(false);
			try (PreparedStatement stmt = conn
					.prepareStatement("INSERT INTO TmpLoaderInc(nNumeroLinea, vLinea, vIdItem, nIdInstitucion) VALUES (?,?,?,?)");) {
				logger.info("Lee por linea");
				try (BufferedReader br = new BufferedReader(new FileReader(filePath));) {
					List<String> lines = br.lines().collect(Collectors.toList());
					int nline = lines.size();
					int[] idx = { 0 };
					lines.stream().forEach(reg -> {
						try {
							stmt.setInt(1, idx[0]+1 );
							stmt.setString(2, reg);
							stmt.setString(3, reg.charAt(0) + "");
							stmt.setInt(4, idInstitucion);
							stmt.addBatch();
							idx[0]++;
							if (idx[0] % batchSize == 0 || idx[0] == nline) {
								stmt.executeBatch();
								conn.commit();
							}
						} catch (SQLException e) {
							logger.error(e.getMessage(), e);
							DatabaseUtil.rollback(conn);
						}
					});
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage(), e);
					throw new EjecucionManualException(
							String.format(ConstantesExcepciones.ERROR_LECTURA_ARCHIVO, filePath));
				} catch (IOException e1) {
					logger.error(e1.getMessage(), e1);
					throw new EjecucionManualException(
							String.format(ConstantesExcepciones.ERROR_LECTURA_ARCHIVO, filePath));
				}
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void eliminarCargaTemporal() {
		LocalDateTime inicio = LocalDateTime.now();
		LocalDateTime fin;
		logger.info("@I Truncamiento de TmpLoaderInc");
		try (Connection conn = dataSource.getConnection();) {
			try (PreparedStatement stmt = conn
					.prepareStatement("DELETE TmpLoaderInc");) {
				stmt.executeUpdate();
				conn.commit();
				logger.info("El truncamiento de la TmpLoaderInc se ha realizado.");
			} catch (SQLException e) {
				logger.error(e.getMessage(), e);
				DatabaseUtil.rollback(conn);
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("El truncamiento de la TmpLoaderInc ha finalizado.");
		fin = LocalDateTime.now();
		logger.info("@F Tiempo de Ejecución : {}", DatesUtils.tiempoEntre(inicio, fin));
	}

}
