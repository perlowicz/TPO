<%--Strona główna--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Strona główna Księgarni internetowej</title>
    <style>
        #panelglowny{
            display: grid;
            place-items: center;
        }
    </style>
</head>
<body>

    <div id="panelglowny">
        <h1>Strona główna</h1>
        <form action="displayAll">
            <button>Wyświetl zawartość księgarni</button>
        </form>
        <form action="search">
            <h3>Wprowadź tytuł książki</h3>
            <input type="text" name="bookTitle">
            <button>Wyszukaj</button>
        </form>
    </div>


</body>
</html>
