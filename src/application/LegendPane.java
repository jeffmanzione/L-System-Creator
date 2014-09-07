package application;

import java.util.Map;


import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class LegendPane extends GridPane {
	public LegendPane(Map<String, String> entries) {
		entries.forEach((k,v) -> {
			Label key = new Label(k);
			key.getStyleClass().add("keys");
			Label val = new Label(v);
			val.getStyleClass().add("values");
			this.add(key, 0, i);
			this.add(val, 1, i++);
		});
	}
	int i;
}
