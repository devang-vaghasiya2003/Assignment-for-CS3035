package unb.cs3035.assignment3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static final int SHAPE_LENGTH = 25;
    public static final Model model = new Model(SHAPE_LENGTH);
    public static final InteractionModel iModel = new InteractionModel();
    public static final View view = new View();
    public static final Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage) throws Exception {

        Scene scene = new Scene(view,400,400);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
