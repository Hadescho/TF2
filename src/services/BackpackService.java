package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.elsys.TF2Checker.models.DBUser;

public class BackpackService {
	private static BackpackService INSTANCE;
	private EntityManagerFactory emf;
	
	protected BackpackService(){
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		emf = createEMF();
	}

	private EntityManagerFactory createEMF() {
		EntityManagerFactory result = Persistence.createEntityManagerFactory("TF2Checker");
		initTables(result);
		return result;
	}

	private void initTables(EntityManagerFactory result) {
		EntityManager em = result.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			DBUser dbUser = new DBUser(-1,-100.00f);
			
		}
		finally{
			if(tx.isActive())
			{
				tx.rollback();
			}
		}
		em.close();
	}
	
	public List<DBUser> getBackpackValues(long id64) throws SQLException{
//	//	EntityManager em = emf.createEntityManager();
//		
////		try{
////			return em.createNamedQuery("byBackpackId",DBUser.class).setParameter("id64", id64).getResultList();
////		}finally{
////			em.close();
////		}
//		List<DBUser> retList = new ArrayList<DBUser>();
//		Connection con = null;
//		try{
//		con = DriverManager.getConnection(
//			 "jdbc:derby:E:\\Developer\\eclipse-jee-kepler-SR1-win32-x86_64\\eclipse\\tf2checker");
//			}
//		catch(java.sql.SQLException ex){
//				
//			System.out.println(ex.getMessage() +"/n/n " + ex.getStackTrace());
//			return null;
//		}
//		String parsedid64 = String.valueOf(id64);
//		
//		parsedid64 = parsedid64.replaceAll("[^\\d.]", "");
//		System.out.println("\n\n-------------------" + parsedid64 + "---------------------\n\n");
//		Statement stmt = con.createStatement();
//		ResultSet rs = stmt.executeQuery
//		 ("SELECT * FROM DBUser WHERE DBUser.id64=" + id64 );
//		while (rs.next()) {
//			long rid64 = rs.getLong("id64");
//			float val = rs.getFloat("value");
//			Date fetchdate = rs.getDate("fetchDate");
//			//System.out.println(id64 + " | " + val + " | " + fetchdate);
//			retList.add(new DBUser(rid64,val,fetchdate));
//			
//		}
//		return retList;
		
		EntityManager em = emf.createEntityManager();
		try{
			Query qu = em.createNativeQuery("SELECT id, id64, fetchDate, value FROM DBuser WHERE id64=?",DBUser.class);
			qu.setParameter(1, id64);
			return qu.getResultList();
		}
		finally{
			em.close();
		}
	}
	
	public static BackpackService getInstance(){
		if(INSTANCE == null){
			INSTANCE = new BackpackService();
		}
		return INSTANCE;
	}
	
	public void createBackpackValue(DBUser bpval){
		EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try{
			tx.begin();
			em.persist(bpval);
			tx.commit();
		}finally{
			if(tx.isActive())
			{
				tx.rollback();
			}
		}
		em.close();
	}
}

