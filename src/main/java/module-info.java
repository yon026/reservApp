module com.reservadecanchas.app {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
   
    exports com.reservadecanchas.app;
    exports com.reservadecanchas.util ;
    exports com.reservadecanchas.controller;    
    exports com.reservadecanchas.persistence;

    opens com.reservadecanchas.controller to javafx.fxml;
    opens com.reservadecanchas.util to javafx.fxml;
    opens com.reservadecanchas.app to javafx.fxml;
    opens com.reservadecanchas.persistence to javafx.fxml;
    opens com.reservadecanchas.model to javafx.base;
}
