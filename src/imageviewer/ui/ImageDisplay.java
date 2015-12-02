package imageviewer.ui;

import imageviewer.model.Image;

public interface ImageDisplay {
    public Image getImage();
    public void nextImage();
    public void prevImage();
}
