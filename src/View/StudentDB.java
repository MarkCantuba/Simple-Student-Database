package View;

import Controller.Sql_Controller;
import Entities.Student;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.Connection;
import java.sql.ResultSet;

public class StudentDB extends Application {
    private TableView tableView = new TableView();
    private ObservableList<Student> studentList = FXCollections.observableArrayList();
    private BorderPane borderPane = new BorderPane();
    protected static String url = "jdbc:mysql://localhost:3306/students";
    protected static Connection conn = Sql_Controller.connectToDatabase(url);
    protected static ResultSet resultSet = Sql_Controller.getResultSet(conn, "universitydb");

    private static void createList(ObservableList<Student> studentList) {

        try {
            while (resultSet.next()) {
                int id = resultSet.getInt("StudentID");
                String FirstName = resultSet.getString("FirstName");
                String LastName = resultSet.getString("LastName");
                Double gpa = resultSet.getDouble("GPA");
                studentList.add(new Student(id, gpa, FirstName, LastName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void start(Stage primaryStage) throws Exception {
        createList(studentList);

        TableColumn idCol = new TableColumn("Student ID");
        idCol.setMinWidth(200);
        idCol.setCellValueFactory(new PropertyValueFactory<Student, Integer>("studentId"));

        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("firstName"));

        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(200);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("lastName"));

        TableColumn gpaCol = new TableColumn("GPA");
        gpaCol.setMinWidth(200);
        gpaCol.setCellValueFactory(new PropertyValueFactory<Student, Double>("gpa"));

        tableView.getColumns().addAll(idCol, firstNameCol, lastNameCol, gpaCol);
        tableView.getStyleClass().add("Times New Roman");
        tableView.setItems(studentList);

        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.ONE_PASS_BOX);
        ds.setColor(Color.LIMEGREEN);
        ds.setSpread(0.1);
        ds.setRadius(10);

        Text text = new Text("**** MySQL Student Database ****");
        text.setFont(Font.font("Times New roman" , 50));
        text.setStyle("-fx-fill: White");
        text.setEffect(new Glow(3));
        text.setEffect(ds);

        VBox vBox = new VBox();

        Button loadDB = new Button("Refresh Database");

        loadDB.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
                "-fx-font-family: 'Times New Roman';-fx-font-size: 20");
        loadDB.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.refresh();
            }
        });

        loadDB.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.ORANGERED);
                loadDB.setEffect(dropShadow);
            }
        });

        loadDB.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadDB.setEffect(null);
            }
        });

        loadDB.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadDB.setOpacity(0.7);
            }
        });

        loadDB.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadDB.setOpacity(1);
            }
        });

        Button addStudent = new Button("Add");

        addStudent.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
                "-fx-font-family: 'Times New Roman';-fx-font-size: 20");

        addStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });

        addStudent.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.ORANGERED);
                addStudent.setEffect(dropShadow);
            }
        });

        addStudent.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addStudent.setEffect(null);
            }
        });

        addStudent.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addStudent.setOpacity(0.7);
            }
        });

        addStudent.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               addStudent.setOpacity(1);
            }
        });

        TextField studentID = new TextField();
        studentID.setPromptText("Student ID");
        studentID.setMinWidth(200);

        TextField firstName = new TextField();
        firstName.setPromptText("First Name");
        firstName.setMinWidth(300);

        TextField lastName = new TextField();
        lastName.setPromptText("Last Name");
        lastName.setMinWidth(300);

        TextField gpa = new TextField();
        gpa.setPromptText("GPA");
        gpa.setMinWidth(100);

        addStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int sid = Integer.parseInt(studentID.getText());
                double gp = Double.parseDouble(gpa.getText());
                String firstnm = firstName.getText();
                String lastnm = lastName.getText();
                studentID.clear();
                gpa.clear();
                firstName.clear();
                lastName.clear();
                Student newStudent = new Student(sid, gp, firstnm, lastnm);
                Sql_Controller.AddStudent(newStudent, conn, "universitydb");
                studentList.add(newStudent);

            }
        });

        Button removeStudent = new Button("Remove Selected Student");

        removeStudent.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
                "-fx-font-family: 'Times New Roman';-fx-font-size: 20");
        removeStudent.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Student student = (Student) tableView.getSelectionModel().getSelectedItem();
                tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
                Sql_Controller.RemoveStudent(student.getStudentId(), conn, "universitydb");
