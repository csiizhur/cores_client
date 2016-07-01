package datecontrol;
import java.text.DateFormat;import java.text.SimpleDateFormat;import java.util.Locale;import javafx.application.Application;import javafx.event.ActionEvent;import javafx.event.EventHandler;import javafx.scene.Group;import javafx.scene.Scene;import javafx.scene.control.Button;import javafx.scene.control.Label;import javafx.scene.control.TextField;import javafx.scene.layout.HBox;import javafx.stage.Stage;
/** * test class  * @author Chain * */
public class Junit extends Application {
    private void init(Stage primaryStage) {        Group root = new Group();        primaryStage.setScene(new Scene(root));        final DatePicker dp = createDatePicker();        final HBox h = new HBox(20);        final Button btn = new Button("OK");        final Label l = new Label("date show ");        TextField text=new TextField();        text=dp.getTextField();        h.getChildren().addAll(dp,btn,l,text);        btn.setOnAction(new EventHandler<ActionEvent>() {			@Override			public void handle(ActionEvent arg0) {				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");				/* dp.getSelectedDate()   get the selected date*/				String l_text =  dateFormat.format(dp.getSelectedDate());				l.setText(l_text);			}		});        primaryStage.setWidth(400);        primaryStage.setHeight(200);
        root.getChildren().add(h);    }

    @Override     public void start(Stage primaryStage) throws Exception {
        init(primaryStage);
        primaryStage.show();
    }
    public static void main(String[] args) { launch(args); }            /**     * create date picker      * @return DatePicker     */    private DatePicker createDatePicker() {		DatePicker datePicker = new DatePicker(Locale.CHINA);		datePicker.getTextField().setEditable(false);		datePicker.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));		datePicker.getCalendarView().todayButtonTextProperty().set("今天");		datePicker.getCalendarView().setShowWeeks(false);		datePicker.getStylesheets().add(Junit.class.getResource("DatePicker.css").toExternalForm());		datePicker.setMaxWidth(90);		return datePicker;	}
}