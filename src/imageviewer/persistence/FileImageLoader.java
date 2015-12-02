package imageviewer.persistence;

import imageviewer.model.Image;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileImageLoader implements ImageLoader{

    private final File[] files;

    public FileImageLoader(String path) {
        File folder= new File(path);
        files = folder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".jpg");
            }
        });
    }

    @Override
    public Image read() {
        return image(0);
    }

    private Image image(int index) {
        return new Image() {

            @Override
            public Object getBitmap() {
                try {
                    return ImageIO.read(files[index]);
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public Image getPrev() {
                return image(index > 0 ? index-1 : files.length-1);
            }

            @Override
            public Image getNext() {
                return image(index < files.length-1 ? index+1 : 0 );
            }
        };
    }
}
