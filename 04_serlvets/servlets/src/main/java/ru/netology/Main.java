package ru.netology;

import org.apache.catalina.ContainerEvent;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        Tomcat tomcat = new Tomcat();
        Path basedir = null;
        try {
            basedir = Files.createTempDirectory("tomcat");
        } catch (IOException e) {
            System.err.println("Ошибка создания временной директории Servlet контейнера (tomcat)");
            return;
        }
        basedir.toFile().deleteOnExit();

        Connector connector = new Connector();
        connector.setPort(9999);
        tomcat.setConnector(connector);

        tomcat.getHost().setAppBase(".");
        tomcat.addWebapp("", ".");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }

        tomcat.getServer().await();

    }

}
