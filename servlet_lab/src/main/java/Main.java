import handlers.DumpHandler;
import handlers.HomeHandler;
import handlers.ProductHandler;
import handlers.StorageHandler;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.eclipse.jetty.apache.jsp.JettyJasperInitializer;
import org.eclipse.jetty.jsp.JettyJspServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

//Copied from official repo
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        WebAppContext context = new WebAppContext();
        context.setContextPath("/");
        //Enable JSP support
        server.addBean(new JspStarter(context));
        context.addServlet(JettyJspServlet.class, "*.jsp");
        enableStaticFiles(context);
        context.setResourceBase(getWebRootResourceUri().toASCIIString());
        setDefaultRoutes(context);
        //Start server
        server.setHandler(context);
        server.start();
        server.join();
    }

    //Enable static files
    private static void enableStaticFiles(WebAppContext context) throws FileNotFoundException, URISyntaxException {
        ServletHolder holderPwd = new ServletHolder("default", new DefaultServlet());
        holderPwd.setInitParameter("pathInfoOnly", "true");
        holderPwd.setInitParameter("resourceBase", "/webroot/WEB-INF/");
        context.addServlet(holderPwd, "/static/*");
    }

    private static void setDefaultRoutes(WebAppContext context) {
        context.addServlet(HomeHandler.class, "/");
        context.addServlet(HomeHandler.class, "/error/");
        context.addServlet(HomeHandler.class, "/find/*");
        context.addServlet(ProductHandler.class, "/product/*");
        context.addServlet(StorageHandler.class, "/storage/*");
        context.addServlet(DumpHandler.class, "/dump/*");
    }

    private static URI getWebRootResourceUri() throws FileNotFoundException, URISyntaxException {
        String WEBROOT_INDEX = "/webroot/";
        URL indexUri = Main.class.getClassLoader().getClass().getResource(WEBROOT_INDEX);
        if (indexUri == null) {
            throw new FileNotFoundException("Unable to find resource " + WEBROOT_INDEX);
        }
        // Points to wherever /webroot/ (the resource) is
        return indexUri.toURI();
    }


    /**
     * JspStarter for embedded ServletContextHandlers
     * <p>
     * This is added as a bean that is a jetty LifeCycle on the ServletContextHandler.
     * This bean's doStart method will be called as the ServletContextHandler starts,
     * and will call the ServletContainerInitializer for the jsp engine.
     */
    public static class JspStarter extends AbstractLifeCycle implements ServletContextHandler.ServletContainerInitializerCaller {
        JettyJasperInitializer sci;
        ServletContextHandler context;

        public JspStarter(ServletContextHandler context) {
            this.sci = new JettyJasperInitializer();
            this.context = context;
            this.context.setAttribute("org.apache.tomcat.JarScanner", new StandardJarScanner());
        }

        @Override
        protected void doStart() throws Exception {
            ClassLoader old = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(context.getClassLoader());
            try {
                sci.onStartup(null, context.getServletContext());
                super.doStart();
            } finally {
                Thread.currentThread().setContextClassLoader(old);
            }
        }
    }

}