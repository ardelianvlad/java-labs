package handlers;

import database.DBService;
import models.Product;
import models.Storage;
import models.StorageBuilder;

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

public class StorageHandler extends HttpServlet {
    final Pattern getPattern = Pattern.compile("/storage/(\\d+)");
    final Pattern addPattern = Pattern.compile("/storage/add/");
    final Pattern editPattern = Pattern.compile("/storage/edit/(\\d+)");
    final Pattern deletePattern = Pattern.compile("/storage/delete/(\\d+)");

    public void init() throws ServletException {
        // Do required initialization
    }

    /**
     * writes beginning of file. Chooses which page to render and calls required method
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        String path = request.getRequestURI();
        PrintWriter out = response.getWriter();
        GeneralWriter.writeStart(out);
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeStorage(request, response, Integer.parseInt(match.group(1)));
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
     * writes information about storage
     *
     * @param id id of storage to write info about
     */
    private void writeStorage(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Storage s = DBService.getStorage(id);
            request.setAttribute("storage", s);
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/WEB-INF/views/storage/index.jsp").forward(request, response);
    }

    /**
     * writes form to create storage for department with {@code id}
     *
     * @param id id of parent
     */
    private void writeAddForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        request.getRequestDispatcher("/WEB-INF/views/storage/add.jsp").forward(request, response);
    }

    /**
     * writes form to change existing storage
     *
     * @param id id of storage
     */
    private void writeEditForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Storage s = DBService.getStorage(id);
            request.setAttribute("storage", s);
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/WEB-INF/views/storage/edit.jsp").forward(request, response);

    }

    /**
     * chooses method to use with post
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Matcher match = editPattern.matcher(path);
        if (match.matches()) {
            editStorage(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            try {
                addStorage(request, response, Integer.parseInt(match.group(1)));
            } catch (SQLException | ClassNotFoundException e) {
                response.sendRedirect("/error");
            }

            return;
        }
        match = deletePattern.matcher(path);
        if (match.matches()) {
            deleteStorage(Integer.parseInt(match.group(1)));
        }
        response.sendRedirect("/");
    }

    /**
     * creates new storage
     *
     * @param storageId
     * @return id of created Storage or 0, if failed
     */
    private void addStorage(HttpServletRequest request, HttpServletResponse response, int storageId) throws SQLException, ClassNotFoundException, ServletException, IOException {

    }

    /**
     * updates existing storage
     *
     * @param id id of storage to change
     * @return whether update was successful
     */
    private void editStorage(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {

    }

    /**
     * @param id id of storage to delete
     */
    private void deleteStorage(int id) {
        try {
            DBService.deleteStorage(id);
        } catch (Exception ignored) {
        }
    }

    public void destroy() {
        // do nothing.
    }
}
