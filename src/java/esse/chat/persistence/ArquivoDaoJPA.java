/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esse.chat.persistence;

import esse.chat.model.Arquivo;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import esse.chat.util.Util;

/**
 *
 * @author Equipe ESSE Chat
 */
class ArquivoDaoJPA implements ArquivoDao {

    public ArquivoDaoJPA() {        
    }

    @Override
    public boolean subirArquivo(Arquivo arquivo) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
                EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(arquivo);
		t.commit();
		em.close();
                return true;
    }

    @Override
    public Arquivo baixarArquivo(Arquivo arquivo) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
                Arquivo arq = (Arquivo) em.find(Arquivo.class, arquivo.getId());
		em.close();
		return arq;
    }

    @Override
    public boolean excluirArquivo(Arquivo arquivo) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
            em.getTransaction().begin();
            Arquivo arq = (Arquivo) em.find(Arquivo.class, arquivo.getId());
            em.remove(arq);
            em.getTransaction().commit();
            em.close();
            return true;
    }

    @Override
    public List<Arquivo> listarArquivos(String roomId) throws SQLException {
        EntityManager em = FabricaDaoJPA.getInstance().getFactory().createEntityManager();
        String SELECT_FROM_ARQUIVO = "select a.id, a.nome, a.chatter, a.timestamp, a.mimeType from Arquivo a where a.roomId="+roomId;    
        List<Object> arquivos = em.createQuery(SELECT_FROM_ARQUIVO).getResultList();
	em.close();
        
        List<Arquivo> meusArquivos = new ArrayList();
        Util util = new Util();
        for (Object obj : arquivos) {
            Object[] objArray = (Object[]) obj;
            long id = Long.parseLong(String.valueOf(objArray[0])); // assumindo que a coluna nome é uma String 
            String nome = String.valueOf(objArray[1]); // assumindo que a coluna nome é uma String 
            String chatter = String.valueOf(objArray[2]);
            Timestamp time = util.convertStringToTimestamp(String.valueOf(objArray[3]));
            String mime = String.valueOf(objArray[4]);
            System.err.println(mime);
            Arquivo arq = new Arquivo(id, nome, chatter, time, mime);
            meusArquivos.add(arq);
        }
	return meusArquivos;
    }
        
}
