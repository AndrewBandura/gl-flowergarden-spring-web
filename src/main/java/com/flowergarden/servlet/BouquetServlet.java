package com.flowergarden.servlet;

import com.flowergarden.model.bouquet.Bouquet;
import com.flowergarden.service.BouquetService;
import com.flowergarden.service.impl.BouquetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class BouquetServlet extends HttpServlet {

    @Autowired
    BouquetService bouquetService;

    @Override

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        bouquetService = new BouquetServiceImpl();

        List<Bouquet> bouquetList = bouquetService.getBouquets();
        req.setAttribute("bouquets", bouquetList);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/bouquets.jsp");
        requestDispatcher.forward(req, resp);

    }


//    public void service(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//        PrintWriter writer = response.getWriter();
//        writer.println("Hello, World!");
//        writer.close();
//    }
}
