package handlers;

import dao.DBDao;
import models.Storage;
import models.StorageBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StorageHandler extends HttpServlet {
    final Pattern getPattern = Pattern.compile("/storage/(\\d+)");
    final Pattern addPattern = Pattern.compile("/storage/add/");
    final Pattern editPattern = Pattern.compile("/storage/edit/(\\d+)");
    final Pattern deletePattern = Pattern.compile("/storage/delete/(\\d+)");

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
            writeStorage(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            writeAddForm(request, response);
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
            Storage s = DBDao.getStorage(id);
            request.setAttribute("storage", s);
            request.getRequestDispatcher("/WEB-INF/views/storage/index.jsp").forward(request, response);
        } catch (Exception ignored) {
            response.sendRedirect("/error");
        }
    }

    /**
     * writes form to create storage for department with {@code id}
     *
     * @param
     */
    private void writeAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
            Storage s = DBDao.getStorage(id);
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
                addStorage(request, response);
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
     * @return id of created Storage or 0, if failed
     */
    private void addStorage(HttpServletRequest request, HttpServletResponse response) throws SQLException, ClassNotFoundException, ServletException, IOException {
        try {
            Storage s = new StorageBuilder().setName(request.getParameter("name")).build();
            DBDao.addStorage(s);
            response.sendRedirect("/");
        }
        catch (Exception ignored) {
            request.setAttribute("error", true);
            writeAddForm(request, response);
        }
    }

    /**
     * updates existing storage
     *
     * @param id id of storage to change
     * @return whether update was successful
     */
    private void editStorage(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        try {
            Storage s = new StorageBuilder().setId(id).setName(request.getParameter("name")).build();
            DBDao.updateStorage(s);
            response.sendRedirect("/storage/" + id);
        }
        catch (Exception ignored) {
            request.setAttribute("error", true);
            writeAddForm(request, response);
        }
    }

    /**
     * @param id id of storage to delete
     */
    private void deleteStorage(int id) {
        try {
            DBDao.deleteStorage(id);
        } catch (Exception ignored) {
        }
    }

}
