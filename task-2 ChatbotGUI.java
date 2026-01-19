import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class ChatbotGUI extends JFrame {

    private JTextArea chatArea;
    private JTextField inputField;
    private HashMap<String, String> responses;

    public ChatbotGUI() {
        setTitle("AI Chatbot");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);

        inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);

        trainBot();

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        setVisible(true);
    }

    // Train chatbot with FAQs
    private void trainBot() {
        responses = new HashMap<>();
        responses.put("hello", "Hello! How can I help you?");
        responses.put("hi", "Hi there!");
        responses.put("how are you", "I'm doing great! How about you?");
        responses.put("what is java", "Java is a popular object-oriented programming language.");
        responses.put("what is ai", "AI stands for Artificial Intelligence.");
        responses.put("bye", "Goodbye! Have a nice day.");
    }

    // NLP + Rule-based response
    private String getResponse(String userInput) {
        userInput = userInput.toLowerCase().trim();

        for (String key : responses.keySet()) {
            if (userInput.contains(key)) {
                return responses.get(key);
            }
        }
        return "Sorry, I didn't understand that.";
    }

    private void sendMessage() {
        String userText = inputField.getText();
        if (userText.isEmpty()) return;

        chatArea.append("You: " + userText + "\n");
        String botReply = getResponse(userText);
        chatArea.append("Bot: " + botReply + "\n\n");

        inputField.setText("");
    }

    public static void main(String[] args) {
        new ChatbotGUI();
    }
}
