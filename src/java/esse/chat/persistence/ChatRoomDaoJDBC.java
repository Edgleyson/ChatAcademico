package esse.chat.persistence;

import esse.chat.model.ChatRoom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ChatRoomDaoJDBC implements ChatRoomDao {
    
    Conexao cnx;
    private Connection conn;  
    private ChatRoom room;
    
    @Override
    public boolean incluirSala(ChatRoom room) throws SQLException {
        cnx = new Conexao();
        PreparedStatement stmt = null;
        conn = cnx.criarConexao();
        this.room = room;
        String cmdsql = "insert into CHATROOM (nome, descricao) values (?,?)";
        try { //Prepara o comando stmt setando os VALUES (?, ?, ?)
                stmt = conn.prepareStatement(cmdsql); 
                stmt.setString(1, room.getName());
                stmt.setString(2, room.getDescription());
                stmt.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }finally{
                try {
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                    } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }return true;
    }           

    @Override
    public boolean atualizarSala(ChatRoom room) throws SQLException {
        cnx = new Conexao();
        conn = cnx.criarConexao();
        try {
            String cmdsql = "UPDATE CHATROOM SET NOME=?, DESCRICAO=?, PROFESSOR=? WHERE ROOMID=?";
        try ( //Prepara o comando stmt setando os VALUES (?, ?, ?, ?, ?)
                PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
                stmt.setString(1, room.getName());
                stmt.setString(2, room.getDescription());
                stmt.setLong(3, room.getProfessor());
                stmt.setLong(4, room.getId());
                stmt.execute();
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            conn.close();
        }return true;
    }

    @Override
    public boolean excluirSala(ChatRoom room) throws SQLException {
        cnx = new Conexao();
        conn = cnx.criarConexao();
        try {
            try (Statement stmt = conn. createStatement()) {
                stmt.execute ("DELETE FROM CHATROOM WHERE ROOMID = " + room.getId());
                stmt.close();
            }
        } catch (Exception excecao) {
            throw (excecao);
        }finally{
            conn.close();
        }return true;
    }

    
    @Override
    public List<ChatRoom> listarSalas(ChatRoom room) throws SQLException {
        cnx = new Conexao();
                List<ChatRoom> rooms = new ArrayList<>() ;
        conn = cnx.criarConexao();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CHATROOM");
        /*
        try {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next() ) {
                    room = new ChatRoom();
                    room.setRoomid(rs.getLong("roomid"));
                    room.setName(rs.getString("nome"));
                    room.setProfessor(rs.getString("professor"));
                    room.setMonitor1(rs.getString("monitor1"));
                    room.setMonitor2(rs.getString("monitor2"));
                    //Adiciona as salas à lista de salas
                    rooms.add(room);
                }rs.close();
            }stmt.close();
        } catch (Exception excecao) {
            throw (excecao);
        }finally{
            conn.close();
        }*/
                return rooms;
    }

    @Override
    public boolean cadastrarProfessor(Long chatterId, int roomId) throws SQLException {
        cnx = new Conexao();
        conn = cnx.criarConexao();
        try {
            String cmdsql = "UPDATE CHATROOM SET PROFESSOR=? WHERE ROOMID=?";
        try ( //Prepara o comando stmt setando os VALUES (?, ?, ?, ?, ?)
                PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
                stmt.setLong(1, chatterId);
                stmt.setInt(2, roomId);
                stmt.execute();
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            conn.close();
        }return true;
    }

    @Override
    public boolean cadastrarMonitor(long id, int roomid) throws SQLException {
        cnx = new Conexao();
        conn = cnx.criarConexao();
        try {
            String cmdsql = "insert into MONITORES (userid, roomid) values (?,?)";
        try ( //Prepara o comando stmt setando os VALUES (?, ?, ?, ?, ?)
                PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
                stmt.setLong(1, id);
                stmt.setInt(2, roomid);
                stmt.execute();
                stmt.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally{
            conn.close();
        }return true;
    }
}
