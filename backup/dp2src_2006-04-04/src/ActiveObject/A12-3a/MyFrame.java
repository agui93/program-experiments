import java.io.IOException;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;

import activeobject.ActiveObjectFactory;
import activeobject.ActiveObject;
import activeobject.Result;

public class MyFrame extends JFrame implements ActionListener {
    private final JTextField textfield = new JTextField("word", 10);
    private final JButton button = new JButton("Search");
    private final JTextArea textarea = new JTextArea(20, 30);
    private final ActiveObject activeObject = ActiveObjectFactory.createActiveObject();
    private final static String NEWLINE = System.getProperty("line.separator");

    public MyFrame() {
        super("ActiveObject Sample");
        getContentPane().setLayout(new BorderLayout());

        // North
        JPanel north = new JPanel();
        north.add(new JLabel("Search:"));
        north.add(textfield);
        north.add(button);
        button.addActionListener(this);

        // Center
        JScrollPane center = new JScrollPane(textarea);

        // Layout
        getContentPane().add(north, BorderLayout.NORTH);
        getContentPane().add(center, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    // 当[Search]按钮被按下时
    public void actionPerformed(ActionEvent e) {
        searchWord(textfield.getText());
    }

    // 显示
    private void println(String line) {
        textarea.append(line + NEWLINE);
    }

    // 搜索
    private void searchWord(final String word) {
        // 调用搜索
        final Result<String> result = activeObject.search(word);
        println("Searching " + word + "...");
        // 等待搜索结果的线程
        new Thread() {
            public void run() {
                // 等待结果
                final String url = result.getResultValue();
                // 已经获取到了结果，所以这里要委托事件分发线程显示结果
                SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            MyFrame.this.println("word = " + word + ", URL = " + url);
                        }
                    }
                );
            }
        }.start();
    }
}
