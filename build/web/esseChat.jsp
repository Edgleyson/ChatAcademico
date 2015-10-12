<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" import="esse.chat.persistence.ChatRoomList, esse.chat.model.ChatRoom, java.text.DateFormat, java.util.Date, esse.chat.model.Chatter, esse.chat.model.Message" errorPage="error.jsp"%>

<!DOCTYPE html>

<%
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
%>	
<html>
    <head>
        <title>ESSE Chat - <%=nickname%> (<%=roomname%>) </title>
        <meta name="Author" content="Equipe ESSE Chat (Edgleyson, Edlas, Saulo e Sérgio)">
        <meta charset="UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/chat.css">
        <script type="text/javascript" >
            function reload()
            {
                window.location.reload();
            }



            function winopen(path)
            {
                chatterinfo = window.open(path, "chatterwin", "scrollbars=no,resizable=no, width=800, height=600, location=no, toolbar=no, status=no");
                chatterinfo.focus();
            }

        </script>
        <script type="text/javascript" src="sendMessage.js"></script>
    </head>


    <body>
        <header>
            <%@ include file="pageheader.html" %>
        </header>
        <aside>       
            <form name="changeRoom" method="post" action="<%=request.getContextPath()%>/listrooms.jsp">
                <input type="hidden" name="n" value="<%=nickname%>">
                <input class="botoes"  type="button"  value="Escolher Outra Sala" onClick='window.open("listrooms.jsp", "_self")'>
            </form>

            <%
                Object o = session.getAttribute("status");
                String status = o.toString();
                int chatterStatus = Integer.parseInt(status);
                if (chatterStatus == 3) {
            %>
            <form name="find">
                <input class="botoes" type="button" value="Cadastrar Professor" onClick='window.open("cadastrarMonitor.jsp?usuario=p")'>
            </form>
            <form name="find2">
                <input class="botoes" type="button" value="Cadastrar Monitor" onClick='window.open("cadastrarMonitor.jsp?usuario=m")'>
            </form>
            <%
                }

                ChatRoom chatRoom = null;
                long prof = 0;
                if (roomname != null) {
                    chatRoom = (ChatRoom) roomlist.getRoom(roomname);//cria a nova sala    
                    prof = chatRoom.getProfessor();
                    if (prof == chatterId) {
            %>
            <form name="find2">
                <input class="botoes" type="button" value="Cadastrar Monitor" onClick='window.open("cadastrarMonitor.jsp?usuario=m")'>
            </form>
            <%
                    }
                }

            %>
            <form name="refresh">
                <input class="botoes" type="Button" value="Refresh" onClick="reload()">
            </form>
            <form name="logout" action="logout.jsp" method="post" target="_top">
                <input class="botoes" type="Submit" value="Sair">
            </form>
        </aside>
        <section class="listMsg">
            <%                Chatter chatter = null;
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
            %>



            <div id="tabelamsgs"></div>
            <table>
                <thead id="thmsgs">
                    <tr><th><font face="Arial" size="4" color="green"><i><%=nickname%></i> você está no chat: <b><%=roomname%></b></font></th></tr>
                </thead>
                <tbody id="tbmsgs">

                </tbody>
            </table>
        </section>

        <nav>
            <table id="listusers">
                <thead id="tbusers">                    
                </thead>
                <tbody id="tbodyusers">                    
                </tbody>
            </table>
        </nav>
        <footer>
            <form id="send" name="msg" action="AjaxSendMessage" method="post">    
                <input type="text" id="message" name="messagebox" maxlength="300" size="35">
                <input type="hidden" id="nick" name="nickname" value="<%=session.getAttribute("nickname")%>">
                <input name="submit" type="submit" value="Enviar">   
            </form><br>
        </footer>
    </body>

</html>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>