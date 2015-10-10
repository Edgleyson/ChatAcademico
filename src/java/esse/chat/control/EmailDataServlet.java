/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package esse.chat.control;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import esse.chat.model.Chatter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Edgleyson
 */
public class EmailDataServlet extends HttpServlet {

    private EmailControl emailControl;
    private Chatter chatter;
    private String chat, emailChat, senhaChat, loginChat;

    public EmailDataServlet() {
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String op = request.getParameter("op") == null ? "" : request.getParameter("op");
        HttpSession session = request.getSession();

        if (op.equals("Enviar")) {
            emailControl = new EmailControl();
            String email_usuario = request.getParameter("email") == null ? "" : request.getParameter("email");

            try {
                chatter = emailControl.buscarPeloEmail(email_usuario);
            } catch (SQLException ex) {
                Logger.getLogger(EmailDataServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String nome = chatter.getName();
            String login = chatter.getNick() == null ? "" : chatter.getNick();
            String senha = chatter.getSenha();
            System.out.println("login"+login);
            if(login.equals("")){
                request.setAttribute("msg", "O email informado não consta em nossa base de dados!");
                request.getRequestDispatcher("email.jsp").forward(request, response);
            }else{
                chat = "Equipe Esse Chat";
                emailChat = getServletContext().getInitParameter("adminEmail");
                loginChat = emailChat;
                senhaChat = getServletContext().getInitParameter("senhaEmail");
            }
            
                String mensagem;
                mensagem = "Olá, " + nome + "\n"
                        + "\n"
                        + "Recebemos uma solicitação de recuperação de senha para este e-mail.\n"
                        + "Caso não tenha feito esta solicitação, favor desconsiderar o mesmo.\n"
                        + "\n"
                        + "Os dados solicitados são:\n"
                        + "\n"
                        + "Nickname: " + login + "\n"
                        + "Senha: " + senha + "\n"
                        + "\n"
                        + "\n"
                        + "Att, \n"
                        + chat + " \n";

                String mailServer = "smtp.gmail.com";//servidor SMTP do Gmail
                String assunto = "Recuperação de Senha";
//                SendMail sendMail = new SendMail();
//                sendMail.sendMail(emailChat,email_usuario,assunto,mensagem); 

                if (emailControl.enviarEmail(mailServer, email_usuario, emailChat, assunto, mensagem, loginChat, senhaChat)) {
                    request.setAttribute("msg", "Envio concluído com sucesso, confira seu e-mail!");
                    request.getRequestDispatcher("email.jsp").forward(request, response);
                } else {
                    request.setAttribute("msg", "Falha ao enviar e-mail!");
                    request.getRequestDispatcher("email.jsp").forward(request, response);
                }
            
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
