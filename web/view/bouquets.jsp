<%@ page import="java.util.List" %>
<%@ page import="com.flowergarden.model.bouquet.Bouquet" %>
<%@ page import="com.flowergarden.model.flowers.GeneralFlower" %>
<%@ page import="com.flowergarden.model.flowers.Rose" %>
<%@ page import="com.flowergarden.model.flowers.Chamomile" %><%--
  Created by IntelliJ IDEA.
  User: andrew
  Date: 3/29/18
  Time: 19:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bouquets list</title>
</head>
<body>

<style>
    #theader {
        background-color: bisque
    }
</style>

<header>
    <h2>Bouquet list</h2>
</header>


<table border="1">
    <thead id="theader">
    <th>Id</th>
    <th>Name</th>
    <th>Assemble price</th>
    <th>Flowers</th>
    </thead>
    <tbody>
    <% List<Bouquet> bouquetList = (List<Bouquet>) request.getAttribute("bouquets");
        for (Bouquet bouquet : bouquetList) {
    %>
    <tr>
        <td align="left"><%=bouquet.getId()%>
        </td>
        <td align="left"><%=bouquet.getName()%>
        </td>
        <td align="left"><%=bouquet.getAssemblePrice()%>
        </td>
        <td align="left">
            <table>
                <thead id="theader">
                <th>Id</th>
                <th>Name</th>
                <th>Price</th>
                <th>Freshness</th>
                <th>Length</th>
                <th>Spike</th>
                <th>Petals</th>
                </thead>
                <tbody>
                <% List<GeneralFlower> flowerlist = (List) bouquet.getFlowers();
                    for (GeneralFlower flower : flowerlist) {
                %>
                <tr>
                    <td align="left"><%=flower.getId()%>
                    </td>
                    <td align="left"><%=flower.getName()%>
                    </td>
                    <td align="left"><%=flower.getPrice()%>
                    </td>
                    <td align="left"><%=flower.getFreshness().getFreshness()%>
                    </td>
                    <td align="left"><%=flower.getLenght()%>
                    </td>
                    <td align="left"><%=(flower instanceof Rose) ? ((Rose) flower).isSpike() : false%>
                    </td>
                    <td align="left"><%=(flower instanceof Chamomile) ? ((Chamomile) flower).getPetals() : 0%>
                    </td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </td>


    </tr>
    <%
        }
    %>
    </tbody>
</table>

</body>
</html>
