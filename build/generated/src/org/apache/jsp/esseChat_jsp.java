package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import esse.chat.persistence.ChatRoomList;
import esse.chat.model.ChatRoom;
import java.text.DateFormat;
import java.util.Date;
import esse.chat.model.Chatter;
import esse.chat.model.Message;

public final class esseChat_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.ArrayList<String>(1);
    _jspx_dependants.add("/pageheader.html");
  }

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			"error.jsp", true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("\n");

    ServletContext context = request.getServletContext();
    String yes = "yes";
    session.setAttribute("exit", yes);

    String nickname = (String) session.getAttribute("nickname");
    Object obj = session.getAttribute("chatterid");
    String id = obj.toString();
    long chatterId = Long.parseLong(id);

    if (nickname != null) {
        ChatRoomList roomlist = (ChatRoomList) context.getAttribute("myListRooms");

        String roomname = (String) session.getAttribute("roomname");

//        int refreshAfter = 10000; // 10 seconds
//        String t = application.getInitParameter("refreshAfter"); // gets segundos
//        if (t != null) {
//            try {
//                refreshAfter = Integer.parseInt(t);
//                refreshAfter = refreshAfter * 1000; // converte para milisegundos
//            } catch (NumberFormatException nfe) {
//            }
//        }

      out.write("\t\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <title>ESSE Chat - ");
      out.print(nickname);
      out.write(' ');
      out.write('(');
      out.print(roomname);
      out.write(") </title>\n");
      out.write("        <meta name=\"Author\" content=\"Equipe ESSE Chat (Edgleyson, Edlas, Saulo e Sérgio)\">\n");
      out.write("        <meta charset=\"UTF-8\">\n");
      out.write("        <link rel=\"stylesheet\" href=\"");
      out.print(request.getContextPath());
      out.write("/chat.css\">\n");
      out.write("        <script type=\"text/javascript\" >\n");
      out.write("            function reload()\n");
      out.write("            {\n");
      out.write("                window.location.reload();\n");
      out.write("            }\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("            function winopen(path)\n");
      out.write("            {\n");
      out.write("                chatterinfo = window.open(path, \"chatterwin\", \"scrollbars=no,resizable=no, width=800, height=600, location=no, toolbar=no, status=no\");\n");
      out.write("                chatterinfo.focus();\n");
      out.write("            }\n");
      out.write("\n");
      out.write("        </script>\n");
      out.write("        <script type=\"text/javascript\" src=\"index.js\"></script>\n");
      out.write("    </head>\n");
      out.write("\n");
      out.write("\n");
      out.write("    <body>\n");
      out.write("        <header>\n");
      out.write("            ");
      out.write("<!DOCTYPE html>\r\n");
      out.write("\r\n");
      out.write("<head>\r\n");
      out.write("<!--<meta http-equiv=\"refresh\" content=\"10\">-->\r\n");
      out.write("<meta name=\"Author\" content=\"Equipe ESSE Chat (Edgleyson, Edlas, Saulo e SÃ©rgio)\">\t\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/chat.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<header id=\"cabecalho\">\r\n");
      out.write("    \r\n");
      out.write("    <img src=\"images/EsseChatAnime.gif\" alt=\"ESSE Chat\">\r\n");
      out.write("\r\n");
      out.write("</header>");
      out.write("\n");
      out.write("        </header>\n");
      out.write("        <aside>       \n");
      out.write("            <form name=\"changeRoom\" method=\"post\" action=\"");
      out.print(request.getContextPath());
      out.write("/listrooms.jsp\">\n");
      out.write("                <input type=\"hidden\" name=\"n\" value=\"");
      out.print(nickname);
      out.write("\">\n");
      out.write("                <input class=\"botoes\"  type=\"button\"  value=\"Escolher Outra Sala\" onClick='window.open(\"listrooms.jsp\", \"_self\")'>\n");
      out.write("            </form>\n");
      out.write("\n");
      out.write("            ");

                Object o = session.getAttribute("status");
                String status = o.toString();
                int chatterStatus = Integer.parseInt(status);
                if (chatterStatus == 3) {
            
      out.write("\n");
      out.write("            <form name=\"find\">\n");
      out.write("                <input class=\"botoes\" type=\"button\" value=\"Cadastrar Professor\" onClick='window.open(\"cadastrarMonitor.jsp?usuario=p\")'>\n");
      out.write("            </form>\n");
      out.write("            <form name=\"find2\">\n");
      out.write("                <input class=\"botoes\" type=\"button\" value=\"Cadastrar Monitor\" onClick='window.open(\"cadastrarMonitor.jsp?usuario=m\")'>\n");
      out.write("            </form>\n");
      out.write("            ");

                }

                ChatRoom chatRoom = null;
                long prof = 0;
                if (roomname != null) {
                    chatRoom = (ChatRoom) roomlist.getRoom(roomname);//cria a nova sala    
                    prof = chatRoom.getProfessor();
                    if (prof == chatterId) {
            
      out.write("\n");
      out.write("            <form name=\"find2\">\n");
      out.write("                <input class=\"botoes\" type=\"button\" value=\"Cadastrar Monitor\" onClick='window.open(\"cadastrarMonitor.jsp?usuario=m\")'>\n");
      out.write("            </form>\n");
      out.write("            ");

                    }
                }
    
            
      out.write("\n");
      out.write("            <form name=\"refresh\">\n");
      out.write("                <input class=\"botoes\" type=\"Button\" value=\"Refresh\" onClick=\"reload()\">\n");
      out.write("            </form>\n");
      out.write("            <form name=\"logout\" action=\"logout.jsp\" method=\"post\" target=\"_top\">\n");
      out.write("                <input class=\"botoes\" type=\"Submit\" value=\"Sair\">\n");
      out.write("            </form>\n");
      out.write("        </aside>\n");
      out.write("        <section class=\"listMsg\">\n");
      out.write("            ");
            
                Chatter chatter = null;
                Message[] messages = null;

                
                    try {

                        chatter = chatRoom.getChatter(chatterId);
                        if (chatRoom != null) {
                            long enteredAt = chatter.getEnteredInRoomAt();
                            if (enteredAt != -1) {
                                messages = chatRoom.getMessages(enteredAt);
                            } else {
                                messages = chatRoom.getMessages(chatter.getLoginTime());
                            }

                        } else {
                            out.write("<b>Sala de chat: " + roomname + " não encontrada!</b>");
                            out.close();
                        }

                    } catch (Exception e) {
                        response.sendRedirect("/error.jsp");
                        System.out.println("Exception: " + e.getMessage());

                        System.err.println(nickname);

                    }
            
      out.write("\n");
      out.write("\n");
      out.write("            <h3><i>");
      out.print(nickname);
      out.write("</i> você está na sala de chat: <b>");
      out.print(roomname);
      out.write("</b></h3>\n");
      out.write("\n");
      out.write("            ");

                boolean professorOnLine = chatRoom.chatterExists(prof);
                if (professorOnLine) {
                    Chatter profChatter = chatRoom.getChatter(prof);
                    String profName = profChatter.getName();
                    String profNick = profChatter.getNick();
                    if (!(profNick.equals(nickname))) {
            
      out.write("\n");
      out.write("            <h2 id=\"prof\">\n");
      out.write("                <font face=\"Arial\" size=\"2\" color=\"red\">Professor: ");
      out.print(profName);
      out.write(" está online</font>\n");
      out.write("            </h2> \n");
      out.write("            ");

                    }
                }
                boolean monitorOnLine = chatRoom.monitorExists(chatter);
                if (monitorOnLine) {
            
      out.write("\n");
      out.write("            <h2 id=\"monit1\">\n");
      out.write("                <font face=\"Arial\" size=\"2\" color=\"red\">Monitor: ");
      out.print(chatter.getName());
      out.write(" está online</font>\n");
      out.write("            </h2> \n");

            }
                
      out.write("\n");
      out.write("                <div id=\"tabelamsgs\"></div>\n");
      out.write("                <table>\n");
      out.write("                    <tbody id=\"tbmsgs\">\n");
      out.write("                        \n");
      out.write("                    </tbody>\n");
      out.write("            </table>\n");
      out.write("        </section>\n");
      out.write("\n");
      out.write("        <nav>\n");
      out.write("            <table id=\"listusers\">\n");
      out.write("                <thead id=\"tbusers\">                    \n");
      out.write("                </thead>\n");
      out.write("                <tbody id=\"tbodyusers\">                    \n");
      out.write("                </tbody>\n");
      out.write("            </table>\n");
      out.write("        </nav>\n");
      out.write("        <footer>\n");
      out.write("            <form id=\"send\" name=\"msg\" action=\"AjaxSendMessage\" method=\"post\">    \n");
      out.write("                <input type=\"text\" id=\"message\" name=\"messagebox\" maxlength=\"300\" size=\"35\">\n");
      out.write("                <input type=\"hidden\" id=\"nick\" name=\"nickname\" value=\"");
      out.print(session.getAttribute("nickname"));
      out.write("\">\n");
      out.write("                <input name=\"submit\" type=\"submit\" value=\"Enviar\">   \n");
      out.write("            </form><br>\n");
      out.write("        </footer>\n");
      out.write("    </body>\n");
      out.write("    \n");
      out.write("</html>\n");

    } else {
        response.sendRedirect("login.jsp");
    }

    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
