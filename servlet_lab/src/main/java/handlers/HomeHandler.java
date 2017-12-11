package handlers;

import dao.DBDao;
import models.Product;
import models.Storage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeHandler extends HttpServlet {


    public void init() throws ServletException {

    }

    /**
     * writes beginning of file and calls the method to render page
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        response.setContentType("text/html");
        if (path.equals("/error")) {
            writeError(request, response);
            return;
        }
        writeMain(request, response);
    }

    /**
     * writes list of storage
     */
    private void writeMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Storage> storages;
        try {
            storages = DBDao.getStorages();
        } catch (Exception ignored) {
            storages = new ArrayList<>();
        }
        request.setAttribute("storages", storages);
        request.getRequestDispatcher("/WEB-INF/views/home/index.jsp").forward(request, response);
    }

    /**
     * writes error message
     */
    private void writeError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/home/error.jsp").forward(request, response);

    }

    /**
     * searches storage/product by name
     *
     * @param name name of storage/product to find
     */
    private void writeFind(String name, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = DBDao.getConnection();
            Statement st = conn.createStatement();
            List<Storage> storages = DBDao.searchStorages(name);
            List<Product> products = DBDao.searchProducts(name);
            request.setAttribute("storages", storages);
            request.setAttribute("products", products);
            request.getRequestDispatcher("/WEB-INF/views/home/find.jsp").forward(request, response);
        } catch (Exception ignored) {
            request.getRequestDispatcher("/WEB-INF/views/home/error.jsp").forward(request, response);
        }
    }

    /**
     * @param request search condition
     * @param response redirect to search
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Pattern p = Pattern.compile("/find/");
        Matcher m = p.matcher(path);
        if (m.matches()) {
            writeFind(request.getParameter("search"), request, response);
        }
    }

}
