package handlers;

import java.io.PrintWriter;

public class GeneralWriter {
    /**
     * @param writer writes beginning of document
     */
    public static void writeStart(PrintWriter writer) {
        writer.write("<!Doctype HTML>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css\" integrity=\"sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M\" crossorigin=\"anonymous\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<nav class=\"navbar navbar-expand-lg navbar-light bg-light\">\n" +
                "  <a class=\"navbar-brand\" href=\"/\">University</a>\n" +
                "  <button class=\"navbar-toggler\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarSupportedContent\" aria-controls=\"navbarSupportedContent\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n" +
                "    <span class=\"navbar-toggler-icon\"></span>\n" +
                "  </button>\n" +
                "\n" +
                "  <div class=\"collapse navbar-collapse\" id=\"navbarSupportedContent\">\n" +
                "    <ul class=\"navbar-nav mr-auto\">\n" +
                "      <li class=\"nav-item active\">\n" +
                "        <a class=\"nav-link\" href=\"/\">Home <span class=\"sr-only\">(current)</span></a>\n" +
                "      </li>\n" +
                "    </ul>\n" +
                "    <form class=\"form-inline my-2 my-lg-0\" method=\"post\" action=\"/\">\n" +
                "      <input class=\"form-control mr-sm-2\" type=\"text\" name=\"search\" placeholder=\"Search\" aria-label=\"Search\">\n" +
                "      <button class=\"btn btn-outline-success my-2 my-sm-0\" type=\"submit\">Search</button>\n" +
                "    </form>\n" +
                "  </div>\n" +
                "</nav>\n" +
                "<main>\n<div class=\"container\">\n");
    }

    /**
     * @param writer writes end of document
     */
    public static void writeEnd(PrintWriter writer) {
        writer.write("\n</div>\n</main>\n<script src=\"https://code.jquery.com/jquery-3.2.1.slim.min.js\" integrity=\"sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js\" integrity=\"sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4\" crossorigin=\"anonymous\"></script>\n" +
                "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js\" integrity=\"sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1\" crossorigin=\"anonymous\"></script>\n" +
                "</body>");
    }
}
