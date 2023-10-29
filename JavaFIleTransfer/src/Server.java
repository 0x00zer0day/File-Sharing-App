import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
public class Server {
        static ArrayList<MyFile> myFiles = new ArrayList<>();

        public static void main(String[] args) {
                int fileId = 0;

                JFrame jFrame = new JFrame(" Server");
                jFrame.setSize(400, 400);
                jFrame.setLayout(new BoxLayout(jFrame.getContentPane(), BoxLayout.Y_AXIS));
                jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel jPanel = new JPanel();
                jPanel.setLayout (new BoxLayout(jPanel, BoxLayout.Y_AXIS));

                JScrollPane jScrollPane = new JScrollPane(jPanel);
                jScrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                JLabel jlTitle = new JLabel(" File Receiver");
                jlTitle.setFont(new Font("Arial", Font.BOLD, 25)); jlTitle.setBorder(new EmptyBorder(20,0,10,0));
                jlTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

                jFrame.add(jlTitle);
                jFrame.add(jScrollPane);
                jFrame.setVisible(true);

                ServerSocket serverSocket = new ServerSocket(1234);

                while (true) {
                        try {
                                Socket socket = serverSocket.accept();
                                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                                int fileNameLength  = dataInputStream.readInt();

                                if (fileNameLength > 0) {
                                        byte[] fileNameBytes = new byte[fileNameLength];
                                        dataInputStream.readFully(fileNameBytes, 0, fileNameBytes.length);
                                        String fileName = new String(fileNameBytes);

                                        int fileContentLength = dataInputStream.readInt();

                                        if (fileContentLength > 0) {
                                                byte[] fileContentBytes = new byte[fileContentLength];

                                                JPanel jpFileRow = new JPanel();
                                                jpFileRow.setLayout(new BoxLayout(jpFileRow, BoxLayout.Y_AXIS));

                                                JLabel jlFileName = new JLabel(fileName);
                                                jlFileName.setFont(new Font("Arial", Font.BOLD, 20));
                                                jlFileName.setBorder(new EmptyBorder(10,0,10,0));

                                                if (getFileExtension(fileName).equalsIgnoreCase("txt")) {
                                                        jpFileRow.setName(String.valueOf(fileId));
                                                        jpFileRow.addMouseListener(getMyMouseListener());

                                                        jpFileRow.add(jFileName);
                                                        jPanel.add(jpFileRow);
                                                        jFrame.validate();
                                                }
                                        }
                                }
                        }

                }
        }
        public static MouseListener getMyMouseListener(){

                return new MouseListener(){
                        @Override
                        public void mouseClicked(MouseEvent e){
                                JPanel jPanel = (JPanel) e.getSource();

                                int fileId = Integer.parseInt(jPanel.getName());

                                for (MyFile myFile:myFiles) {
                                       if (myFile.getId() == fileId) {
                                              JFrame.jfPreview = createFrame();
                                              jfPreview.setVisible(true);
                                       }
                                }

                        }

                        @Override
                        public void mousePressed(MouseEvent e){

                        }

                        @Override
                        public void mouseReleased(MouseEvent e){

                        }

                        @Override
                        public void mouseExited(MouseEvent e){

                        }
                };
        }

        public static JFrame createFrame(String fileName, byte[] fileData, string fileExtension){

                JFrame jFrame = new JFrame("VCET's File Downloader");
                jFrame.setsize(400,400);

                JPanel jPanel = new JPanel();
                jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        }

        public static String getFileExtension(String fileName){
                int i = fileName.lastIndexOf('.');

                if (i > 0) {
                        return fileName.substring(i+1);
                } else {
                        return "No extension found.";
                }
        }
}
