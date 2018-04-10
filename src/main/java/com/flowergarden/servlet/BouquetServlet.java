package com.flowergarden.servlet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.service.BouquetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/bouquets")
public class BouquetServlet extends AbstractServlet {

    @Autowired
    BouquetService bouquetService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Bouquet> bouquetList = bouquetService.getBouquets();
        req.setAttribute("bouquets", bouquetList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/bouquets.jsp");
        requestDispatcher.forward(req, resp);

    }

}
