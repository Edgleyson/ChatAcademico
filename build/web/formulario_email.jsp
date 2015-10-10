<%--<%@include file="topo.jsp"%>--%>  
<!DOCTYPE html>
<html>
    <head>
        <title>ESSE Chat: Recuperação de senha</title>
        <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/chat.css">

        <meta name="Author" content="Equipe ESSE Chat (Edgleyson, Edlas, Saulo e Sérgio)">	
    </head>
    <body>
        <header>
            <%@ include file="pageheader.html" %>
        </header>
        <%  
        String msg = request.getAttribute("msg")==null?"":(String)request.getAttribute("msg");  
        %>  

        <aside>
        <form name="formulario_email" method="post" action="manter_dados_email">  
            <table width="800" border="0" align="center" cellspacing="0">  
                <tr>  
                    <td colspan="2"><div align="center">Lembrar Senha</div></td>  
                </tr>  
                <tr>  
                    <td colspan="2"> </td>  
                </tr>  
                <tr>  
                    <td colspan="2"><div align="center">Digite no campo abaixo o e-mail que você cadastrou anteriormente  
                            para que sua senha seja enviada para seu e-mail.</div></td>  
                </tr>  
                <tr>  
                    <td colspan="2"> </td>  
                </tr>  
                <tr>  
                    <td width="304"><div align="right">E-mail:  </div></td>  
                    <td width="492"> <input name="email" type="text" size="50" value=""></td>  
                </tr>  
                <tr>  
                    <td colspan="2"> </td>  
                </tr>  
                <%if ((msg!=null)&(!msg.equals("null"))&(!msg.equals(""))){ %>  
                <tr>  
                    <td colspan="4">
                        <%=msg%><%--<%@include file="msg.jsp"%>--%>
                    </td>  
                </tr>  
                <tr>  
                    <td colspan="4"> </td>  
                </tr>  
                <% } %>  
                <tr>  
                    <td colspan="2"><div align="center">  
                            <input type="submit" name="op" value="Enviar">  

                            <input name="reset" type="reset" id="reset" value="Voltar" onClick="location.href = 'index.jsp'">  
                        </div></td>  
                </tr>  
            </table>  
        </form>
        </aside>
    </body>
</html>
