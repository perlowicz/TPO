package com.example.Library.controller;

import com.example.Library.model.Pozycja;
import com.example.Library.model.PozycjaDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/displayAll")
public class BooksController extends HttpServlet {

    private final PozycjaDao pozycjaDao = new PozycjaDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Pozycja> allPozycjas = pozycjaDao.findAll();
        request.setAttribute("pozycje", allPozycjas);
        request.getRequestDispatcher("/WEB-INF/viewPage.jsp").forward(request, response);
    }
}
