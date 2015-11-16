
<%@page import="java.util.List"%>
<%@page import="java.sql.Date"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>    
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/chat.css" type="text/css">
        <title>Lista de Arquivos</title>
    </head>
    <body>
        <header>
            <c:import url="pageheader.html" />
        </header>
        <section>
        <table id="listChatters">
            <caption>Lista de Arquivos da sala: </caption>
            <!-- percorre arquivos montando as linhas da tabela -->
            <tr class="pagetitle">
                <th></th>
                <th>Arquivo</th>
                <th>Responsável</th>
                <th>Postado em:</th>
                <th>às:</th>
                <th>Download</th>                
            </tr>
            <c:forEach var="arquivo" items="${arquivosList}" varStatus="id">
                <tr bgcolor="#${id.count % 2 == 0 ? '99FF99' : 'CCFFCC' }">
                    <td>${id.count}</td>
                    <td>${arquivo['nome']}</td>
                    <td>${arquivo['chatter']}</td>
                    <c:if test="${not empty arquivo['timestamp']}">
                    <td><fmt:formatDate value="${arquivo['timestamp']}" pattern="MM/dd/yyyy"/></td>
                    <td><fmt:formatDate value="${arquivo['timestamp']}" pattern="HH:mm"/></td>
                    </c:if>
                    <td>
                        <c:choose>
                        <c:when test="${arquivo['mimeType'] eq 'application/pdf'}">
                            <a href="Download?id=${arquivo['id']}"><img width="25" height="25" src="images/pdf.png" alt="Baixar" /></a>
                        </c:when>
                        <c:when test="${arquivo['mimeType'] eq 'application/msword'}">
                            <a href="Download?id=${arquivo['id']}"><img width="25" height="25" src="images/doc.jpg" alt="Baixar" /></a>
                        </c:when>
                        <c:when test="${arquivo['mimeType'] eq 'application/x-rar-compressed'}">
                            <a href="Download?id=${arquivo['id']}"><img width="25" height="25" src="images/rar.jpg" alt="Baixar" /></a>
                        </c:when>
                        <c:otherwise>
                            <a href="Download?id=${arquivo['id']}">Baixar</a>
                        </c:otherwise>
                    </c:choose>   
                    </td>
                </tr>
            </c:forEach>
        </table> <br/>
        <ul>
            <%
                
            %>
            <li><a href="#" onclick="window.print();" title="Imprimir Listagem">Imprimir</a></li>
            <li><a href="<%=request.getContextPath()%>/listrooms.jsp" title="Voltar para lista de salas">Retornar</a></li>
                <%
                    
                %> 
        </ul>
        </section>
    </body>
</html>
