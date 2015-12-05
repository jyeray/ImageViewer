package imageviewer.control;

import imageviewer.ui.ImageDisplay;

public class ResizeCommand implements Command {
    
    private final ImageDisplay imageDisplay;

    public ResizeCommand(ImageDisplay imageDisplay) {
        this.imageDisplay = imageDisplay;
    }
    
    @Override
    public void execute() {
        imageDisplay.resize();
    }
}
