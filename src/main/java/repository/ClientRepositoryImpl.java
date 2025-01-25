package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Account;
import model.Movements;

public class ClientRepositoryImpl implements ClientRepository {
	
	static DataSource ds;
	static{
    	try {
			Context ctx=new InitialContext();
			ds=(DataSource)ctx.lookup("java:comp/env/refbanca");
		} catch (NamingException e) {			
			e.printStackTrace();
		}        
    }
	@Override
	public Optional<Double> checkBalanceByAccount(int idAccount) {
		double result=0;
        try(Connection cn=ds.getConnection();) {                                   
			String sql="Select saldo from cuentas where numeroCuenta=?";
	       // Preparamos el statement con la consulta SQL
	        PreparedStatement ps = cn.prepareStatement(sql);
	       // Establecemos el valor del parámetro
	        ps.setInt(1, idAccount);
	       // Ejecutamos la consulta
	        ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				 result=rs.getDouble("saldo");
			}
		} catch (SQLException ex) {
            ex.printStackTrace();
		}
        return result > 0 ? Optional.of(result) : Optional.empty();
	}

	@Override
	public boolean income(double quantity, int idAccount) {
        try(Connection cn=ds.getConnection();) {                                   
            String sql="UPDATE cuentas SET saldo = saldo + ? WHERE numeroCuenta=? AND estado='ACTIVA'";
            PreparedStatement ps=cn.prepareStatement(sql);
	        ps.setDouble(1, quantity);
	        ps.setInt(2, idAccount);
	        // Ejecutamos la actualización
	        int rowsAffected = ps.executeUpdate();  // El método executeUpdate devuelve el número de filas afectadas
	        // Si se ha actualizado al menos una fila, el ingreso fue exitoso
	        return rowsAffected > 0;			
        } catch (SQLException ex) {
            ex.printStackTrace();
		}
       
		return false;
	}

	@Override
	public boolean withdrawal(double quantity, int idAccount) {
      try(Connection cn=ds.getConnection();) { 
     //no es recomendable por seguridad
     String sql = "UPDATE cuentas SET saldo = saldo - " + quantity + " WHERE numeroCuenta = '" + idAccount + "'AND estado='ACTIVA'";
     PreparedStatement ps=cn.prepareStatement(sql);
       ps.setDouble(1, quantity);
       ps.setInt(2, idAccount);
       // Ejecutamos la actualización
       int rowsAffected = ps.executeUpdate();  // El método executeUpdate devuelve el número de filas afectadas
       // Si se ha actualizado al menos una fila, el ingreso fue exitoso
       return rowsAffected > 0;		
        } catch (SQLException ex) {
            ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean transfer(int idAccountStart, int idAccountEnd, double quantity) {
		 Connection cn = null;  // Declarar Connection fuera del try
		    boolean exito = false;  // Para devolver el resultado de la operación
		    try {
		        cn = ds.getConnection();  // Obtener la conexión
		        cn.setAutoCommit(false);   // Desactivar autocommit para la transacción

		        String sql="INSERT INTO transferencias (cuentaOrigen, cuentaDestino, cantidad, fecha, estado) VALUES (?,?,?,NOW(),?)";
		        PreparedStatement psTransfer = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		        psTransfer.setInt(1, idAccountStart);
		        psTransfer.setInt(2, idAccountEnd);
		        psTransfer.setDouble(3, quantity);
		        psTransfer.setString(4, "COMPLETADA");
		        psTransfer.executeUpdate();
		        // Obtener el `idTransferencia` generado
		        ResultSet rs = psTransfer.getGeneratedKeys();
		        int idTransferencia = -1;
		        if (rs.next()) {
		            idTransferencia = rs.getInt(1);
		        }
		        rs.close();
		        psTransfer.close();

		        if (idTransferencia == -1) {
		            throw new SQLException("Error al generar idTransferencia");
		        }
		        
		        String sql1 = "UPDATE cuentas SET saldo = saldo - ? WHERE numeroCuenta = ? AND estado='ACTIVA'";
		        String sql2 = "UPDATE cuentas SET saldo = saldo + ? WHERE numeroCuenta = ? AND estado='ACTIVA'";
		        String sql3 = "INSERT INTO movimientos (idCuenta, fecha, cantidad, operacion, idTransferencia) VALUES (?, NOW(), ?, ?, ?)";

		        // Actualizar cuenta origen
		        try (PreparedStatement ps1 = cn.prepareStatement(sql1)) {
		            ps1.setDouble(1, quantity);
		            ps1.setInt(2, idAccountStart);
		            ps1.executeUpdate();
		        }

		        // Actualizar cuenta destino
		        try (PreparedStatement ps2 = cn.prepareStatement(sql2)) {
		            ps2.setDouble(1, quantity);
		            ps2.setInt(2, idAccountEnd);
		            ps2.executeUpdate();
		        }

		        // Registrar movimiento de la cuenta origen (Extracción)
		        try (PreparedStatement ps3 = cn.prepareStatement(sql3)) {
		            ps3.setInt(1, idAccountStart);
		            ps3.setDouble(2, quantity);
		            ps3.setString(3, "Extracción");
		            ps3.setInt(4, idTransferencia);
		            ps3.executeUpdate();
		        }

		        // Registrar movimiento de la cuenta destino (Ingreso)
		        try (PreparedStatement ps4 = cn.prepareStatement(sql3)) {
		            ps4.setInt(1, idAccountEnd);
		            ps4.setDouble(2, quantity);
		            ps4.setString(3, "Ingreso");
		            ps4.setInt(4, idTransferencia);

		            ps4.executeUpdate();
		        }

		        // Si todo fue exitoso, hacer commit
		        cn.commit();
		        exito = true;
		    } catch (SQLException e) {
		        e.printStackTrace();
		        if (cn != null) {
		            try {
		                // Si ocurre un error, deshacer todo (rollback)
		                cn.rollback();
		            } catch (SQLException rollbackEx) {
		                rollbackEx.printStackTrace();
		            }
		        }
		    } finally {
		        try {
		            if (cn != null) {
		                // Restaurar el autoCommit al valor predeterminado
		                cn.setAutoCommit(true);
		                cn.close();  // Cerrar la conexión
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		    }
		    return exito;
		
	}

	@Override
	public List<Movements> checkMovements(int idAccount, LocalDate from, LocalDate to) {
		List<Movements> listAll=new ArrayList<>();
		try(Connection cn=ds.getConnection();){
		String sql="Select idMovimiento, idCuenta, fecha, cantidad, operacion from movimientos where idCuenta=? and fecha >= ? and fecha <= ?";
		PreparedStatement ps=cn.prepareStatement(sql);
	       ps.setInt(1, idAccount);
	    // Convertir LocalDate a LocalDateTime con hora 00:00:00
	        ps.setTimestamp(2, Timestamp.valueOf(from.atStartOfDay()));  // Para la fecha de inicio
	        ps.setTimestamp(3, Timestamp.valueOf(to.atStartOfDay()));  // Para la fecha de fin
		 ResultSet rs=ps.executeQuery();
		 while(rs.next()) {
			 listAll.add(new Movements(rs.getInt("idMovimiento"),
					           rs.getInt("idCuenta"),
					           rs.getTimestamp("fecha").toLocalDateTime().toLocalDate(),
					           rs.getDouble("cantidad"),
					           rs.getString("operacion")));
		 }
		}catch(SQLException ex) {
            ex.printStackTrace();
		}
		
		return listAll;
	}

	@Override
	public Account accountById(int idAccount) {
		Account account=null;
		try(Connection cn=ds.getConnection();) {                                   
			String sql="Select numeroCuenta, saldo, tipocuenta from cuentas where numeroCuenta=?";
	       // Preparamos el statement con la consulta SQL
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, idAccount);  // Establecer el parámetro en la consulta
			ResultSet rs = ps.executeQuery();
		       if (rs.next()) {
		                // Crear objeto Account con los valores obtenidos
		               account = new Account(
		                    rs.getInt("numeroCuenta"),
		                    rs.getDouble("saldo"),
		                    rs.getString("tipocuenta"));
		            }
		}catch(SQLException ex) {
            ex.printStackTrace();
		}
		
	          return account;

}

	@Override
	public void saveMovimiento(Movements movs) {
		//Account account=null;
		try(Connection cn=ds.getConnection();) {                                   
			String sql="INSERT INTO movimientos (idCuenta, fecha, cantidad, operacion) VALUES (?,?,?,?)";
	       // Preparamos el statement con la consulta SQL
			PreparedStatement ps = cn.prepareStatement(sql);
			ps.setInt(1, movs.getIdAccount());  // Establecer el parámetro en la consulta
	        ps.setTimestamp(2, Timestamp.valueOf(movs.getDateMovement().atStartOfDay()));  // Para la fecha de inicio
			ps.setDouble(3, movs.getQuantity());  // Establecer el parámetro en la consulta
			ps.setString(4, movs.getOperation());  // Establecer el parámetro en la consulta
			ps.executeUpdate();
		      
		}catch(SQLException ex) {
            ex.printStackTrace();
		}
		
		
	}
}
