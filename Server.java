import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;


import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

public class Server {
    
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    
    public static void main(final String[] args) {
        new NativeDiscovery().discover();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Server(args);
            }
        });
    }
    
    private Server
    (String[] args) {
        JFrame frame = new JFrame("vlcj");
        
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        
        frame.setContentPane(mediaPlayerComponent);
        

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(100, 100, 600, 400);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        


        
        // video delivery configuration via RTP. Sends two different videos to two different multicast groups

        String media1 = "file:///home/labsma/Desktop/practical study case/material-complementario/movie.mp4";
        String options1 = formatRtpStream("239.1.1.1", 5004);

        String media2 = "file:///home/labsma/Desktop/practical study case/material-complementario/dw11222.mp4";
        String options2 = formatRtpStream("239.1.1.2", 5014);

        MediaPlayerFactory mediaPlayerFactory1 = new MediaPlayerFactory();
        HeadlessMediaPlayer mediaPlayer1 = mediaPlayerFactory1.newHeadlessMediaPlayer();

        MediaPlayerFactory mediaPlayerFactory2 = new MediaPlayerFactory();
        HeadlessMediaPlayer mediaPlayer2 = mediaPlayerFactory2.newHeadlessMediaPlayer();

        mediaPlayer1.playMedia(media1,
            options1,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep"

            );
        
        mediaPlayer2.playMedia(media2,
            options2,
            ":no-sout-rtp-sap",
            ":no-sout-standard-sap",
            ":sout-all",
            ":sout-keep"
        );

    
        frame.setVisible(true);
    }

        private static String formatRtpStream(String serverAddress, int serverPort) {
            StringBuilder sb = new StringBuilder(60);
            sb.append(":sout=#rtp{dst=");
            sb.append(serverAddress);
            sb.append(",port=");
            sb.append(serverPort);
            sb.append(",ttl=3");
            sb.append(",mux=ts}");
            return sb.toString();
        }

    }
