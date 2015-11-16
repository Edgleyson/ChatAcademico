/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esse.chat.control;


import esse.chat.model.Arquivo;
import esse.chat.model.ChatRoom;
import esse.chat.model.Chatter;
import esse.chat.persistence.ArquivoDao;
import esse.chat.persistence.ChatRoomList;
import esse.chat.persistence.ChatterDao;
import esse.chat.persistence.ChatterDaoJDBC;
import esse.chat.persistence.ChatterDaoJPA;
import esse.chat.persistence.Fabrica;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Equipe ESSE Chat
 */
public class ListaArquivosServlet extends HttpServlet {

    private String contextPath = "";
    private static final String tipo = "JPA";
    ArquivoDao arquivoDao = Fabrica.FabricaMethod(tipo).createArquivoDAO();
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                contextPath = request.getContextPath();
		response.sendRedirect(contextPath + "/login.jsp");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();
        String roomName = (String)session.getAttribute("roomname");
            
            contextPath = request.getContextPath();
                ChatRoomList roomlist = (ChatRoomList) context.getAttribute("myListRooms");
                ChatRoom chatRoom = roomlist.getRoom(roomName);
                Object obj = chatRoom.getId();
                String roomId = obj.toString();

            
            try {
                
                List<Arquivo> arquivos = new ArrayList<>();
                arquivos = arquivoDao.listarArquivos(roomId); 
                
                if(arquivos==null){
                    response.sendRedirect(contextPath + "/esseChat.jsp?repo=nulo");
                }else{
                    session.setAttribute("arquivosList", arquivos);                  
                    response.sendRedirect(contextPath + "/listarquivos.jsp");
                }
                
                

            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
                }
    }

    


