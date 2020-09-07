package it.prodata.views.about;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;

import it.prodata.views.main.MainView;

@Route(value = "about", layout = MainView.class)
@PageTitle("About")
@CssImport("styles/views/about/about-view.css")
public class AboutView extends VerticalLayout implements HasUrlParameter<String> {

    private final Label label;

	public AboutView() {
        setId("about-view");
        label = new Label("Content placeholder");
        var paramField = new TextField();
        var applyParamButton = new Button("apply", e -> {
        	e.getSource().getUI().ifPresent(ui -> {
        		ui.navigate(AboutView.class, paramField.getValue());
        	});
        });
		add(paramField, applyParamButton, label);
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
    	label.setText(parameter);
    }
}
