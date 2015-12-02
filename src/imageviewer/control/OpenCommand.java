package imageviewer.control;

import imageviewer.persistence.ImageLoader;
import imageviewer.ui.ImageDisplay;

public class OpenCommand implements Command {
    private final ImageDisplay imageDisplay;
    private final ImageLoader imageLoader;

    public OpenCommand(ImageDisplay imageDisplay, ImageLoader imageLoader) {
        this.imageDisplay = imageDisplay;
        this.imageLoader = imageLoader;
    }

    @Override
    public void execute() {
        imageDisplay.show(imageLoader.read());
    }
    
}
