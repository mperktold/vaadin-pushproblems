package it.prodata;

import org.atmosphere.cpr.ApplicationConfig;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;

import com.vaadin.flow.server.InitParameters;
import com.vaadin.flow.server.VaadinServlet;

public class Main {

	public static void main(String[] args) throws Exception {
		var webApp = new WebAppContext();
		webApp.setContextPath("/vaadin/");
		webApp.setBaseResource(Resource.newClassPathResource("META-INF"));
		webApp.setAttribute(WebInfConfiguration.CONTAINER_JAR_PATTERN, ".*");
		webApp.setConfigurationDiscovered(true);
		webApp.getServletContext().setExtendedListenerTypes(true);
		webApp.setThrowUnavailableOnStartupException(true);

		webApp.setConfigurations(new Configuration[] {
            new WebInfConfiguration(),
            new WebXmlConfiguration(),
            new MetaInfConfiguration(),
            new FragmentConfiguration(),
            new AnnotationConfiguration(),
            new JettyWebXmlConfiguration()
		});

		ServletHolder holder = webApp.addServlet(VaadinServlet.class, "/*");
		holder.setAsyncSupported(true);
		holder.setInitParameter(InitParameters.SERVLET_PARAMETER_ENABLE_PNPM, Boolean.toString(true));
		holder.setInitParameter(ApplicationConfig.BROADCASTER_ASYNC_WRITE_THREADPOOL_MAXSIZE, "-1");

		var server = new Server();
		server.setHandler(webApp);
		ServerConnector connector = new ServerConnector(server);
		connector.setPort(8080);
		server.addConnector(connector);
		server.setStopAtShutdown(true);
		server.start();
		server.join();
	}
}
