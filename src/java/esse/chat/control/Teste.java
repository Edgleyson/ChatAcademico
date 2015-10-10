/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esse.chat.control;

import esse.chat.model.ChatRoom;
import esse.chat.model.ChatterStatus;
import esse.chat.persistence.ChatRoomDao;
import esse.chat.persistence.ChatterDao;
import esse.chat.persistence.Fabrica;
import java.sql.SQLException;

/**
 *
 * @author Edgleyson
 */
public class Teste {
    private static final String tipo = "JDBC";
    ChatterDao chatterDao = Fabrica.FabricaMethod(tipo).createChatterDAO();
    private static ChatRoomDao roomDao = Fabrica.FabricaMethod(tipo).createChatRoomDAO();
    
//    public static void main(String[] args) throws SQLException {
//        ChatRoom room = new ChatRoom();
//        room.setName("Sistema de Tempo Real");
//        room.setDescription("Docente: Paulo Abadie Guedes");
//        boolean incluiuSala = roomDao.incluirSala(room);
//        if(incluiuSala){
//            System.out.println("Sala foi persistida");
//        }
//        room.setProfessor(3);
//        boolean atualizouSala = roomDao.atualizarSala(room);
//        if(atualizouSala){
//            System.out.println("Sala foi atualizada");
//        }
//       
//    }
}
