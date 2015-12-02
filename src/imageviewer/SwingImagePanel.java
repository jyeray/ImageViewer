package imageviewer;

import imageviewer.model.Image;
import imageviewer.ui.ImageDisplay;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class SwingImagePanel extends JPanel implements ImageDisplay {
    
    private Image image;

    public SwingImagePanel(Image image) {
        this.image = image;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage((BufferedImage)image.getBitmap(), 0, 0, this);
    }
    
    @Override
    public Image getImage() {
        return this.image;
    }

    @Override
    public void nextImage() {
        this.image = image.getNext();
        this.repaint();
    }

    @Override
    public void prevImage() {
        this.image = image.getPrev();
        this.repaint();
    }
}
