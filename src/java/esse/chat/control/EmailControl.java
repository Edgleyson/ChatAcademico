package esse.chat.control;

import java.util.*;  
import java.sql.SQLException;    
import esse.chat.model.Chatter;
import esse.chat.persistence.ChatRoomDao;
import esse.chat.persistence.ChatterDao;
import esse.chat.persistence.Fabrica;
import java.util.Properties; 
import javax.mail.Message; 
import javax.mail.Session; 
import javax.mail.Transport; 
import javax.mail.internet.InternetAddress; 
import javax.mail.internet.MimeMessage; 
  
  
public class EmailControl {  
      
    private Chatter chatter;   
    private String email;  
    private String erro;  
    private static final String tipo = "JDBC";
    ChatterDao chatterDao = Fabrica.FabricaMethod(tipo).createChatterDAO();
    ChatRoomDao roomDao = Fabrica.FabricaMethod(tipo).createChatRoomDAO();
      
    public EmailControl() {  
    }  

      
    /********************************** Buscar ****************************************/  
    public Chatter buscarPeloEmail(String email) throws SQLException {  
        this.chatter = chatterDao.consultarPeloEmail(email); 
        System.out.println(chatter.getNick());
        return chatter;
    }  
      
      
    /********************************** Transporte ****************************************/  
    public boolean enviarEmail(String mailServer, String para, String de, 
                               String assunto, String mensagem, String login_instituicao, 
                               String senha_instituicao){  
          

        try{  
            Properties mailProps = new Properties();
              
            mailProps.put("mail.smtp.host", mailServer);  
            mailProps.put("mail.smtp.auth", "true");  
            mailProps.put("mail.debug", "true");  
            mailProps.put("mail.smtp.debug", "true");  
            mailProps.put("mail.mime.charset", "ISO?8859?1");  
            mailProps.put("mail.smtp.port", "465");  
            mailProps.put("mail.smtp.starttls.enable", "true");  
            mailProps.put("mail.smtp.socketFactory.port", "465");  
            mailProps.put("mail.smtp.socketFactory.fallback", "false");  
            mailProps.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
              
            Session mailSession = Session.getDefaultInstance(mailProps);  
              
            //As duas linhas seguintes de código, colocam no formato de endereços,  
            //supostamente válidos, de email os dados passados pelos parâmetros de e para.  
            InternetAddress destinatario = new InternetAddress(para);  
            InternetAddress remetente = new InternetAddress(de);  
              
            //As duas linhas de código a seguir, são responsáveis por setar os atributos e  
            //propriedades necessárias do objeto message para que o email seja enviado.  
            //inicialização do objeto Message  
            Message message = new MimeMessage(mailSession);  
              
            //Definição da Data que está enviando o email  
            message.setSentDate(new Date());//novo  
              
            //Definição de quem está enviando o email  
            message.setFrom(remetente);  
              
            //define o(s) destinatário(s) e qual o tipo do destinatário.  
            //os possíveis tipos de destinatário: TO, CC, BCC  
            message.setRecipient(Message.RecipientType.TO, destinatario);  
              
            //definição do assunto do email  
            message.setSubject(assunto);  
              
            //definição do conteúdo da mensagem e do tipo da mensagem  
            message.setContent(mensagem, "text/plain");  
              
            //as linhas de código seguinte é a responsável pelo envio do email  
            Transport transport = mailSession.getTransport("smtp");  
            transport.connect(mailServer, login_instituicao, senha_instituicao);  
            message.saveChanges();  
            transport.sendMessage(message, message.getAllRecipients());  
            transport.close();  
              
            return true;  
        }catch (Exception e){  
            return false;  
        }  
    }  
          
}