//                Stage infoWindow = new Stage();
//                BorderPane form = new BorderPane();
//                Scene scene = new Scene(form);
//
//                primaryStage.hide();
//                infoWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                    @Override
//                    public void handle(WindowEvent event) {
//                        primaryStage.show();
//                    }
//                });
//
//
//                HBox hBox = new HBox();
//                hBox.setMinWidth(300);
//                hBox.setMinHeight(50);
//                hBox.setStyle("-fx-background-color: Linear-Gradient(red, white)");
//
//                Text prompt = new Text("Please enter student number to be deleted:");
//                prompt.setFont(new Font("Times New Roman", 16));
//
//                TextField textField = new TextField();
//
//                VBox enterInfo = new VBox(prompt, textField);
//                enterInfo.setAlignment(Pos.TOP_LEFT);
//
//                enterInfo.setSpacing(10);
//                enterInfo.setPadding(new Insets(10,10,10,10));
//
//                HBox hBox1 = new HBox();
//                hBox1.setMinWidth(300);
//                hBox1.setMinHeight(50);
//                hBox1.setStyle("-fx-background-color: Linear-Gradient(white, red)");
//
//                Button cancel = new Button("Cancel");
//                cancel.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
//                        "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
//                        "-fx-font-family: 'Times New Roman';-fx-font-size: 20");
//                cancel.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        primaryStage.show();
//                        infoWindow.close();
//                    }
//                });
//                cancel.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        DropShadow dropShadow = new DropShadow();
//                        dropShadow.setColor(Color.ORANGERED);
//                        cancel.setEffect(dropShadow);
//                    }
//                });
//
//                cancel.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        cancel.setEffect(null);
//                    }
//                });
//
//                cancel.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        cancel.setOpacity(0.7);
//                    }
//                });
//
//                cancel.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        cancel.setOpacity(1);
//                    }
//                });
//
//                Button ok = new Button("Ok");
//                ok.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
//                        "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
//                        "-fx-font-family: 'Times New Roman';-fx-font-size: 20");
//
//                ok.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        int studentId = Integer.parseInt(textField.getText());
//                        if (Sql_Controller.exists(conn, studentId)) {
//                            tableView.getSelectionModel().select(Sql_Controller.obtainStudent(conn, studentId));
//                            infoWindow.close();
//                            primaryStage.show();
//                        }
//                    }
//                });
//
//                ok.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        DropShadow dropShadow = new DropShadow();
//                        dropShadow.setColor(Color.ORANGERED);
//                        ok.setEffect(dropShadow);
//                    }
//                });
//
//                ok.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        ok.setEffect(null);
//                    }
//                });
//
//                ok.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        ok.setOpacity(0.7);
//                    }
//                });
//
//                ok.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
//                    @Override
//                    public void handle(MouseEvent event) {
//                        ok.setOpacity(1);
//                    }
//                });
//
//                hBox1.getChildren().addAll(ok, cancel);
//                hBox1.setPadding(new Insets(10,10,10,0));
//                hBox1.setAlignment(Pos.BOTTOM_RIGHT);
//                hBox1.setSpacing(10);
//
//                form.topProperty().setValue(hBox);
//                form.bottomProperty().setValue(hBox1);
//                form.centerProperty().setValue(enterInfo);
//
//                infoWindow.setScene(scene);
//                infoWindow.show();
            }
        });

        removeStudent.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.ORANGERED);
                removeStudent.setEffect(dropShadow);
            }
        });

        removeStudent.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeStudent.setEffect(null);
            }
        });

        removeStudent.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeStudent.setOpacity(0.7);
            }
        });

        removeStudent.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                removeStudent.setOpacity(1);
            }
        });

        Button exit = new Button("Exit");

        exit.setStyle("-fx-background-color: linear-gradient(#f0ff35, #a9ff00), " +
                "radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%); " +
                "-fx-font-family: 'Times New Roman';-fx-font-size: 20");

        exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                primaryStage.close();
            }
        });

        exit.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                DropShadow dropShadow = new DropShadow();
                dropShadow.setColor(Color.ORANGERED);
                exit.setEffect(dropShadow);
            }
        });

        exit.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               exit.setEffect(null);
            }
        });

        exit.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exit.setOpacity(0.7);
            }
        });

        exit.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exit.setOpacity(1);
            }
        });

        HBox hBox = new HBox();
        HBox hBox1 = new HBox(tableView);
        HBox hBox2 = new HBox(studentID, firstName, lastName, gpa, addStudent);


        hBox.getChildren().add(text);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setStyle("-fx-background-color: Linear-Gradient(red, white)");

        hBox1.setPadding(new Insets(10,10,10,10));
        hBox1.setStyle("-fx-background-color: white");

        hBox2.setAlignment(Pos.BOTTOM_LEFT);
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10, 10, 10,10));
        hBox2.setStyle("-fx-background-color: Linear-Gradient(white, red)");

        vBox.setPadding(new Insets(0,10,0,10));
        vBox.setSpacing(50);
        vBox.setStyle("-fx-background-color: white");
        vBox.getChildren().addAll(loadDB, removeStudent, exit);
        vBox.setAlignment(Pos.CENTER);

        borderPane.rightProperty().setValue(vBox);

        borderPane.topProperty().setValue(hBox);

        borderPane.centerProperty().setValue(hBox1);

        borderPane.bottomProperty().setValue(hBox2);

        Scene scene = new Scene(borderPane);
        primaryStage.setMinWidth(700);
        primaryStage.setMinHeight(500);
        primaryStage.setTitle("Student Database System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
