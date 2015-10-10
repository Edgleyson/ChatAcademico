package esse.chat.persistence;

import esse.chat.model.Chatter;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author Equipe ESSE Chat
 */
public class ChatterDaoJPA implements ChatterDao {

    private static final String SELECT_FROM_CHATTER = "select c from Chatter c"; 
    public ChatterDaoJPA() {
    }
    
    @Override
    public boolean inserirDados(Chatter chatter) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
                EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(chatter);
		t.commit();
		em.close();
                return true;
    }

    @Override
    public boolean atualizarDados(Chatter chatter) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
		em.getTransaction().begin();
		em.merge(chatter);
		em.getTransaction().commit();
		em.close();
                return true;
    }

    @Override
    public boolean excluirDados(Chatter umChatter) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
                em.getTransaction().begin();
		Chatter chatter = (Chatter)em.find(Chatter.class, umChatter.getId());
		em.remove(chatter);
		em.getTransaction().commit();
		em.close();
                return true;
    }

    @Override
    public Chatter pesquisarDados(Long id) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
                Chatter chatter = (Chatter) em.find(Chatter.class, id);
		em.close();
		return chatter;
    }

    
    @Override
    public List<Chatter> listarChatters() throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
                List<Chatter> chatters = em.createQuery(SELECT_FROM_CHATTER).getResultList();
		em.close();
		return chatters;
    }

    @Override
    public Chatter consultarPeloNickname(String nickname) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
        Query query = em.createQuery("select c from Chatter c where c.NICKNAME = :nickname");
        Query setParameter;
        setParameter = query.setParameter("nickname", nickname);
        Chatter chatter = (Chatter)setParameter.getSingleResult();
        return chatter;
    }

    @Override
    public Chatter consultarPeloEmail(String email) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
        Query query = em.createQuery("select c from Chatter c where c.EMAIL = :email");
        Query setParameter;
        setParameter = query.setParameter("email", email);
        Chatter chatter = (Chatter)setParameter.getSingleResult();
        return chatter;
    }

    
}
