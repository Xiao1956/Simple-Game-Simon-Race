module com.example.simonrace {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.testng;


    opens com.example.simonrace to javafx.fxml;
    exports com.example.simonrace;
}