package esse.chat.control;

import esse.chat.model.ChatRoom;
import esse.chat.model.Chatter;
import esse.chat.persistence.ChatRoomDao;
import esse.chat.persistence.ChatRoomList;
import esse.chat.persistence.ChatterDao;
import esse.chat.persistence.Conexao;
import esse.chat.persistence.Fabrica;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/* Durante a inicialização do servidor este servlet é inicializado.
*  @author Equipe ESSE Chat
*/
public class ManageChatServlet extends HttpServlet
{
    private ChatRoomList rooms;
    PreparedStatement stmt = null;
    ServletContext context;
    Conexao conexao = null;
    Connection conn = null;
    ChatRoom room;
    private static final String tipo = "JDBC";
    ChatterDao chatterDao = Fabrica.FabricaMethod(tipo).createChatterDAO();
    ChatRoomDao roomDao = Fabrica.FabricaMethod(tipo).createChatRoomDAO();
    
    /** Cria um objeto ChatRoomList e as armazena em ServletContext
    * @throws javax.servlet.ServletException
    */
    @Override
    public void init() throws ServletException {
        try {
            String sql = "SELECT * FROM ESSECHAT.CHATROOM";
            this.rooms = ChatRoomList.getInstancia();
            criarListaDeSalas(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ManageChatServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    public void criarListaDeSalas(String sql) throws SQLException {
        //this.rooms=list;
        conexao = new Conexao();
        conn = conexao.criarConexao();
        context = getServletContext();
        stmt = conn.prepareStatement(sql);
        try {
            try (ResultSet rs = stmt.executeQuery()) {
                String s = getServletContext().getInitParameter("maxNoOfMessages");
		int maxMessages = 25;
		    if (s != null) {
                    try {
                        maxMessages = Integer.parseInt(s);
                    } catch (NumberFormatException nfe) {

                    }
                }
                while (rs.next() ) {
                    room = new ChatRoom();
                    room.setId(rs.getLong("roomid"));
                    room.setName(rs.getString("nome"));
                    room.setDescription(rs.getString("descricao"));
                    room.setProfessor(rs.getLong("professor"));
                    room.setMaximumNoOfMessages(maxMessages);
                    String mySql = "SELECT * FROM ESSECHAT.Monitores WHERE ROOMID=?";
                    List monitores = criarListadeMonitores(conn, mySql, room.getId());
                    room.setMonitores(monitores);
                    rooms.addRoom(room);
                }rs.close();
                synchronized(context){
                context.setAttribute("myListRooms", rooms);
                }
                System.err.println("Lista de salas criada");
            }stmt.close();            
        } catch (Exception e) {
            throw new RuntimeException(e) ;
        }finally{
            
                conn.close();
        }
    }
    
    private List criarListadeMonitores(Connection conn, String sql, long roomid) throws SQLException {
        List monitores = new ArrayList();
        String cmdsql = "SELECT * FROM ESSECHAT.Monitores WHERE ROOMID=?";
        try (PreparedStatement newstmt = conn.prepareStatement(cmdsql)) {
                newstmt.setLong(1, roomid);
                ResultSet rs = newstmt.executeQuery();
                while (rs.next() ) {
                    Long idMonitor;
                    idMonitor = (rs.getLong("userid"));
                    Chatter chatter = chatterDao.pesquisarDados(idMonitor);
                    monitores.add(chatter);                    
                }rs.close();
                
                newstmt.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }return monitores;
    }

        @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out;
                out = response.getWriter();
                
                
        }

        
	
	/**  Permite ao usuário adicionar novas salas após a realização de validação.
        *    Também salva informações para arquivos chat.properties se exigido pelo parâmetro de inicialização <code>saveRooms</code>.
        * @param request
        * @param response
        * @throws javax.servlet.ServletException
        * @throws java.io.IOException
	*/
        @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		context = request.getServletContext();
                HttpSession session = request.getSession();
                String roomName = request.getParameter("rn");
		String roomDescr = request.getParameter("rd");
                int status = (int)session.getAttribute("status");
                long chatterId = (long)session.getAttribute("chatterid");
		if (roomName == null || roomName.length() == 0 || roomDescr == null || roomDescr.length() == 0)
		{
			request.setAttribute("error", "Por favor, especifique o nome da sala e sua descrição");
			context.getRequestDispatcher("/addRoom.jsp").forward(request, response);
			return;

		}
		try
		{
			if (rooms != null)
			{
                            ChatRoom room = new ChatRoom();
                            room.setName(roomName);
                            room.setDescription(roomDescr);
                            if(status == 2){
                                room.setProfessor(chatterId);
                            }
                            String s = getServletContext().getInitParameter("maxNoOfMessages");
                            int maxMessages = 25;
                                if (s != null) {
                                try {
                                    maxMessages = Integer.parseInt(s);
                                } catch (NumberFormatException nfe) {

                                }
                            }
                            room.setMaximumNoOfMessages(maxMessages);
                        rooms.addRoom(room);
                        }
			
			
			String s = getServletContext().getInitParameter("saveRooms");
			boolean save = false;
			if (s != null && "true".equals(s) )
			{
				Boolean b = Boolean.valueOf(s);
				save = b;
			}
			if (save)
			{
				/*Caso haja necessidade, futuramente podemos escrever
                                * um método para persistir as salas criadas pelos usuários no
                                * banco de dados.
                                */
			}
			response.sendRedirect(request.getContextPath() + "/listrooms.jsp");
		}
		catch (Exception e)
		{
				System.err.println("Exception: " + e.getMessage());
		}		
	}
	
	/** Chamado quando servlet está sendo destruído */

        @Override
	public void destroy()
	{
            System.err.println("Todas as salas foram destruídas");
	}
}