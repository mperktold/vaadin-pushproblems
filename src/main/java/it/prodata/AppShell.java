package it.prodata;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.PWA;

@Push
@PWA(name = "PushProblems", shortName = "PushProblems")
public class AppShell implements AppShellConfigurator { }
