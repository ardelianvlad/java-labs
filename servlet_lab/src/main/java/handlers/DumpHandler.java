package handlers;

import database.DBService;
import services.SerializationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DumpHandler extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Content-disposition", "attachment; filename=dump.json");
        File dump = new File("dump.json");
        try {
            SerializationService.serializeCollection(DBService.getProducts(), new PrintWriter(dump));
        } catch (Exception ignored) {
        }
        OutputStream out = response.getOutputStream();
        FileInputStream in = new FileInputStream(dump);
        byte[] buffer = new byte[4096];
        int length;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
        in.close();
        out.flush();
    }
}

