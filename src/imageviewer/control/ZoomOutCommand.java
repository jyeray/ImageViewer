package imageviewer.control;

import imageviewer.ui.ImageDisplay;

public class ZoomOutCommand implements Command {

    private final ImageDisplay imageDisplay;

    public ZoomOutCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }
    
    @Override
    public void execute() {
        imageDisplay.zoomOut();
    }
}
