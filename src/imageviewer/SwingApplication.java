package imageviewer;

import imageviewer.control.Command;
import imageviewer.control.NextCommand;
import imageviewer.control.PrevCommand;
import imageviewer.persistence.FileImageLoader;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingApplication extends JFrame{
    
    public static void main(String[] args) {
        new SwingApplication();
    }

    private final String path;
    private final Map<String,Command> commands= new HashMap<>();
    private final SwingImagePanel swingImagePanel;
    
    public SwingApplication() {
        this.path = "C:\\Users\\joaqu\\Documents\\Wallpaper";
        this.swingImagePanel = new SwingImagePanel(new FileImageLoader(path).read());
        setCommands();
        deployUI();
    }

    private void setCommands() {
        commands.put("next", new NextCommand(swingImagePanel));
        commands.put("prev", new PrevCommand(swingImagePanel));
    }

    private void deployUI() {
        this.setTitle("Image Viewer");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(400, 400));
        this.getContentPane().add(swingImagePanel, BorderLayout.CENTER);
        this.setPreferredSize(new Dimension(1000, 800));
        this.pack();
        this.getContentPane().add(bottomMenu(), BorderLayout.SOUTH);
        this.setVisible(true);
    }

    private JPanel bottomMenu() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(prevButton());
        panel.add(nextButton());
        return panel;
    }

    private JButton prevButton() {
        JButton button = new JButton("<");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("prev").execute();
            }
        });
        return button;
    }
    
    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get("next").execute();
            }
        });
        return button;
    }

}
