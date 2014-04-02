package services;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
	
	public List<DBUser> getBackpackValues(long id64){
		EntityManager em = emf.createEntityManager();
		
		try{
			return em.createNamedQuery("byBackpackId",DBUser.class).setParameter("id64", id64).getResultList();
		}finally{
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

