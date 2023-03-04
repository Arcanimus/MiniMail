package java_mail_client;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

public class MailClient extends JFrame {
    private static final long serialVersionUID = 1L;

    private String smtpHost = "smtp.gmail.com"; // replace with your SMTP server
    private int smtpPort = 587; // replace with your SMTP port
    private String username = "neven.sherer@gmail.com"; // replace with your email address
    private String password = "nvukmiiyhcscbhuu"; // replace with your email password

    private JLabel toLabel, subjectLabel, messageLabel;
    private JTextField toField, subjectField;
    private JTextArea messageArea;
    private JButton sendButton;

    public MailClient() {
        super("Mail Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        toLabel = new JLabel("To:");
        subjectLabel = new JLabel("Subject:");
        messageLabel = new JLabel("Message:");

        toField = new JTextField(30);
        subjectField = new JTextField(30);
        messageArea = new JTextArea(10, 30);
        JScrollPane scrollPane = new JScrollPane(messageArea);

        sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendEmail();
            }
        });

        JPanel toPanel = new JPanel(new BorderLayout());
        toPanel.add(toLabel, BorderLayout.WEST);
        toPanel.add(toField, BorderLayout.CENTER);

        JPanel subjectPanel = new JPanel(new BorderLayout());
        subjectPanel.add(subjectLabel, BorderLayout.WEST);
        subjectPanel.add(subjectField, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.add(messageLabel, BorderLayout.SOUTH);
        messagePanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(sendButton);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(toPanel, BorderLayout.WEST);
        contentPane.add(subjectPanel, BorderLayout.CENTER);
        contentPane.add(messagePanel, BorderLayout.SOUTH);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void sendEmail() {
        String to = toField.getText().trim();
        String subject = subjectField.getText().trim();
        String message = messageArea.getText().trim();

        if (to.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter To, Subject, and Message");
            return;
        }

        try {
            Session session = getSession();
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
            JOptionPane.showMessageDialog(this, "Message sent successfully");
        } catch (MessagingException e) {
            JOptionPane.showMessageDialog(this, "Error sending message: " + e.getMessage());
        }
    }

    private Session getSession() {
        Authenticator authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        };

        Properties props = new Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return Session.getInstance(props, authenticator);
    }

    public static void main(String[] args) {
        new MailClient();
    }
}
