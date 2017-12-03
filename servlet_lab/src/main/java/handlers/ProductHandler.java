package handlers;

import database.DBService;
import models.Product;
import models.ProductBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductHandler extends HttpServlet {
    final Pattern getPattern = Pattern.compile("/product/(\\d+)");
    final Pattern addPattern = Pattern.compile("/product/add/(\\d+)");
    final Pattern editPattern = Pattern.compile("/product/edit/(\\d+)");
    final Pattern deletePattern = Pattern.compile("/product/delete/(\\d+)");


    public void init() throws ServletException {
    }

    /**
     * writes beginning of file. Chooses which page to render and calls required method
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        String path = request.getRequestURI();
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeProduct(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            writeAddForm(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = editPattern.matcher(path);
        if (match.matches()) {
            writeEditForm(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        response.sendRedirect("/error");
    }

    /**
     * writes info about product with id {@code id}
     *
     * @param id product to write about
     */
    private void writeProduct(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Product p = DBService.getProduct(id);
            request.setAttribute("product", p);
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/WEB-INF/views/product/index.jsp").forward(request, response);
    }

    /**
     * writes from to add new product
     */
    private void writeAddForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        request.getRequestDispatcher("/WEB-INF/views/product/add.jsp").forward(request, response);
    }

    /**
     * writes from to edit product with id {@code id}
     *
     * @param id product to edit
     */
    private void writeEditForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Product p = DBService.getProduct(id);
            request.setAttribute("product", p);
        } catch (Exception ignored) {
            response.sendRedirect("/error");
        }
        request.getRequestDispatcher("/WEB-INF/views/product/edit.jsp").forward(request, response);
    }

    /**
     * chooses method to use with post
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Matcher match = editPattern.matcher(path);
        if (match.matches()) {
            try {
                editProduct(request, response, Integer.parseInt(match.group(1)));
            } catch (SQLException | ClassNotFoundException ignored) {
                response.sendRedirect("/error");
            }
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            addProduct(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = deletePattern.matcher(path);
        if (match.matches()) {
            deleteProduct(Integer.parseInt(match.group(1)));
        }
        response.sendRedirect("/");
    }

    /**
     * creates new product
     */
    private void addProduct(HttpServletRequest request, HttpServletResponse response, int storageId) throws ServletException, IOException {

        try {
            Product p = new ProductBuilder()
                    .setName(request.getParameter("name"))
                    .setCategory(Product.Category.valueOf(request.getParameter("category")))
                    .setProductionDate(LocalDate.parse(request.getParameter("production_date")))
                    .setExpiration(LocalDate.parse(request.getParameter("expiration_date")))
                    .setPrice(Double.parseDouble(request.getParameter("price")))
                    .build();
            DBService.addProduct(p, storageId);
            response.sendRedirect("/storage/" + storageId);
        } catch (Exception ignored) {
            request.setAttribute("error", true);
            writeAddForm(request, response, storageId);
        }
    }

    /**
     * updates fields of product with id {@code id}
     *
     * @param id id of product to edit
     * @return whether updated successfully
     */
    private void editProduct(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, SQLException, ClassNotFoundException {
        Product p = DBService.getProduct(id);
        if (p == null) {
            response.sendRedirect("/product/add/" + id);
        }
        try {
            p.setName(request.getParameter("name"));
            p.setCategory(Product.Category.valueOf(request.getParameter("category")));
            p.setProductionDate(LocalDate.parse(request.getParameter("production_date")));
            p.setExpiration(LocalDate.parse(request.getParameter("expiration_date")));
            p.setPrice(Double.parseDouble(request.getParameter("price")));
            DBService.updateProduct(p);
            response.sendRedirect("/product/" + id);
        } catch (Exception ignored) {
            request.setAttribute("error", true);
            writeEditForm(request, response, id);
        }
    }

    /**
     * @param id product to delete
     */
    private void deleteProduct(int id) {
        try {
            DBService.deleteProduct(id);
        } catch (Exception ignored) {
        }
    }

}
