package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.Account;
import model.Client;
import model.Movements;
import model.Transfer;

public class EmployeeRepositoryImpl implements EmployeeRepository{

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
	public Client clientInfoAccounts(int idCliente) {
		Client client=null;
		try(Connection cn=ds.getConnection();){
    	   String sql="Select c.dni, c.nombre, c.direccion, ac.numeroCuenta, ac.saldo, ac.tipocuenta from clientes c JOIN titulares t ON c.dni=t.idCliente "
    	   		+ "JOIN cuentas ac ON t.idCuenta=ac.numeroCuenta WHERE c.dni=?";
    	   PreparedStatement ps=cn.prepareStatement(sql);
	       ps.setInt(1, idCliente);
	       ResultSet rs=ps.executeQuery();
    	   List<Account> accountList=new ArrayList<>();

	       while(rs.next()) {
	    	   if (client == null) {
	                // Aquí se crea el objeto Client solo una vez si aún no existe
	    		   client= new Client();
	    		   client.setDni(rs.getInt("dni"));
	    		   client.setName(rs.getString("nombre"));
	    		   client.setAddress(rs.getString("direccion"));
	            }
	            // Crear y agregar cuentas a la lista
	            Account account = new Account(
	                rs.getInt("numeroCuenta"),
	                rs.getDouble("saldo"),
	                rs.getString("tipocuenta"));
	            accountList.add(account);
	        }
	        
	        if (client != null) {
	            client.setAccounts(accountList); // Asumiendo que Client tiene una lista de cuentas
	        }
		  return client;
       }catch(SQLException ex) {
             ex.printStackTrace();    	   
       }
		return client;
	}
	
	@Override
	public double obtenerSaldoTotal(int idCliente) {
		double balance=0;
       try(Connection cn=ds.getConnection()) {
    	   String sql="Select SUM(ac.saldo) as saldo_total from clientes c "
    	   		+ "JOIN titulares t ON c.dni=t.idCliente JOIN cuentas ac ON t.idCuenta=ac.numeroCuenta WHERE "
    	   		+ "c.dni=?";
    	   PreparedStatement ps=cn.prepareStatement(sql);
    	   ps.setInt(1, idCliente);
    	   ResultSet rs=ps.executeQuery();
    	   while(rs.next()) {
    		   balance=rs.getDouble("saldo");
    	   }
       }catch(SQLException ex) {
           ex.printStackTrace();
       }
		return balance;
	}
	@Override
	public List<Movements> obtenerMovimientosPorCuenta(int idAccount) {
		List<Movements>listMovs=new ArrayList<>();
		 try(Connection cn=ds.getConnection()) {
			String sql= "Select idMovimiento, idCuenta, fecha, cantidad, operacion from movimientos where idCuenta=?";
				PreparedStatement ps=cn.prepareStatement(sql);
			       ps.setInt(1, idAccount);
	    	   ResultSet rs=ps.executeQuery();
	    	   while(rs.next()) {
	    		listMovs.add( new Movements(rs.getInt("idMovimiento"),
				           rs.getInt("idCuenta"),
				           rs.getTimestamp("fecha").toLocalDateTime().toLocalDate(),
				           rs.getDouble("cantidad"),
				           rs.getString("operacion")));
	    	   }
	       }catch(SQLException ex) {
	           ex.printStackTrace();
	       }
			return listMovs;
	}
	
