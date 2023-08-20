module com.example.emotionalsong_client {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.emotionalsong_client to javafx.fxml;
    exports com.example.emotionalsong_client;
}