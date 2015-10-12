package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

public class Controller {
    @FXML
    private TableView<Storage> StorageTable;

    @FXML
    private PTableColumn<Storage, Number> StorageI;

    @FXML
    private PTableColumn<Storage, Number> StorageII;

    @FXML
    private PTableColumn<Storage, Number> StorageIII;

    @FXML
    private TableView<Train> TrainTable;

    @FXML
    private PTableColumn<Train, Number> TrainI;

    @FXML
    private PTableColumn<Train, Number> TrainII;

    @FXML
    private PTableColumn<Train, Number> TrainIII;

    @FXML
    private TableView<Car> CarITable;

    @FXML
    private TableView<Car> CarIITable;

    @FXML
    private TableView<Car> CarIIITable;

    @FXML
    private PTableColumn<Car, Number> CarIColumn;

    @FXML
    private PTableColumn<Car, Number> CarIIColumn;

    @FXML
    private PTableColumn<Car, Number> CarIIIColumn;


    @FXML
    private void initialize() {
        StorageI.setCellValueFactory(cellData -> cellData.getValue().currentLiquidProperty());
        StorageII.setCellValueFactory(cellData -> cellData.getValue().currentLooseProperty());
        StorageIII.setCellValueFactory(cellData -> cellData.getValue().currentPerishableProperty());

        TrainI.setCellValueFactory(cellData -> cellData.getValue().getLiquidCounter());
        TrainII.setCellValueFactory(cellData -> cellData.getValue().getLooseCounter());
        TrainIII.setCellValueFactory(cellData -> cellData.getValue().getPerishableCounter());

        CarIColumn.setCellValueFactory(cellData -> cellData.getValue().getCurrentCounter());
        CarIIColumn.setCellValueFactory(cellData -> cellData.getValue().getCurrentCounter());
        CarIIIColumn.setCellValueFactory(cellData -> cellData.getValue().getCurrentCounter());
    }

    @FXML
    private Button AddTrainButton;

    @FXML
    private Button AddCarIButton;

    @FXML
    private Button AddCarIIButton;

    @FXML
    private Button AddCarIIIButton;

    private Main main;

    @FXML
    private void addTrain() {
        Train newTrain = new Train(2, 2, 1, 2, main.getStorage().get(0));
        main.getTrains().add(newTrain);
        main.getTrainsExecutor().submit(newTrain);
    }

    @FXML
    private void addCarI() {
        Car newCar = new Car(Good.GoodTypes.LIQUIDGOOD, 2, main.getStorage().get(0));
        main.getCarsI().add(newCar);
        main.getCarsIExecutor().submit(newCar);
    }

    @FXML
    private void addCarII() {
        Car newCar = new Car(Good.GoodTypes.LOOSEGOOD, 2, main.getStorage().get(0));
        main.getCarsII().add(newCar);
        main.getCarsIIExecutor().submit(newCar);
    }

    @FXML
    private void addCarIII() {
        Car newCar = new Car(Good.GoodTypes.PERISHABLEGOOD, 2, main.getStorage().get(0));
        main.getCarsIII().add(newCar);
        main.getCarsIIIExecutor().submit(newCar);
    }

    public void setMain(Main main) {
        this.main = main;
        StorageTable.setItems(main.getStorage());
        TrainTable.setItems(main.getTrains());
        CarITable.setItems(main.getCarsI());
        CarIITable.setItems(main.getCarsII());
        CarIIITable.setItems(main.getCarsIII());
    }
}
