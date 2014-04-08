package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.elsys.TF2Checker.models.DBUser;
import org.elsys.TF2Checker.models.User;

public class UserService {
	private static UserService INSTANCE;
	private EntityManagerFactory emf;
	
	protected UserService(){
		try{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		emf = createEMF();
	}
	
	public static UserService getInstance(){
		if(INSTANCE == null){
			INSTANCE = new UserService();
		}
		return INSTANCE;
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
			User myUser = new User("test","test",1);
		}
		finally{
			if(tx.isActive()){
				tx.rollback();
			}
		}
		em.close();
	}
	
	public User getUsers(String email, String password){
		EntityManager em = emf.createEntityManager();
		try{
			Query qu = em.createNativeQuery("SELECT id, id64, fetchDate, value FROM DBuser WHERE email=? AND password=?",User.class);
			qu.setParameter(1,email);
			qu.setParameter(2, password);
			return (User) qu.getResultList().get(0);
		}
		finally{
			em.close();
		}
	}
	
	public void createUser(User myUser){
		EntityManager em = emf.createEntityManager();
		final EntityTransaction tx = em.getTransaction();
		
		try{
			tx.begin();
			em.persist(myUser);
			tx.commit();
		}finally{
			if(tx.isActive()){
				tx.rollback();
			}
		}
		em.close();
	}
}