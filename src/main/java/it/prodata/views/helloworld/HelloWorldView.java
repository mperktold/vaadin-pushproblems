package it.prodata.views.helloworld;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import it.prodata.views.main.MainView;

@Route(value = "hello", layout = MainView.class)
@RouteAlias(value = "", layout = MainView.class)
@PageTitle("Hello World")
@CssImport("styles/views/helloworld/hello-world-view.css")
public class HelloWorldView extends HorizontalLayout {

	private final TextField name;
	private final Button sayHello;

	private ScheduledExecutorService scheduler;

	public HelloWorldView() {
		setId("hello-world-view");
		name = new TextField("Your name");
		sayHello = new Button("Say hello");
		add(name, sayHello);
		sayHello.addClickListener(e -> {
			getUI().ifPresent(ui -> {
				scheduler.schedule(
					() -> ui.access(this::sayHello),
					1, TimeUnit.SECONDS
				);
			});
		});
		setVerticalComponentAlignment(Alignment.END, name, sayHello);
	}

	@Override
	protected void onAttach(AttachEvent attachEvent) {
		scheduler = newSingleThreadScheduledExecutor();
	}

	@Override
	protected void onDetach(DetachEvent detachEvent) {
		scheduler.shutdownNow();
	}

	private void sayHello() {
		Notification.show("Hello " + name.getValue());
	}
}
