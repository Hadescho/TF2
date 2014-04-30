package services;

import java.sql.SQLException;
import java.util.List;
import java.math.BigInteger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.core.SecurityContext;


import org.elsys.TF2Checker.models.DBUser;
import org.elsys.TF2Checker.models.User;

public class UserService {
	private static UserService INSTANCE;
	private EntityManagerFactory emf;

	protected UserService() throws SQLException {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		System.out.println("---MyApp--- UserService constructor");
		emf = createEMF();
	}

	public static UserService getInstance() throws SQLException {
		if (INSTANCE == null) {
			INSTANCE = new UserService();
		}
		return INSTANCE;
	}

	private EntityManagerFactory createEMF() throws SQLException {
		EntityManagerFactory result = Persistence
				.createEntityManagerFactory("TF2Checker");
		initTables(result);
		return result;
	}

	private void initTables(EntityManagerFactory result) throws SQLException {
		EntityManager em = result.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			User myUser = new User("test", "test", 1);
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		em.close();
	}

	public User getUser(String email) {
		EntityManager em = emf.createEntityManager();
		try {
			Query qu = em
					.createNativeQuery(
							"SELECT id, id64, fetchDate, value FROM User1 WHERE email=?",
							User.class);
			qu.setParameter(1, email);
			return (User) qu.getResultList().get(0);
		} finally {
			em.close();
		}
	}

	public void createUser(User myUser) {
		EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();
			em.persist(myUser);
			tx.commit();
		} finally {
			if (tx.isActive()) {
				tx.rollback();
			}
		}
		em.close();
	}

	public long getUserId64(SecurityContext username) {
		System.out.println("---MyApp--- Enters getUserId64");
		EntityManager em = emf.createEntityManager();
		Query qu;
		try {
			qu = em.createNativeQuery("SELECT id64 FROM USER1 WHERE email=?");
			qu.setParameter(1, username.getUserPrincipal().getName());
			BigInteger BIresult = ((BigInteger) qu.getResultList().get(0));
			long result = BIresult.longValue();
			System.out.println("---MyApp--- Result of getUserId64: " + result );
			return result;
		} finally {
			em.close();
		}
	}
	public void updateUserId64(SecurityContext username, long newId64) {
		System.out.println("---MyApp--- Enters updateUserId64");
		System.out.println("---MyApp--- newID = " + newId64);
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Query qu;
		try {
			tx.begin();
			qu = em.createNativeQuery("UPDATE USER1 SET id64=? WHERE email=?");
			qu.setParameter(1, newId64);
			qu.setParameter(2, username.getUserPrincipal().getName());
			int count = qu.executeUpdate();
			tx.commit();
			if (count == 1){
				System.out.println("---MyApp--- User's ID64 should be updated");
			}else
			{
				System.out.println("---MyApp--- Something went terrily wrong while updating the Id64 of " + username.getUserPrincipal().getName());
			}
		} finally {
			em.close();
		}
		
	}
}