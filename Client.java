import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;

public class Client{
    

    private final JFrame frame;
    
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Client(args);
            }
        });
    }
    
    public Client(String[] args) {
        
        
        frame = new JFrame("Media Player");
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);
        
        JPanel controlsPane = new JPanel();
        
        //Definition of PLAY button
        
        JButton playButton = new JButton("Play");
        controlsPane.add(playButton);
        
        
        //Handler for PLAY button

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                mediaPlayerComponent.getMediaPlayer().pause();

            }
        });
        
        JButton pauseButton = new JButton("Pause");
        controlsPane.add(pauseButton);

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.getMediaPlayer().pause();

            }
        });

        // GO button 
        JPanel goPane = new JPanel();

        JLabel go_label = new JLabel();
        go_label.setText("Enter channel link: ");
        go_label.setBounds(50,20,60,10);
        goPane.add(go_label);

        JTextField textfield = new JTextField("",20);
        goPane.add(textfield);

        JButton goButton = new JButton("Go");
        goPane.add(goButton);


        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str_textfield = textfield.getText();
                mediaPlayerComponent.getMediaPlayer().playMedia(str_textfield);


            }
        });

        JButton stopButton = new JButton("Stop");
        controlsPane.add(stopButton);

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.getMediaPlayer().stop();
                mediaPlayerComponent.release();
                System.exit(0);

            }
        });

        
        contentPane.add(controlsPane, BorderLayout.SOUTH);
        contentPane.add(goPane,BorderLayout.NORTH);

        frame.setContentPane(contentPane);
        frame.setVisible(true);
        
        
    }
    
}

