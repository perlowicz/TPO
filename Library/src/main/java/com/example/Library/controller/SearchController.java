package com.example.Library.controller;

import com.example.Library.model.Pozycja;
import com.example.Library.model.PozycjaDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/search")
public class SearchController extends HttpServlet {

    private final PozycjaDao pozycjaDao = new PozycjaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Pozycja pozycja = pozycjaDao.findOne(request.getParameter("bookTitle"));
        if (pozycja == null){
            request.setAttribute("znalezionaPozycja", pozycja);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } else {
            request.setAttribute("znalezionaPozycja", pozycja);
            request.getRequestDispatcher("/WEB-INF/searchPage.jsp").forward(request, response);
        }
    }
}
