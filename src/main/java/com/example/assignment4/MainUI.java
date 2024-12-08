package com.example.assignment4;

import javafx.scene.layout.StackPane;

// Establish a full Model-View-Controller architecture
public class MainUI extends StackPane {
    public MainUI() {
        LineModel model = new LineModel();
        InteractionModel iModel = new InteractionModel();
        DView dView = new DView();
        AppController controller = new AppController();

        // controller connections
        controller.setModel(model);
        controller.setInteractionModel(iModel);

        // view connections
        dView.setModel(model);
        dView.setupEvents(controller);
        dView.setInteractionModel(iModel);

        // set subscribers for model change notifications
        model.addSubscriber(dView);
        iModel.addSubscriber(dView);

        dView.draw();

        this.getChildren().add(dView);
    }
}
