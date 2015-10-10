<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/chat.css">
        <meta name="Author" content="Equipe ESSE Chat (Edgleyson, Edlas, Saulo e Sérgio)">
        <title>Recuperação de login</title>
    </head>
    <body>
        <header>
            <%@ include file="pageheader.html" %>
        </header>
        <%  
        String msg = request.getAttribute("msg")==null?"":(String)request.getAttribute("msg");  
        %>  
        <aside>

        </aside>
        <section>
            <div class="emailsenha">
                Digite o e-mail cadastrado nESSE Chat  
                para que possamos reenviar seus dados de login.<br>
                <%if ((msg!=null)&(!msg.equals("null"))&(!msg.equals(""))){ %>  
                    <p><span class="emailerror"><%=msg%></span></p>                 
                <% } %>
                <form name="formulario_email" method="post" action="<%=request.getContextPath()%>/EmailDataServlet"> 
                    <label for="email" class="white">Email: </label>
                    <input type="email" name="email" id="email" value="" placeholder="nome@email.com" required>
                    <input type="submit" class="emailbotao" name="op" value="Enviar"><br>
                </form>
                
            </div>
                <div class="login">
                <a href="login.jsp">Voltar para página de login</a>
                </div>
        </section>
    </body>
</html>
