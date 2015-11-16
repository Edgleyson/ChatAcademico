package esse.chat.model;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Arquivo POJO classe. Representa a tabela Arquivo
 */
@Entity
public class Arquivo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ARQUIVO_ID")
    private long id;
    
    @Column(name = "ROOM_ID", nullable = false)
    private long roomId;
    
    @Column(name = "ARQUIVO_NOME", nullable = false)
    private String nome;

    @Column(name = "ARQUIVO_CHATTER", nullable = false)
    private String chatter;

    @Column(name = "ARQUIVO_DATA", nullable = false)
    private Timestamp timestamp;

    @Column(name = "ARQUIVO_TIPO", nullable = false)
    private String mimeType;

    @Lob
    @Column(name = "ARQUIVO", nullable = false, columnDefinition = "longblob")
    private byte[] arquivo;

    public Arquivo() {
            this.timestamp = new Timestamp(System.currentTimeMillis());  
    }

    public Arquivo(String name, long roomId, String chatter, String mimeType, byte[] image) {
        this.nome = name;
        this.roomId = roomId;
        this.chatter = chatter;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.mimeType = mimeType;
        this.arquivo = image;
    }
    
    public Arquivo(String name, long roomId, String chatter, String mimeType) {
        this.nome = name;
        this.roomId = roomId;
        this.chatter = chatter;
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.mimeType = mimeType;
    }    
    
    public Arquivo(long id, String name, String chatter, Timestamp timestamp, String mime) {
        this.id = id;
        this.nome = name;
        this.chatter = chatter;
        this.timestamp = timestamp;
        this.mimeType = mime;
    }    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String name) {
        this.nome = name;
    }

    public byte[] getImage() {
        return arquivo;
    }

    public void setImage(byte[] image) {
        this.arquivo = image;
    }

    public String getChatter() {
        return chatter;
    }

    public void setChatter(String chatter) {
        this.chatter = chatter;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
