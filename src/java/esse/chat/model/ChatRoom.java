package esse.chat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "roomid")
        private Long id;
        @Column (name = "nome", unique = true)
	private String name;
        @Column (name = "descricao", nullable = false)
	private String description;
        @Column (nullable = true)
	private Long professor;
        
        @OneToMany(fetch = FetchType.EAGER)
        @JoinTable(name = "MONITORES", joinColumns = {
        @JoinColumn(name = "ROOMID")},
            inverseJoinColumns = {
                @JoinColumn(name = "USERID")})
        private Collection<Chatter> monitores;
        
                @Transient
        public Map<Long, Chatter> chatters;
        /*
	* Linked list para armazenar objetos Message
	*/
	@Transient
        private List messages;
	
	/*
	* Usado para setar o número máximo de mensagens
	*/
        @Transient
	private int messages_size = 25;
        

        public void adicionaMonitor(Chatter chatter) {
            if (!this.monitores.contains(chatter)) {
            monitores.add(chatter);
            }
        }

        public void removeMonitor(Chatter chatter) {
            if (monitores != null) {
                monitores.remove(chatter);
            }
        }
        
        public void setMonitores(Collection<Chatter> monitores) {
            for (Chatter chatter : monitores) {
            this.adicionaMonitor(chatter);
            }
        }
        
        public Chatter getMonitor(long id){
            for (Chatter chatter : monitores) {
                if(id == chatter.getId()){
                    return chatter;
                }
            }
            return null;
        }


        
        public ChatRoom()
	{
		chatters = new HashMap();
                monitores = new ArrayList<>();
                messages = new LinkedList();
	}
        
        
       public Long getId() {
           return id;
       }

       
       public void setId(Long id) {
           this.id = id;
       }

       /**
        * @return the name
        */
       public String getName() {
           return name;
       }

       /**
        * @param name the name to set
        */
       public void setName(String name) {
           this.name = name;
       }

       /**
        * @return the description
        */
       public String getDescription() {
           return description;
       }

       /**
        * @param description the description to set
        */
       public void setDescription(String description) {
           this.description = description;
       }

       /**
        * @return the professor
        */
       public Long getProfessor() {
           return professor;
       }

       /**
        * @param professor the professor to set
        */
       public void setProfessor(long professor) {
           this.professor = professor;
       }

             
        
	/**
	* Este construtor recebe um nome e descrição
        * Para criar uma nova ChatRoom
	* @param name Nome da sala
	* @param descr Descrição da sala
	*/
	public ChatRoom(String name, String descr)
	{
                this.name= name;
		this.description = descr;
                chatters = new HashMap();
                monitores = new ArrayList<>();
                messages = new LinkedList();
	}
	
        public ChatRoom(Long id, String name, String descr, long prof, long mt1, long mt2)
	{
                this.id = id;
                this.name= name;
		this.description = descr;
                this.professor = prof;
                chatters = new HashMap();
                monitores = new ArrayList<>();
                messages = new LinkedList();
	}
	
	
	/**
	* Adiciona usuário à lista
     	* @param chatter usuário
	*/
	public synchronized void addChatter(Chatter chatter)
	{
                chatters.put(chatter.getId(), chatter);
           
	}
	
	
	/**
	* Retorna usuário da lista.
	* @param id nome do usuário
	* @return esse.chat.Chatter
	*/
	public Chatter getChatter(long id)
	{
		return (Chatter)chatters.get(id);
	}
	
	/**
	* verifica se um participante existe ou não
	* @param id nome do usuário para checar
	* @return boolean
	*/
	public boolean chatterExists(long id)
	{
           return chatters.containsKey(id);
        }
	
        /**
	* verifica se um participante existe ou não
        * @param chatter usuário para checar
	* @return boolean
	*/
	public boolean monitorExists(Chatter chatter)
	{
           return monitores.contains(chatter);
        }    
	
	
	/**
	* retorna número total de usuários na sala
	* @return int
	*/
	public int getNoOfChatters()
	{
		return chatters.size();
	}
	
	/**
	* Retorna um conjunto contendo todos os usuários da sala
	* @return java.util.Set
	*/
	public Set getChatters()
	{
            return chatters.entrySet();
        }
        
	public Map getMapChatters()
	{
            return chatters;
        }
	
	/** retorna um array contendo todos os usuários
	* @return esse.chat.Chatter[]
	*/
	public Chatter[] getChattersArray()
	{
		Chatter[] chattersArray = new Chatter[chatters.size()];
		Set myChatters = getChatters();
		Iterator chattersit = myChatters.iterator();
		int i = 0;
		while(chattersit.hasNext())
		{
			Map.Entry me = (Map.Entry)chattersit.next();
			long key = (long) me.getKey();
			chattersArray[i] = (Chatter)me.getValue();
                        System.out.println(chattersArray[i].getName());
			i++;
		}
		return chattersArray;
	}
	
	/** Adiciona uma mensagemà lista de mensagens
	* @param msg objeto mensagem
	*/
	public synchronized void addMessage(Message msg)
	{
		if(messages.size()==messages_size)
		{
			((LinkedList)messages).removeFirst();
		}
		messages.add(msg);
	}
	
	/**
	* retorna um objecto ListIterator contendo todas as mensagens
	* @return java.util.ListIterator
	*/	
	public ListIterator getMessages()
	{
		return messages.listIterator();
	}
        
        public List getListMessage(){
            return this.messages;
        }

	/**
	* Retorna um array de mensagens enviadas após determinado tempo
	* @param afterTimeStamp Time in milliseconds.
	* @return array
	*/	
	public Message[] getMessages(long afterTimeStamp)
	{
		ListIterator li = messages.listIterator();
		List temp = new ArrayList();
		Message m;
		while (li.hasNext())
		{
			m = (Message)li.next();
			if (m.getTimeStamp() >= afterTimeStamp)
			{
				temp.add(m);
			}
		}
		Object o[] = temp.toArray();
		Message[] arr = new Message[o.length];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = (Message)o[i];
		}
		return arr;
	}

	/**
	* Retorna o número total de mensagens na lista de mensagens
	* @return int
	*/
	public int getNoOfMessages()
	{
		return messages.size();
	}
	
	/**
	* Define tamanho máximo de mensagens.
	* @param size
	*/
	public void setMaximumNoOfMessages(int size)
	{
		messages_size = size;
	}
	
	/**
	* retorna número maxmium de mensagens definidas.
	* @return int
	*/
	public int getMaxiumNoOfMessages()
	{
		return messages_size;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ChatRoom)) {
            return false;
        }
        ChatRoom other = (ChatRoom) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        } else if (this.id == null && other.id == null) {
            return this.name.equals(other.name);
        }
        
        return true;
    }

    

    @Override
    public String toString() {
        return "esse.chat.model.ChattRoom[ id=" + id + " ]";
    }
    
}
