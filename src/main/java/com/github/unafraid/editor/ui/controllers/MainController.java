package com.github.unafraid.editor.ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.unafraid.editor.ui.Main;
import com.github.unafraid.editor.ui.util.logging.GUILogger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.stage.StageStyle;

public class MainController implements Initializable {
	private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

	@FXML
	private TextArea console;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		GUILogger.onMessage(this::onMessage);

		LOGGER.info("Application initialized");

		Executors.newSingleThreadScheduledExecutor().schedule(() -> Platform.runLater(() -> {
			if (console.getText().isEmpty()) {
				final Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Error!");
				alert.setHeaderText("Failed to initialize!");
				alert.setContentText("Failed to initialize custom logger!");
				alert.initStyle(StageStyle.UTILITY);
				alert.show();
			}
		}), 500, TimeUnit.MILLISECONDS);
	}

	@FXML
	private void onApplicationClose(ActionEvent event) {
		Main.shutdown();
	}

	@FXML
	private void onAboutRequest(ActionEvent event) {
		LOGGER.warn("Not implemented yet!");
	}

	private void onMessage(String message) {
		Platform.runLater(() -> {
			if (console.getText().length() > Short.MAX_VALUE) {
				console.clear();
				console.setScrollTop(Double.MIN_VALUE);
			}

			console.appendText(message);
			console.setScrollTop(Double.MIN_VALUE);
		});
	}
}
