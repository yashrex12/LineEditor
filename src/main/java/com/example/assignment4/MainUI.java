package com.example.assignment4;

import javafx.scene.layout.StackPane;

public class MainUI extends StackPane {
    public MainUI() {
        LineModel model = new LineModel();
        InteractionModel iModel = new InteractionModel();
        DView dView = new DView();
        AppController controller = new AppController();

        controller.setModel(model);
        controller.setInteractionModel(iModel);

        dView.setModel(model);
        dView.setupEvents(controller);
        dView.setInteractionModel(iModel);

        model.addSubscriber(dView);
        iModel.addSubscriber(dView);

        dView.draw();

        this.getChildren().add(dView);
    }
}
