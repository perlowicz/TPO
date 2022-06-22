<%--Strona wyświetlana po wciśnięciu przyciusku Wyszukaj--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="book" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Wyszukiwarka Książek</title>
</head>
<body>

    <h1>Znaleziono książkę w bazie!</h1>

    <section class="bookDesc">
        <h2>TYTUŁ: "<book:out value="${requestScope.znalezionaPozycja.tytul}"/>"</h2>
        <p>ISBN: <book:out value="${requestScope.znalezionaPozycja.ISBN}"/></p>
        <p>AUTOR: ${requestScope.znalezionaPozycja.autorName}</p>
        <p>WYDAWCA: <book:out value="${requestScope.znalezionaPozycja.wydawcaName}"/></p>
        <p>ROK: <book:out value="${requestScope.znalezionaPozycja.rok}"/></p>
        <p>CENA: <book:out value="${requestScope.znalezionaPozycja.cena}"/> zł</p>
    </section>


</body>
</html>
