<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Księgarnia</title>
    <style>
        .pozycja {
            border-style: solid;
            border-width: 1px;
            margin: 20px;
            padding: 20px;
        }
    </style>
</head>
<body>

    <h1>Księgarnia internetowa</h1>
    <c:forEach var="pozycja" items="${requestScope.pozycje}">
        <section class="recipe">
            <h2>TYTUŁ: "<c:out value="${pozycja.tytul}"/>"</h2>
            <p>ISBN: <c:out value="${pozycja.ISBN}"/></p>
            <p>AUTOR: ${pozycja.autorName}</p>
            <p>WYDAWCA: <c:out value="${pozycja.wydawcaName}"/></p>
            <p>ROK: <c:out value="${pozycja.rok}"/></p>
            <p>CENA: <c:out value="${pozycja.cena}"/> zł</p>
        </section>
    </c:forEach>


</body>
</html>
