/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esse.chat.control;

import esse.chat.model.Arquivo;
import esse.chat.persistence.ArquivoDao;
import esse.chat.persistence.ChatRoomList;
import esse.chat.persistence.Fabrica;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import static com.google.common.primitives.Bytes.concat;


@MultipartConfig
public class Upload extends HttpServlet {
    
    private static final String tipo = "JPA";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

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
        processRequest(request, response);
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
        ChatRoomList roomlist = (ChatRoomList)context.getAttribute("myListRooms");
        HttpSession session = request.getSession();
            String roomname = (String) session.getAttribute("roomname");
            String nickname = (String) session.getAttribute("nickname");
        Object obj = roomlist.getRoom(roomname).getId();
        String id = obj.toString();
        long roomId = Long.parseLong(id);
            //String mimeType = request.getParameter("mimeType");
            
        //Part filePart = request.getPart("file");
        Collection<Part> items = request.getParts();
        String nome = "", mime = "";
        byte[] buffer;
        byte[] bFile = new byte[] {};
        for (Part item : items) {
            nome = item.getSubmittedFileName();
            mime = item.getContentType();
        
            InputStream is = item.getInputStream();
            int size = is.available();
            buffer = new byte[size];
            int nLidos;
            while ((nLidos = is.read(buffer)) >= 0) {
                bFile = concat(bFile, buffer);
            }
        }
//                    output.write(buffer, 0, nLidos);
//                }
//            nLidos = is.read(buffer);
//            bFile += buffer;
//        }
//        byte[] bFile;
//        try (InputStream is = filePart.getInputStream()) {
//            int size = is.available();
//            bFile = new byte[size];
//            is.read(bFile, 0, size);
//        }
//        String mimeType = filePart.getContentType();
//        String nome = filePart.getName();
        
        Arquivo arq = new Arquivo(nome, roomId, nickname, mime, bFile);
        
        // salva o contato usando o padrão DAO
            ArquivoDao arquivoDao;
            arquivoDao = Fabrica.FabricaMethod(tipo).createArquivoDAO();
        try {
            arquivoDao.subirArquivo(arq);
        } catch (SQLException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("Arquivo enviado com sucesso!");
    }          
//        Collection<Part> items = request.getParts();
        // Pega o diretório /logo dentro do diretório atual de onde a
        // aplicação está rodando
//        String caminhodefault = getServletContext().getRealPath("");
//        String caminho = caminhodefault + "\\chat";

        // Cria o diretório caso ele não exista
//        File diretorio = new File(caminho);
//        if (!diretorio.exists()) {
//            diretorio.mkdir();
//        }

//        for (Part item : items) {
            // Mandar o arquivo para o diretório informado
//            String nome2 = item.getSubmittedFileName();
//            String mime = item.getContentType();
//        String arq[] = nome.split("\\\\");
//        for (int i = 0; i < arq.length; i++) {
//            nome = arq[i];
//        }

//            File file = new File(diretorio, nome);
//            try (FileOutputStream output = new FileOutputStream(file)) {
//                InputStream is = item.getInputStream();
//                byte[] buffer = new byte[2048];
//                int nLidos;
//                while ((nLidos = is.read(buffer)) >= 0) {
//                    output.write(buffer, 0, nLidos);
//                }
//                
//                output.flush();
//            }
//        }
//
//        response.setContentType("text/plain");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("Arquivo enviado com sucesso!");
//    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
