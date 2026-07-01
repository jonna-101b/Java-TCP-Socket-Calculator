import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

public class App extends JFrame {

    private JTextField display;
    private PrintWriter out;
    private BufferedReader in;

    // Colors
    private static final Color BG_DARK = new Color(18, 18, 18);
    private static final Color BTN_DARK_GREY = new Color(46, 46, 46);
    private static final Color BTN_GREEN = new Color(46, 204, 113);
    private static final Color BTN_GREEN_DARK = new Color(39, 174, 96);
    private static final Color BTN_RED = new Color(192, 57, 43);

    private static final Font BTN_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font DISPLAY_FONT = new Font("Segoe UI", Font.BOLD, 34);

    public App() {
        setTitle("Socket Calculator");
        setSize(340, 520);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BG_DARK);
        setLayout(new BorderLayout(10, 10));

        createDisplay();
        createButtons();
        connectToServer();
    }

    private void createDisplay() {
        display = new JTextField();
        display.setFont(DISPLAY_FONT);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setResizable(false);
        display.setEditable(false);
        display.setBackground(BG_DARK);
        display.setForeground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        add(display, BorderLayout.NORTH);
    }

    private void createButtons() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BG_DARK);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.weightx = 1;
        gbc.weighty = 1;

        // Row 0
        addBtn(panel, "C", 0, 0, gbc);
        addBtn(panel, "B", 1, 0, gbc);
        addBtn(panel, "/", 2, 0, gbc);
        addBtn(panel, "*", 3, 0, gbc);

        // Row 1
        addBtn(panel, "7", 0, 1, gbc);
        addBtn(panel, "8", 1, 1, gbc);
        addBtn(panel, "9", 2, 1, gbc);
        addBtn(panel, "-", 3, 1, gbc);

        // Row 2
        addBtn(panel, "4", 0, 2, gbc);
        addBtn(panel, "5", 1, 2, gbc);
        addBtn(panel, "6", 2, 2, gbc);
        addBtn(panel, "+", 3, 2, gbc);

        // Row 3
        addBtn(panel, "1", 0, 3, gbc);
        addBtn(panel, "2", 1, 3, gbc);
        addBtn(panel, "3", 2, 3, gbc);

        // Row 4
        addBtn(panel, "0", 1, 4, gbc);
        addBtn(panel, ".", 2, 4, gbc);

        // Equals button (tall)
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridheight = 2;
        panel.add(createButton("="), gbc);
        gbc.gridheight = 1;

        add(panel, BorderLayout.CENTER);
    }

    private void addBtn(JPanel panel, String text, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(createButton(text), gbc);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(BTN_FONT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);

        if ("+-*/".contains(text)) {
            btn.setBackground(BTN_GREEN);
            btn.setForeground(Color.WHITE);
        } else if (text.equals("=")) {
            btn.setBackground(BTN_GREEN_DARK);
            btn.setForeground(Color.WHITE);
        } else if (text.equals("C")) {
            btn.setBackground(BTN_RED);
            btn.setForeground(Color.WHITE);
        } else if (text.equals("B")) {
            btn.setBackground(new Color(120, 120, 120));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(BTN_DARK_GREY);
            btn.setForeground(BTN_GREEN);
        }

        btn.addActionListener(e -> handleInput(text));
        return btn;
    }



    private void handleInput(String text) {
        if (text.equals("C")) {
            display.setText("");
        } else if (text.equals("B")) {
            String current = display.getText();
            if (!current.isEmpty()) {
                display.setText(current.substring(0, current.length() - 1));
            }
        } else if (text.equals("=")) {
            sendExpression();
        } else {
            display.setText(display.getText() + text);
        }
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("SERVER_IP_HERE", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Cannot connect to server");
        }
    }

    private void sendExpression() {
        try {
            out.println(display.getText());
            display.setText(in.readLine());
        } catch (IOException e) {
            display.setText("ERROR");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new App().setVisible(true));
    }
}
