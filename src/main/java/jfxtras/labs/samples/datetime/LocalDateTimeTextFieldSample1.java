package jfxtras.labs.samples.datetime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import jfxtras.labs.samples.JFXtrasSampleBase;
import jfxtras.labs.scene.control.LocalDateTimeTextField;
import jfxtras.labs.scene.layout.GridPane;
import jfxtras.labs.scene.layout.VBox;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class LocalDateTimeTextFieldSample1 extends JFXtrasSampleBase
{
    public LocalDateTimeTextFieldSample1() {
        localeDateTimeTextField = new LocalDateTimeTextField();
    }
    final LocalDateTimeTextField localeDateTimeTextField;

    @Override
    public String getSampleName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public String getSampleDescription() {
        return "Basic LocalDateTimeTextField usage";
    }

    @Override
    public Node getPanel(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30, 30, 30, 30));

        root.getChildren().addAll(localeDateTimeTextField);

        return root;
    }

    @Override
    public Node getControlPanel() {
        // the result
        GridPane lGridPane = new GridPane();
        lGridPane.setVgap(2.0);
        lGridPane.setHgap(2.0);

        // setup the grid so all the labels will not grow, but the rest will
        ColumnConstraints lColumnConstraintsAlwaysGrow = new ColumnConstraints();
        lColumnConstraintsAlwaysGrow.setHgrow(Priority.ALWAYS);
        ColumnConstraints lColumnConstraintsNeverGrow = new ColumnConstraints();
        lColumnConstraintsNeverGrow.setHgrow(Priority.NEVER);
        lGridPane.getColumnConstraints().addAll(lColumnConstraintsNeverGrow, lColumnConstraintsAlwaysGrow);
        int lRowIdx = 0;

        // Locale
        {
            lGridPane.add(new Label("Locale"), new GridPane.C().row(lRowIdx).col(0).halignment(HPos.RIGHT));
            final ObservableList<Locale> lLocales = FXCollections.observableArrayList(Locale.getAvailableLocales());
            FXCollections.sort(lLocales,  (o1, o2) -> { return o1.toString().compareTo(o2.toString()); } );
            localeComboBox = new ComboBox( lLocales );
            localeComboBox.converterProperty().set(new StringConverter<Locale>() {
				@Override
				public String toString(Locale locale) {
					return locale == null ? "-" : locale.toString();
				}

				@Override
				public Locale fromString(String s) {
					if ("-".equals(s)) return null;
					// this goes wrong with upper and lowercase, so we do a toString search in the list: return new Locale(s);
					for (Locale l : lLocales) {
						if (l.toString().equalsIgnoreCase(s)) {
							return l;
						}
					}
					throw new IllegalArgumentException(s);
				}
			});
            localeComboBox.setEditable(true);
            lGridPane.add(localeComboBox, new GridPane.C().row(lRowIdx).col(1));
			// once the date format has been set manually, changing the local has no longer any effect, so binding the property is useless
			localeComboBox.valueProperty().addListener( (observable) -> {
				setDateFormat();
			});
        }
        lRowIdx++;

//        // nullAllowed
//        {
//            Label lLabel = new Label("Null allowed");
//            lLabel.setTooltip(new Tooltip("Is the control allowed to hold null (or have no datetime deselected)"));
//            lGridPane.add(lLabel, new GridPane.C().row(lRowIdx).col(0).halignment(HPos.RIGHT));
//            CheckBox lCheckBox = new CheckBox();
//            lGridPane.add(lCheckBox, new GridPane.C().row(lRowIdx).col(1));
//            lCheckBox.selectedProperty().bindBidirectional(localeDateTimeTextField.allowNullProperty());
//        }
//        lRowIdx++;

        // showTime
        {
            Label lLabel = new Label("Show time");
            //lLabel.setTooltip(new Tooltip("Only in SINGLE mode"));
            lGridPane.add(lLabel, new GridPane.C().row(lRowIdx).col(0).halignment(HPos.RIGHT));
            showTimeCheckbox = new CheckBox();
            lGridPane.add(showTimeCheckbox, new GridPane.C().row(lRowIdx).col(1));
            showTimeCheckbox.selectedProperty().addListener((observable) -> {
				setDateFormat();
			});
        }
        lRowIdx++;

//		// highlight
//		{
//			Label lLabel = new Label("Highlighted");
//			lLabel.setTooltip(new Tooltip("All highlighted dates"));
//			lGridPane.add(lLabel, new GridPane.C().row(lRowIdx).col(0).halignment(HPos.RIGHT).valignment(VPos.TOP));
//
//			// text field
//			final LocalDateTimeTextField lLocalDateTimeTextField = new LocalDateTimeTextField();
//			lGridPane.add(lLocalDateTimeTextField, new GridPane.C().row(lRowIdx).col(1));
//			// add button
//			{
//				Button lButton = new Button("add");
//				lGridPane.add(lButton, new GridPane.C().row(lRowIdx).col(2));
//				lButton.onActionProperty().set( (actionEvent) -> {
//					LocalDateTime c = lLocalDateTimeTextField.getLocalDateTime();
//					if (c != null) {
//						localeDateTimeTextField.highlightedLocalDateTimes().add(c);
//						lLocalDateTimeTextField.setLocalDateTime(null);
//					}
//				});
//			}
//
//			lRowIdx++;
//
//			final ListView<LocalDateTime> lListView = new ListView<LocalDateTime>();
//			lListView.setItems(localeDateTimeTextField.highlightedLocalDateTimes());
//			lListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<LocalDateTime>() {
//				@Override
//				public String toString(LocalDateTime o) {
//					DateFormat lDateFormat = localeDateTimeTextField.getShowTime() ? SimpleDateFormat.getDateTimeInstance() : SimpleDateFormat.getDateInstance();
//					return o == null ? "" : lDateFormat.format(o.getTime());
//				}
//
//				@Override
//				public LocalDateTime fromString(String s) {
//					return null;  //never used
//				}
//			}));
//			lGridPane.add(lListView, new GridPane.C().row(lRowIdx).col(1));
//			// remove button
//			{
//				Button lButton = new Button("remove");
//				lGridPane.add(lButton, new GridPane.C().row(lRowIdx).col(2).valignment(VPos.TOP));
//				lButton.onActionProperty().set( (actionEvent) -> {
//					LocalDateTime c = lListView.getSelectionModel().getSelectedItem();
//					if (c != null) {
//						localeDateTimeTextField.highlightedLocalDateTimes().remove(c);
//					}
//				});
//			}
//
//		}
//		lRowIdx++;
//
//		// disabled
//		{
//			Label lLabel = new Label("Disabled");
//			lLabel.setTooltip(new Tooltip("All disabled dates"));
//			lGridPane.add(lLabel, new GridPane.C().row(lRowIdx).col(0).halignment(HPos.RIGHT).valignment(VPos.TOP));
//
//			// text field
//			final LocalDateTimeTextField lLocalDateTimeTextField = new LocalDateTimeTextField();
//			lGridPane.add(lLocalDateTimeTextField, new GridPane.C().row(lRowIdx).col(1));
//			// add button
//			{
//				Button lButton = new Button("add");
//				lGridPane.add(lButton, new GridPane.C().row(lRowIdx).col(2));
//				lButton.onActionProperty().set( (actionEvent) -> {
//					LocalDateTime c = lLocalDateTimeTextField.getLocalDateTime();
//					if (c != null) {
//						localeDateTimeTextField.disabledLocalDateTimes().add(c);
//						lLocalDateTimeTextField.setLocalDateTime(null);
//					}
//				});
//			}
//
//			lRowIdx++;
//
//			final ListView<LocalDateTime> lListView = new ListView<LocalDateTime>();
//			lListView.setItems(localeDateTimeTextField.disabledLocalDateTimes());
//			lListView.setCellFactory(TextFieldListCell.forListView(new StringConverter<LocalDateTime>() {
//				@Override
//				public String toString(LocalDateTime o) {
//					DateFormat lDateFormat = localeDateTimeTextField.getShowTime() ? SimpleDateFormat.getDateTimeInstance() : SimpleDateFormat.getDateInstance();
//					return o == null ? "" : lDateFormat.format(o.getTime());
//				}
//
//				@Override
//				public LocalDateTime fromString(String s) {
//					return null;  //never used
//				}
//			}));
//			lGridPane.add(lListView, new GridPane.C().row(lRowIdx).col(1));
//			// remove button
//			{
//				Button lButton = new Button("remove");
//				lGridPane.add(lButton, new GridPane.C().row(lRowIdx).col(2).valignment(VPos.TOP));
//				lButton.onActionProperty().set( (actionEvent) -> {
//					LocalDateTime c = lListView.getSelectionModel().getSelectedItem();
//					if (c != null) {
//						localeDateTimeTextField.disabledLocalDateTimes().remove(c);
//					}
//				});
//			}
//		}
//		lRowIdx++;

        // done
        return lGridPane;
    }
	private CheckBox showTimeCheckbox;
 	private ComboBox<Locale> localeComboBox;

	private void setDateFormat() {
		Locale lLocale = localeComboBox.valueProperty().get();
		if (lLocale == null) {
			lLocale = Locale.getDefault();
		}
		localeDateTimeTextField.dateFormatProperty().set( showTimeCheckbox.isSelected() ? SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.LONG, SimpleDateFormat.LONG, lLocale) : SimpleDateFormat.getDateInstance(SimpleDateFormat.LONG, lLocale) );
	}

    @Override
    public String getJavaDocURL() {
		return "http://jfxtras.org/doc/8.0/" + LocalDateTimeTextField.class.getName().replace(".", "/") + ".html";
    }

    public static void main(String[] args) {
        launch(args);
    }
}