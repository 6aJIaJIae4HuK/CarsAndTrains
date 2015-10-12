package sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Predicate;

public class Main extends Application {

    private Stage primaryStage;
    private GridPane rootLayout;
    private ObservableList<Storage> storage = FXCollections.observableArrayList();

    private ExecutorService trainsExecutor;
    private ObservableList<Train> trains = FXCollections.observableArrayList();

    private ExecutorService carsIExecutor;
    private ExecutorService carsIIExecutor;
    private ExecutorService carsIIIExecutor;

    private ObservableList<Car> carsI = FXCollections.observableArrayList();
    private ObservableList<Car> carsII = FXCollections.observableArrayList();
    private ObservableList<Car> carsIII = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("fdfd");

        initRootLayout();
    }

    public void initRootLayout() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource("sample.fxml"));
            rootLayout = (GridPane)fxmlLoader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

            trainsExecutor = Executors.newFixedThreadPool(1);

            carsIExecutor = Executors.newSingleThreadExecutor();
            carsIIExecutor = Executors.newSingleThreadExecutor();
            carsIIIExecutor = Executors.newSingleThreadExecutor();
            storage.add(new Storage(3, 3, 3));

            Controller controller = fxmlLoader.getController();
            controller.setMain(this);

            Timer timer = new Timer();
            TimerTask deleteExtraLines = new TimerTask() {
                @Override
                public void run() {
                    trains.removeIf(new Predicate<Train>() {
                        @Override
                        public boolean test(Train train) {
                            return train.isEmpty();
                        }
                    });
                    carsI.removeIf(new Predicate<Car>() {
                        @Override
                        public boolean test(Car car) {
                            return car.isFull();
                        }
                    });
                    carsII.removeIf(new Predicate<Car>() {
                        @Override
                        public boolean test(Car car) {
                            return car.isFull();
                        }
                    });
                    carsIII.removeIf(new Predicate<Car>() {
                        @Override
                        public boolean test(Car car) {
                            return car.isFull();
                        }
                    });
                }
            };

            timer.schedule(deleteExtraLines, 1000, 500);

            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    trainsExecutor.shutdownNow();
                    carsIExecutor.shutdownNow();
                    carsIIExecutor.shutdownNow();
                    carsIIIExecutor.shutdownNow();
                    timer.cancel();
                }
            });
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Storage> getStorage() {
        return storage;
    }

    public ExecutorService getTrainsExecutor() {
        return trainsExecutor;
    }

    public ObservableList<Train> getTrains() {
        return trains;
    }

    public ExecutorService getCarsIExecutor() {
        return carsIExecutor;
    }

    public ExecutorService getCarsIIExecutor() {
        return carsIIExecutor;
    }

    public ExecutorService getCarsIIIExecutor() {
        return carsIIIExecutor;
    }

    public ObservableList<Car> getCarsI() {
        return carsI;
    }

    public ObservableList<Car> getCarsII() {
        return carsII;
    }

    public ObservableList<Car> getCarsIII() {
        return carsIII;
    }
}