	@Override
	public List<Account> obtenerCuentasConSaldoMayor(double saldoMinimo) {
		List<Account>listAccounts=new ArrayList<>();
	 try(Connection cn=ds.getConnection()) {
         String sql="SELECT numeroCuenta, saldo, tipocuenta FROM cuentas WHERE saldo > ?";
         PreparedStatement ps=cn.prepareStatement(sql);
	       ps.setDouble(1, saldoMinimo);
	       ResultSet rs=ps.executeQuery();
    	   while(rs.next()) {
    		   listAccounts.add( new Account(rs.getInt("numeroCuenta"),
			           rs.getDouble("saldo"),
			           rs.getString("tipocuenta")));
			         
    	   }
	    }catch(SQLException ex) {
	           ex.printStackTrace();
	       }
		return listAccounts;
	}
	@Override
	public List<Movements> movementsOfClient(int idCliente) {
		List<Movements>listMovs=new ArrayList<>();
		 try(Connection cn=ds.getConnection()) {
			String sql= "Select m.idCuenta, m.fecha, m.cantidad, m.operacion from clientes c join titulares t on c.dni=t.idCliente"
					+ " join cuentas ac on t.idCuenta=ac.numeroCuenta join movimientos m on ac.numeroCuenta=m.idCuenta where dni=?";
				PreparedStatement ps=cn.prepareStatement(sql);
			       ps.setInt(1, idCliente);
	    	   ResultSet rs=ps.executeQuery();
	    	   while(rs.next()) {
	    		listMovs.add( new Movements(rs.getInt("idMovimiento"),
				           rs.getInt("idCuenta"),
				           rs.getTimestamp("fecha").toLocalDateTime().toLocalDate(),
				           rs.getDouble("cantidad"),
				           rs.getString("operacion")));
	    	   }
	       }catch(SQLException ex) {
	           ex.printStackTrace();
	       }
			return listMovs;
	}
	@Override
	public List<Movements> obtenerMovimientosPorClienteEnRango(int idCliente, LocalDate from, LocalDate to) {
		List<Movements> listAll=new ArrayList<>();
		try(Connection cn=ds.getConnection();){
		String sql= "Select m.idMovimiento, m.idCuenta, m.fecha, m.cantidad, m.operacion from clientes c join titulares t on c.dni=t.idCliente"
				+ " join cuentas ac on t.idCuenta=ac.numeroCuenta join movimientos m on ac.numeroCuenta=m.idCuenta where dni=? "
				+ "and fecha between ? and ?";
		PreparedStatement ps=cn.prepareStatement(sql);
	       ps.setInt(1, idCliente);
	       ps.setTimestamp(2, Timestamp.valueOf(from.atStartOfDay()));  // Para la fecha de inicio
	       ps.setTimestamp(3, Timestamp.valueOf(to.atStartOfDay()));
	       ResultSet rs=ps.executeQuery();
    	   while(rs.next()) {
    		   listAll.add( new Movements(rs.getInt("idMovimiento"),
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
	public List<Client> obtenerClientesConSaldosNegativos() {
		List<Client> listClients=new ArrayList<>();
		try(Connection cn=ds.getConnection();){
			String sql="Select c.dni, c.nombre, ac.saldo from clientes c JOIN titulares t ON c.dni=t.idCliente "
	    	   		+ "JOIN cuentas ac ON t.idCuenta=ac.numeroCuenta WHERE ac.saldo < 0";
			   PreparedStatement ps=cn.prepareStatement(sql);
		       ResultSet rs=ps.executeQuery();
	    	   List<Account> accountList=new ArrayList<>();
               while(rs.next()) {
		    	   Client c= new Client();
		    	        c.setDni(rs.getInt("dni"));
		    		    c.setName(rs.getString("nombre"));
		    			c.setAddress(rs.getString("direccion"));
		    			c.setPhone(rs.getInt("telefono"));		
					 listClients.add(c);
		       }
	       }catch(SQLException ex) {
	           ex.printStackTrace();
	       }
			return listClients;
	}
	@Override
	public Map<String, Double> obtenerTotalSaldosPorTipoDeCuenta() {
		Map<String,Double> listClients=new HashMap<>();
		try(Connection cn=ds.getConnection();){
			String sql="SELECT tipocuenta, SUM(saldo) as balance_all from cuentas GROUP BY tipocuenta";
			PreparedStatement ps=cn.prepareStatement(sql);
		       ResultSet rs=ps.executeQuery();
			      while(rs.next()) {
			    	   listClients.put(rs.getString("tipocuenta"), rs.getDouble("balance_all"));
			       }
		       }catch(SQLException ex) {
		           ex.printStackTrace();
		       }
				return listClients; 
				
	}
	@Override
	public Map<Integer,Client> obtenerClientesSinMovimientosDesde(LocalDate fecha) {
		Map<Integer,Client> clientMap=new HashMap<>();
		 try(Connection cn=ds.getConnection()) {
				String sql= "Select c.dni, c.nombre, c.direccion, c.telefono, ac.numeroCuenta, ac.saldo, ac.tipocuenta from clientes c join titulares t on c.dni=t.idCliente"
						+ " join cuentas ac on t.idCuenta=ac.numeroCuenta left join movimientos m on ac.numeroCuenta=m.idCuenta WHERE (m.fecha IS NULL OR m.fecha < ?) GROUP BY c.dni, c.nombre, c.telefono"
						+ "";
				PreparedStatement ps=cn.prepareStatement(sql);
			       ps.setTimestamp(1, Timestamp.valueOf(fecha.atStartOfDay()));  // Para la fecha de inicio
			       ResultSet rs=ps.executeQuery();
		    	   while(rs.next()) {
		    		  int dni=rs.getInt("dni");
	    		      if(!clientMap.containsKey(dni)) {
	    		    	  Client c=new Client();
	    		           c.setName(rs.getString("nombre"));
	    		           c.setAddress(rs.getString("direccion"));
	    		           c.setPhone(rs.getInt("telefono"));
		    	            c.setAccounts(new ArrayList<>());

	    		           clientMap.put(dni, c);
	    		      }
	    	          Client clientAccount=clientMap.get(dni);

	    		       // Obtener las cuentas como un String
	    	            Account account=new Account();
	    	               account.setIdAccount(rs.getInt("numeroCuenta"));
	    	               account.setBalance(rs.getDouble("saldo"));
	    	               account.setTypeAccount(rs.getString("tipocuenta"));
	    	            clientAccount.getAccounts().add(account);
		    	   }return clientMap;
		    	  
		       }catch(SQLException ex) {
		           ex.printStackTrace();
	}return clientMap;
	}
	@Override  // los ultimos 6 meses
	public List<Client> obtenerClientesSinMovimientosUltimosMeses(int meses) {
	/*	List<Client> clientMovsNull=new ArrayList<>();
		 try(Connection cn=ds.getConnection()) {
			 String sql = "SELECT c.dni, c.nombre, c.direccion, c.telefono
				        FROM clientes c
				        JOIN titulares t ON c.dni = t.idCliente
				        JOIN cuentas ac ON t.idCuenta = ac.numeroCuenta
				        LEFT JOIN movimientos m ON ac.numeroCuenta = m.idCuenta
				        WHERE (m.fecha IS NULL OR m.fecha < DATE_SUB(CURDATE(), INTERVAL ? MONTH))"
				        */
		return null;
	}
	
			 		
	@Override
	public List<Double> obtenerHistorialDeSaldosPorCuenta(int idCuenta, LocalDate desde, LocalDate hasta) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Integer> obtenerInformeDeMovimientosPorTipo(int idCuenta, YearMonth mes) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Client> obtenerClientesConSaldoPromedioMasAlto(int anio) {
		List<Client> clientBalanceHight=new ArrayList<>();
		 try(Connection cn=ds.getConnection();) {
		 String sql="SELECT c.dni, c.nombre, AVG(ac.saldo) AS saldo_promedio from clientes c JOIN titulares t ON c.dni=t.idCliente JOIN cuentas ac "
		 		+ "ON t.idCuenta=ac.numeroCuenta JOIN movimientos m ON ac.numeroCuenta=m.idCuenta WHERE YEAR(m.fecha)=? GROUP BY c.dni, c.nombre "
		 		+ "ORDER BY saldo_promedio DESC";
		 PreparedStatement ps=cn.prepareStatement(sql);
	       ps.setInt(1,anio);  // Para la fecha de inicio
	       ResultSet rs=ps.executeQuery();	
	       while(rs.next()) {
	    		  Client c=new Client();
 		       c.setDni(rs.getInt("dni"));
 		       c.setName(rs.getString("nombre"));
 		      clientBalanceHight.add(c);
	    	   }
		 }catch(SQLException ex) {
	           ex.printStackTrace();
	       }
			return clientBalanceHight;
	}
	@Override
	public boolean registrarCliente(Client cliente) {
		 try(Connection cn=ds.getConnection();) {
		 String sql="insert into clientes (dni, nombre, direccion, telefono) VALUES (?,?,?,?)";	
		 
		 PreparedStatement ps=cn.prepareStatement(sql);
		 ps.setInt(1, cliente.getDni());
		 ps.setString(2, cliente.getName());
		 ps.setString(3, cliente.getAddress());
		 ps.setInt(4, cliente.getPhone());
		 int rowsAffected = ps.executeUpdate();  // El método executeUpdate devuelve el número de filas afectadas
	       // Si se ha actualizado al menos una fila, el ingreso fue exitoso
	       return rowsAffected > 0;	
		 }catch(SQLException ex) {
	           ex.printStackTrace();
	       }
		 
		return false;
	}
	@Override
	public boolean registrarCuenta(int idCliente, Account cuenta) {
		 try(Connection cn=ds.getConnection();) {
		        // Paso 1: Insertar la cuenta
             String sql="insert into cuentas (numeroCuenta, saldo, tipocuenta, estado) VALUES (?,?,?,?)";	
			 PreparedStatement ps=cn.prepareStatement(sql);
			 ps.setInt(1, cuenta.getIdAccount());
			 ps.setDouble(2, cuenta.getBalance());
			 ps.setString(3, cuenta.getTypeAccount());
		     ps.setString(4, cuenta.getStatus().name()); // Convertir enum a texto
			 int rowsAccount = ps.executeUpdate();  // El método executeUpdate devuelve el número de filas afectadas
		       // Si se ha actualizado al menos una fila, el ingreso fue exitoso
		        // Paso 2: Relacionar la cuenta con el cliente en la tabla titulares
                if( rowsAccount > 0) {
		    	   String sql2="INSERT INTO titulares (idCliente, idCuenta) VALUES (?,?)";
		           PreparedStatement ps2=cn.prepareStatement(sql2);
		           ps2.setInt(1, idCliente);
		           ps2.setInt(2, cuenta.getIdAccount());
		           int rowsTitulares= ps2.executeUpdate();
		           return rowsTitulares>0;
		       }
			 }catch(SQLException ex) {
		           ex.printStackTrace();
		       }
			 
			return false;
	}
	@Override
	public boolean actualizarDatosCliente(Client cliente) {
		 try(Connection cn=ds.getConnection();) {
			 String sql="UPDATE clientes SET dni=?, nombre=?, direccion=?, telefono=?";	
			 
			 PreparedStatement ps=cn.prepareStatement(sql);
			 ps.setInt(1, cliente.getDni());
			 ps.setString(2, cliente.getName());
			 ps.setString(3, cliente.getAddress());
			 ps.setInt(4, cliente.getPhone());
			 int rowsAffected = ps.executeUpdate();  // El método executeUpdate devuelve el número de filas afectadas
		       return rowsAffected>0;
		  
	     }catch(SQLException ex) {
          ex.printStackTrace();
      }
	 
	return false;	
	}
	
	public boolean bloquearCuenta(int idCuenta) {
		
          String sql = "UPDATE cuentas SET estado = 'BLOQUEADA' WHERE numeroCuenta = ?";
	        // Iniciar transacción
          try (Connection cn = ds.getConnection();
        	         PreparedStatement ps = cn.prepareStatement(sql)) {
        	        cn.setAutoCommit(false);  // Desactivar autocommit para la transacción

        	        ps.setInt(1, idCuenta);
        	        int rowsAffected = ps.executeUpdate();

        	        if (rowsAffected > 0) {
        	            cn.commit();  // Confirmar la transacción
        	            return true;
        	        } else {
        	            cn.rollback();  // Deshacer la transacción si no se afectaron filas
        	            return false;
        	        }
          } catch (SQLException ex) {
        	        ex.printStackTrace();  // Manejo de excepciones
        	        return false;
          }
	}

	public boolean desbloquearCuenta(int idCuenta) {
		
        String sql = "UPDATE cuentas SET estado = 'ACTIVA' WHERE numeroCuenta = ?";
	        // Iniciar transacción
        try (Connection cn = ds.getConnection();
      	         PreparedStatement ps = cn.prepareStatement(sql)) {
      	        cn.setAutoCommit(false);  // Desactivar autocommit para la transacción

      	        ps.setInt(1, idCuenta);
      	        int rowsAffected = ps.executeUpdate();

      	        if (rowsAffected > 0) {
      	            cn.commit();  // Confirmar la transacción
      	            return true;
      	        } else {
      	            cn.rollback();  // Deshacer la transacción si no se afectaron filas
      	            return false;
      	        }
        } catch (SQLException ex) {
      	        ex.printStackTrace();  // Manejo de excepciones
      	        return false;
        }
	}

	@Override
	public Client clientByDni(int dni) {
		Client c=null;
			 try(Connection cn=ds.getConnection();) {
		        // Paso 1: Insertar la cuenta
          String sql="SELECT dni, nombre, direccion, telefono FROM clientes WHERE dni=?";	
			 PreparedStatement ps=cn.prepareStatement(sql);
			 ps.setInt(1, dni);
	
		     ResultSet rs=ps.executeQuery();	
		       while(rs.next()) {
		    	   c=new Client();
	 		       c.setDni(rs.getInt("dni"));
	 		       c.setName(rs.getString("nombre"));
	 		       c.setName(rs.getString("direccion"));
	 		       c.setName(rs.getString("telefono"));
		       }
			 }catch (SQLException ex) {
	      	        ex.printStackTrace(); 
	        }
	         return c;
}

	@Override
	public Account accountByNumber(int numberAcount) {
		Account c=null;
		 try(Connection cn=ds.getConnection();) {
	        // Paso 1: Insertar la cuenta
     String sql="SELECT numeroCuenta, saldo, tipocuenta FROM cuentas WHERE numeroCuenta=?";	
		 PreparedStatement ps=cn.prepareStatement(sql);
		 ps.setInt(1, numberAcount);

	     ResultSet rs=ps.executeQuery();	
	       while(rs.next()) {
	    	   c=new Account();
		       c.setIdAccount(numberAcount);
		       c.setBalance(numberAcount);
		       c.setTypeAccount(sql);
	       }
		 }catch (SQLException ex) {
     	        ex.printStackTrace(); 
       }
        return c;
        }

	@Override
	public Transfer transferById(int id) {
		Transfer t=null;
		 try(Connection cn=ds.getConnection();) {
	        // Paso 1: Insertar la cuenta
    String sql="SELECT idTransferencia, cuentaOrigen, cuentaDestino, cantidad, fecha, estado FROM transferencias WHERE idTransferencia=?";	
		 PreparedStatement ps=cn.prepareStatement(sql);
		 ps.setInt(1, id);

	     ResultSet rs=ps.executeQuery();	
	       while(rs.next()) {
	    	   t=new Transfer();
		       t.setIdTransferencia(rs.getInt("idTransferencia"));
		       t.setCuentaOrigen(rs.getInt("cuentaOrigen"));
		       t.setCuentaDestino(rs.getInt("cuentaDestino"));
		       t.setCantidad(rs.getDouble("cantidad"));
	           t.setFecha(rs.getTimestamp("fecha").toLocalDateTime().toLocalDate());

		       t.setEstado(rs.getString("estado"));
	       }
		 }catch (SQLException ex) {
    	        ex.printStackTrace(); 
      }
       return t;
	}

	@Override
	public boolean updateTransferById(Transfer t) {
			 try(Connection cn=ds.getConnection();) {
				 String sql="UPDATE transferencias SET cuentaOrigen=?, cuentaDestino=?, cantidad=?, fecha=?, estado=?";	
		   	 PreparedStatement ps=cn.prepareStatement(sql);
			  ps.setInt(1, t.getCuentaOrigen());
			  ps.setInt(2, t.getCuentaDestino());
			  ps.setDouble(3, t.getCantidad());
			  ps.setTimestamp(4, Timestamp.valueOf(t.getFecha().atStartOfDay())); // Convierte LocalDate a LocalDateTime
              ps.setString(5, t.getEstado());
	        // Ejecutar la consulta
		     int rowsAffected = ps.executeUpdate(); // Devuelve el número de filas afectadas
		      return rowsAffected > 0; // Retorna true si se actualizó al menos una fila
			 } catch (SQLException ex) {
			        ex.printStackTrace();
		     }
			  return false; //			
	}

	@Override
	public void actualizarSaldo(int numeroCuenta, double cantidad) {
		 String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE numeroCuenta = ? AND estado = 'ACTIVA'";
		    try (Connection cn = ds.getConnection();
		         PreparedStatement ps = cn.prepareStatement(sql)) {
                ps.setDouble(1, cantidad);
                ps.setInt(2, numeroCuenta);
		        ps.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}
}
/*
 * Map<Integer, Client> clientesMap = new HashMap<>();
while (rs.next()) {
    int dni = rs.getInt("dni");

    // Si el cliente no está en el mapa, lo agregamos
    if (!clientesMap.containsKey(dni)) {
        Client c = new Client();
        c.setDni(dni);
        c.setName(rs.getString("nombre"));
        c.setPhone(rs.getInt("telefono"));
        c.setAccounts(new ArrayList<>()); // Inicializar lista vacía
        clientesMap.put(dni, c);
    }

    // Agregar la cuenta a la lista de cuentas del cliente
    Client cliente = clientesMap.get(dni);
    Account account = new Account();
    account.setIdAccount(rs.getInt("numeroCuenta"));
    cliente.getAccounts().add(account);
}

// Convertimos el mapa a una lista
List<Client> clientMovsNull = new ArrayList<>(clientesMap.values());
*/
