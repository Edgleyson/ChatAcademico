/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esse.chat.persistence;

import esse.chat.model.Chatter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Equipe ESSE Chat
 */
public class ChatterDaoJDBC implements ChatterDao{
    
    private final Conexao conexao = new Conexao();
    private Connection conn; 
    private Chatter chatter;
    
    @Override
    public boolean inserirDados(Chatter chatter) throws SQLException{
        try {
            this.chatter = chatter;
            conn = conexao.criarConexao();
            String cmdsql = "insert into CHATTER (nome, nickname, senha, sexo, nascimento, email, status) values (?,?,?,?,?,?,?)";
            try ( //Prepara o comando stmt setando os VALUES (?, ?, ?) '" + nickname + "'
                PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
                stmt.setString(1, chatter.getName());
                stmt.setString(2, chatter.getNick());
                stmt.setString(3, chatter.getSenha());
                stmt.setString(4, chatter.getSex());
//                Date data = chatter.getNascimento();
//                java.sql.Date d = new java.sql.Date (data.getTime());
//                stmt.setDate(5, d);
//                ou na forma reduzida abaixo:
                stmt.setDate(5, new java.sql.Date(chatter.getNascimento().getTime()));                    
                stmt.setString(6, chatter.getEmail());
                int status = chatter.getStatus();
                String statusStr = Integer.toString(status);
                stmt.setString(7, statusStr);
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
    public boolean atualizarDados(Chatter chatter) throws SQLException {
        try {//ver página 38 da Caellum
            conn = conexao.criarConexao();
            String cmdsql = "UPDATE CHATTER SET NOME = '" + chatter.getName()+ "', "
                    + "NICKNAME =" + chatter.getNick() + ", "
                    + "SEXO =" + chatter.getSex() + ", "
                    + "NASCIMENTO =" + chatter.getNascimento() + ", "
                    + "EMAIL = "+chatter.getEmail()+ " "
                    + "WHERE CHATTERID = " + chatter.getId();
           // manterDados(conn, cmdsql);
        } catch (Exception ex) {
            throw (ex);
        }finally{
            conn.close();
        }return true;
    }

    @Override
    public boolean excluirDados(Chatter chatter) throws SQLException{
        try {
            conn = conexao.criarConexao();
            String cmdsql = "DELETE FROM CHATTER WHERE CHATTERID = " + chatter.getId();
            //manterDados(conn, cmdsql);
        } catch (Exception excecao) {
            throw (excecao);
        }finally{
            conn.close();
        }return true;
    }

    @Override
    public Chatter pesquisarDados(Long id) throws SQLException{
        chatter = new Chatter();
        conn = conexao.criarConexao();
        String cmdsql = "SELECT * FROM CHATTER WHERE USERID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
            stmt.setLong(1, id);
        
            ResultSet retorno = stmt.executeQuery();
            retorno.next();
            try {
                chatter.setId(retorno.getLong("userid"));
                chatter.setName(retorno.getString("nome"));
                chatter.setNick(retorno.getString("nickname"));
                chatter.setSenha(retorno.getString("senha"));
                chatter.setSex(retorno.getString("sexo"));
                chatter.setNascimento(retorno.getDate("nascimento"));
                chatter.setEmail(retorno.getString("email"));
                chatter.setStatus(retorno.getInt("status"));
            } catch (Exception excecao) {
                
            }
            
        } catch (Exception excecao) {
            throw (excecao);
        }finally{
            conn.close();
        }
        return chatter;
    }
    
    
    @Override
    public Chatter consultarPeloNickname(String nickname) throws SQLException{
        chatter = new Chatter();
        conn = conexao.criarConexao();
        String cmdsql = "SELECT * FROM ESSECHAT.CHATTER WHERE NICKNAME = ?";
        try (PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
            stmt.setString(1, nickname);
        
            ResultSet retorno = stmt.executeQuery();
            retorno.next();
            try {
                chatter.setId(retorno.getLong("userid"));
                chatter.setName(retorno.getString("nome"));
                chatter.setNick(retorno.getString("nickname"));
                chatter.setSenha(retorno.getString("senha"));
                chatter.setSex(retorno.getString("sexo"));
                chatter.setNascimento(retorno.getDate("nascimento"));
                chatter.setEmail(retorno.getString("email"));
                chatter.setStatus(retorno.getInt("status"));
            } catch (Exception excecao) {
                
            }
            
        } catch (Exception excecao) {
            throw (excecao);
        }finally{
            conn.close();
        }
        return chatter;
    }
    public Chatter consultarPeloEmail(String email) throws SQLException{
        chatter = new Chatter();
        conn = conexao.criarConexao();
        String cmdsql = "SELECT * FROM ESSECHAT.CHATTER WHERE EMAIL = ?";
        try (PreparedStatement stmt = conn.prepareStatement(cmdsql)) {
            stmt.setString(1, email);
        
            ResultSet retorno = stmt.executeQuery();
            retorno.next();
            try {
                chatter.setId(retorno.getLong("userid"));
                chatter.setName(retorno.getString("nome"));
                chatter.setNick(retorno.getString("nickname"));
                chatter.setSenha(retorno.getString("senha"));
                chatter.setSex(retorno.getString("sexo"));
                chatter.setNascimento(retorno.getDate("nascimento"));
                chatter.setEmail(retorno.getString("email"));
                chatter.setStatus(retorno.getInt("status"));
            } catch (Exception excecao) {
                
            }
            
        } catch (Exception excecao) {
            throw (excecao);
        }finally{
            conn.close();
        }
        return chatter;
    }

        
    /**
     *
     * @return
     * @throws SQLException
     */
    @Override
    public List<Chatter> listarChatters() throws SQLException {
        
        List<Chatter> chatters = new ArrayList<>() ;
        conn = conexao.criarConexao();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM CHATTER ORDER BY NOME");
        
        
        try {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next() ) {
                    chatter = new Chatter();
                    chatter.setId(rs.getLong("userid"));
                    chatter.setName(rs.getString("nome"));
                    chatter.setNick(rs.getString("nickname"));
                    chatter.setSenha(rs.getString("senha"));
                    chatter.setSex(rs.getString("sexo"));
//                    String nasc = rs.getString("nascimento");
                    chatter.setNascimento(rs.getDate("nascimento"));
                    chatter.setEmail(rs.getString("email"));
                    chatter.setStatus(rs.getInt("status"));
                    //Adiciona as salas à lista de salas
                    chatters.add(chatter);
                }rs.close();
            }stmt.close();
        } catch (SQLException e) {
                throw new RuntimeException(e);
        }finally{
            try {
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                    } catch (SQLException ex) {
                }
        }
        return chatters;
    }

}
