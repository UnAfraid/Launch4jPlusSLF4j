package com.github.unafraid.editor.ui;

import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		final Pane pane;
		try {
			pane = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
		} catch (IOException e) {
			throw new Error("Failed to initialize GUI!", e);
		}

		primaryStage.setScene(new Scene(pane));
		primaryStage.setTitle("SLF4j Test Application");
		primaryStage.setOnCloseRequest(event -> shutdown());
		primaryStage.show();
	}

	public static void shutdown() {
		Platform.exit();
		System.exit(0);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
