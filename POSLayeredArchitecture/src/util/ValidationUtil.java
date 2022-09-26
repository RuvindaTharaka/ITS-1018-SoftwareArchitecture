package util;

import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;

public class ValidationUtil {

    public static Object validate(LinkedHashMap<TextField, Pattern> map, JFXButton btn) {
            btn.setDisable(true);
            for (TextField textFieldKey : map.keySet()) {
                Pattern patternValue = map.get(textFieldKey);
                if (!patternValue.matcher(textFieldKey.getText()).matches()) {
                    if (!textFieldKey.getText().isEmpty()) {
                        textFieldKey.setStyle("-fx-border-color: #ff0000");
                        // ((AnchorPane) textFieldKey.getParent()).getChildren().get(1).setStyle("-fx-text-fill: green");
                    }
                    return textFieldKey;
                }
                textFieldKey.setStyle("-fx-border-color: #00ff4c");
            }
            btn.setDisable(false);
            return true;
        }
}

