package esse.chat.control;

import esse.chat.model.FileService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.servlet.http.HttpSession;

/**
 * Created by Marcio Ballem on 15/04/2014.
 */
@WebServlet(urlPatterns = "/uploads")//era /upload
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 8,   // 4MB
        maxRequestSize = 1024 * 1024 * 8 // 4MB
)
public class FileUploadServlet extends HttpServlet {

    

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String roomname = (String) session.getAttribute("roomname");
        Logger logger = Logger.getLogger(FileUploadServlet.class);
        String caminho = getServletContext().getRealPath("\\image\\");

        String BASE_DIR = caminho + roomname;

        response.setContentType("text/html;charset=UTF-8");

        FileService service = new FileService();

        Path destination = service.createFolder(BASE_DIR);

        for (Part part : request.getParts()) {
            if (Files.exists(destination)) {
                service.saveFile(destination, part);
            }
        }

        request.setAttribute("message", "Upload has been done successfully!");
        request.setAttribute("fileEntities", service.findAll());

        getServletContext()
                .getRequestDispatcher("/file-list.jsp")
                .forward(request, response);
    }
}
