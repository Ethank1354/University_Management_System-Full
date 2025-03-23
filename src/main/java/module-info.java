module engg1420_project.universitymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.apache.poi.ooxml;
    requires org.apache.logging.log4j;
    requires java.sql;
    requires org.apache.commons.io;

    opens engg1420_project.universitymanagementsystem to javafx.fxml;
    opens engg1420_project.universitymanagementsystem.faculty to javafx.fxml;
    //opens engg1420_project.universitymanagementsystem.student to javafx.fxml;
    //opens engg1420_project.universitymanagementsystem.subject to javafx.fxml;
    //opens engg1420_project.universitymanagementsystem.course to javafx.fxml;
    //opens engg1420_project.universitymanagementsystem.event to javafx.fxml;
    exports engg1420_project.universitymanagementsystem;
    exports engg1420_project.universitymanagementsystem.student;
    opens engg1420_project.universitymanagementsystem.student to javafx.fxml;
    exports engg1420_project.universitymanagementsystem.projectClasses;
    opens engg1420_project.universitymanagementsystem.projectClasses to javafx.fxml;
    exports engg1420_project.universitymanagementsystem.course;
    opens engg1420_project.universitymanagementsystem.course to javafx.fxml;
    exports engg1420_project.universitymanagementsystem.events;
    opens engg1420_project.universitymanagementsystem.events to javafx.fxml;
    exports engg1420_project.universitymanagementsystem.subject;
    opens engg1420_project.universitymanagementsystem.subject to javafx.fxml;
    exports engg1420_project.universitymanagementsystem.login;
    opens engg1420_project.universitymanagementsystem.login to javafx.fxml;
    exports engg1420_project.universitymanagementsystem.empty;
    opens engg1420_project.universitymanagementsystem.empty to javafx.fxml;
}