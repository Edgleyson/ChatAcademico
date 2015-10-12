package esse.chat.control;

import esse.chat.model.ChatRoom;
import esse.chat.model.Chatter;
import esse.chat.model.Message;
import esse.chat.persistence.ChatRoomList;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Edgleyson
 */
public class AjaxSendMessage extends HttpServlet {
    
    

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            ServletContext context = request.getServletContext();
            HttpSession session = request.getSession();
            String roomname = (String) session.getAttribute("roomname");
            ChatRoomList roomlist = (ChatRoomList) context.getAttribute("myListRooms");
            ChatRoom chatRoom = (ChatRoom) roomlist.getRoom(roomname);
            
            //colher parametros
            int logados = chatRoom.getNoOfChatters();
            Chatter[] chatters = chatRoom.getChattersArray();
            List<Message> mensagens;
            mensagens = chatRoom.getListMessage();

            //criar resposta com JSON
            JSONArray jArray = atualizaJSON(logados, chatters, chatRoom, mensagens);
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jArray.toString());

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();
        String roomname = (String) session.getAttribute("roomname");
        ChatRoomList roomlist = (ChatRoomList) context.getAttribute("myListRooms");
        ChatRoom chatRoom = (ChatRoom) roomlist.getRoom(roomname);
        String nickname = (String) session.getAttribute("nickname");
        request.setCharacterEncoding("UTF-8");
        String msg = request.getParameter("message");
        try {
            if (nickname != null) {
                if (chatRoom != null) {
                    
                    if (msg != null && msg.length() > 0) {
                        msg = msg.trim();
                        chatRoom.addMessage(new Message(nickname, msg, new java.util.Date().getTime()));
                        List<Message> mensagens;
                        mensagens = chatRoom.getListMessage();
                        JSONArray jArray = mensagensJSON(mensagens);
                        response.setContentType("text/plain");
                        response.setCharacterEncoding("UTF-8");
                        response.getWriter().write(jArray.toString());
                    }else{
                        response.sendRedirect("error.jsp");
                    }
                } else {
                    response.sendRedirect("error.jsp");
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.err.println("doPost ok");

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private JSONArray mensagensJSON(List<Message> mensagens) {

        JSONObject job;
        JSONArray jArray = new JSONArray();
        for (Message message : mensagens) {
            
            job = new JSONObject();
            job.put("chatterName", message.getChatterName());
            job.put("message", message.getMessage());
            job.put("timeStamp", message.getTimeStamp());
            jArray.put(job);
        }
        //System.err.println(jArray.toString());
        return jArray;

    }

    private JSONArray atualizaJSON(int logados, Chatter[] chatters, ChatRoom chatRoom, List<Message> mensagens) {
        JSONObject job;
        JSONObject log = new JSONObject();
        JSONArray jArray = new JSONArray();
            
            log.put("logados", logados);
            jArray.put(log);          
            
            
        for (Chatter chatter : chatters) {
            job = new JSONObject();
            String sexChatter = chatter.getSex();
            //checa se o chatter é um monitor verificando se ele existe na lista de monitores
                boolean monitorOnLine = chatRoom.monitorExists(chatter);
                if (monitorOnLine){
                    if (sexChatter.equals("m")) {
                        job.put("status", "(monitor)");
                    } else {
                        job.put("status", "(monitora)");
                    }
                }else{
                    //checa se o chatter é o professor da sala comparando os ids
                    if (chatter.getId() == chatRoom.getProfessor()){
                        if (sexChatter.equals("m")) {
                            job.put("status", "(professor)");
                        } else {
                            job.put("status", "(professora)");
                        }
                    }else{
                    job.put("status", "");
                    }
                }
            job.put("chatterName", chatter.getNick());
            job.put("chatterId", chatter.getId());
            jArray.put(job);              
        }
        
        for (Message message : mensagens) {
            
            job = new JSONObject();
            job.put("chatterName", message.getChatterName());
            job.put("message", message.getMessage());
            job.put("timeStamp", message.getTimeStamp());
            jArray.put(job);
        }
        System.err.println(jArray.toString());
        return jArray;
    }

}
