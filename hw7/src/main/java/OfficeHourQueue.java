import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * A program designed in JavaFX that serves as a queue for students going to
 * office hours. Students can add themselves to the back of the queue, and TAs
 * can dequeue from the head of the queue by entering a password when they
 * wish to take the next student.
 *
 * @author Justin Wurst
 * @version 11-27-18
 */
public class OfficeHourQueue extends Application {

    private LinkedQueue<String> students;
    private int largestNo;
    private TextInputDialog privAction;

    /**
     * Generates the window for the application. At the top are two lines of
     * text which display the length of the queue and its longest length.
     * Then, several lines list the names of the students in the queue. At the
     * bottom is a text entry field, a button for adding to queue, and a
     * password-protected dequeue feature.
     *
     * @param stage The stage to which all the objects of the application are
     *              added.
     */
    @Override public void start(Stage stage) {
        privAction = new TextInputDialog("");
        privAction.setTitle("Privilege Check");
        privAction.setHeaderText("Confirmation");
        privAction.setContentText("Please enter password:");

        Text curSize = new Text(0, 0, "Current number of students in queue: 0");

        largestNo = 0;
        Text largestSize = new Text(0, 0,
                                "Largest number of students in queue: 0");

        students = new LinkedQueue<>();
        ListView<String> listView = new ListView<String>(students);

        TextField inputField = new TextField();

        Button addButton = new Button();
        addButton.setText("Add Student");
        Button rmButton = new Button();
        rmButton.setText("Dequeue Student");

        addButton.setOnAction(e -> {
                if (inputField.getText().trim().length() == 0) {
                    inputField.setText("");
                    inputField.requestFocus();
                    return;
                }
                students.add(inputField.getText());
                curSize.setText(curSize.getText().substring(0,
                        curSize.getText().indexOf(':') + 2) + students.size());
                largestNo = Math.max(largestNo, students.size());
                largestSize.setText(largestSize.getText().substring(0,
                        largestSize.getText().indexOf(':') + 2) + largestNo);
                inputField.setText("");
                inputField.requestFocus();
            });

        rmButton.setOnAction(e -> {
                Optional<String> result = privAction.showAndWait();
                result.ifPresent(pw -> {
                        if (pw.equals("CS1321R0X")) {
                            students.remove(0);
                            curSize.setText(curSize.getText().substring(0,
                                    curSize.getText().indexOf(':') + 2)
                                    + students.size());
                        } else {
                            privAction.close();
                        }
                        privAction.getEditor().clear();
                    });
            });

        addButton.disableProperty()
                .bind(Bindings.isEmpty(inputField.textProperty()));
        rmButton.disableProperty()
                .bind(Bindings.isEmpty(students));

        HBox entryBox = new HBox();
        entryBox.setPrefWidth(560);
        HBox.setHgrow(inputField, Priority.ALWAYS);
        HBox.setHgrow(addButton, Priority.ALWAYS);
        HBox.setHgrow(rmButton, Priority.ALWAYS);
        inputField.setMaxWidth(280);
        addButton.setMaxWidth(140);
        rmButton.setMaxWidth(140);
        entryBox.getChildren().addAll(inputField, addButton, rmButton);

        VBox vbox = new VBox();
        vbox.setPrefHeight(400);
        vbox.getChildren().addAll(curSize, largestSize, listView, entryBox);

        Group root = new Group();
        root.getChildren().add(vbox);
        Scene scene = new Scene(root, 560, 400);
        stage.setScene(scene);
        stage.setTitle("CS 1321 Office Hours Queue");
        stage.show();
    }
}
