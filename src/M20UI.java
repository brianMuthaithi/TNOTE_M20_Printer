import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class M20UI extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nameField;
    private DefaultListModel<String> listModel;
    private List<String> names;
    private M20Class m20Class;

    public M20UI() {
        names = new ArrayList<>();
        m20Class = new M20Class(); 
        
        setTitle("CPSE Card Printing Using This UI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(598, 339);
        getContentPane().setLayout(null);

        JPanel inputPanel = new JPanel();
        inputPanel.setBounds(10, 11, 422, 79);

        JLabel nameLabel = new JLabel("ENTER NAME:");
        nameLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        nameLabel.setBounds(10, 39, 78, 17);
        nameField = new JTextField(20);
        nameField.setBounds(98, 37, 309, 20);
        inputPanel.setLayout(null);

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);

        getContentPane().add(inputPanel);

        JLabel lblNewLabel = new JLabel("Customer List");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        lblNewLabel.setBounds(10, 11, 161, 17);
        inputPanel.add(lblNewLabel);

        listModel = new DefaultListModel<>();

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 125, 422, 130);
        getContentPane().add(scrollPane);
        JList<String> nameList = new JList<>(listModel);
        scrollPane.setViewportView(nameList);

        JButton resetPrinterButton = new JButton("Reset Printer");
        resetPrinterButton.setFont(new Font("Dialog", Font.BOLD, 12));
        resetPrinterButton.setBounds(442, 29, 130, 39);
        getContentPane().add(resetPrinterButton);

        JButton ejectCardButton = new JButton("Eject Card");
        ejectCardButton.setFont(new Font("Dialog", Font.BOLD, 12));
        ejectCardButton.setBounds(442, 92, 130, 39);
        getContentPane().add(ejectCardButton);

        JButton printCardButton = new JButton("Print Card");
        printCardButton.setFont(new Font("Dialog", Font.BOLD, 13));
        printCardButton.setBounds(442, 235, 130, 39);
        getContentPane().add(printCardButton);

        JLabel lblNewLabel_1 = new JLabel("Name List:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel_1.setBounds(10, 101, 122, 13);
        getContentPane().add(lblNewLabel_1);

        JButton addButton = new JButton("Add To List");
        addButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        addButton.setBounds(20, 266, 112, 23);
        getContentPane().add(addButton);

        JButton removeButton = new JButton("Remove From List");
        removeButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeButton.setBounds(144, 266, 141, 23);
        getContentPane().add(removeButton);

        JButton removeAllButton = new JButton("Remove All");
        removeAllButton.setFont(new Font("Tahoma", Font.BOLD, 11));
        removeAllButton.setBounds(298, 266, 112, 23);
        getContentPane().add(removeAllButton);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = nameList.getSelectedIndex();
                if (selectedIndex != -1) {
                    listModel.remove(selectedIndex);
                    names.remove(selectedIndex);
                    JOptionPane.showMessageDialog(null, "Name removed successfully!");
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                if (!name.isEmpty()) {
                    listModel.addElement(name);
                    names.add(name);
                    nameField.setText("");
                    JOptionPane.showMessageDialog(null, "Name added successfully!");
                }
            }
        });

        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listModel.clear();
                names.clear();
                JOptionPane.showMessageDialog(null, "All names removed successfully!");
            }
        });

        resetPrinterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte result = m20Class.CPSC1900DeviceReset();
                System.out.println("Printer reset hex code: " + m20Class.ConvertToHex(result));
            }
        });

        ejectCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte result = m20Class.CPSC1900EjectCard((byte) 0x01);
                System.out.println("Card eject hex code: " + m20Class.ConvertToHex(result));
            }
        });

        printCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!names.isEmpty()) {
                    String firstName = names.get(0);
                    byte cleanData = m20Class.CPSC1900EmbosserCleanLineData();
                    String cldHex = m20Class.ConvertToHex(cleanData);

                    if (cldHex.equals("0x00")) {
                        byte result = m20Class.CPSC1900EmbosserDownloadLineData(firstName, 420, 312);
                        String eddHex = m20Class.ConvertToHex(result);

                        if (eddHex.equals("0x00")) {
                            byte feedResult = m20Class.CPSC1900FeedCard();
                            String feedHex = m20Class.ConvertToHex(feedResult);

                            if (feedHex.equals("0x00")) {
                                byte emb = m20Class.CPSC1900EmbosserEmbossLines(false);
                                String embHex = m20Class.ConvertToHex(emb);

                                if (embHex.equals("0x00")) {
                                    byte topp = m20Class.CPSC1900TopperPressCard(true, (byte) 20);
                                    String topHex = m20Class.ConvertToHex(topp);

                                    if (topHex.equals("0x00")) {
                                        byte eject = m20Class.CPSC1900EjectCard((byte) 0x30);
                                        String ejHex = m20Class.ConvertToHex(eject);

                                        if (ejHex.equals("0x00")) {
                                            byte disc = m20Class.CPSC1900Disconnect();
                                            String discHex = m20Class.ConvertToHex(disc);
                                            System.out.println("Disconnection Successful. Code: " + discHex);
                                            JOptionPane.showMessageDialog(null, "Card printed successfully!");
                                        } else {
                                            System.out.println("Card ejection failed. Code: " + ejHex);
                                            JOptionPane.showMessageDialog(null, "Card ejection failed. Code: " + ejHex);
                                        }
                                    } else {
                                        System.out.println("Topping failed. Code: " + topHex);
                                        JOptionPane.showMessageDialog(null, "Topping failed. Code: " + topHex);
                                    }
                                } else {
                                    System.out.println("EmbosserEmbossLines failed. Code: " + embHex);
                                    JOptionPane.showMessageDialog(null, "Embossing failed. Code: " + embHex);
                                }
                            } else {
                                System.out.println("Card not fed. Code: " + feedHex);
                                JOptionPane.showMessageDialog(null, "Card not fed. Code: " + feedHex);
                            }
                        } else {
                            System.out.println("CPSC1900EmbosserDownloadLineData failed. Code: " + eddHex);
                            JOptionPane.showMessageDialog(null, "Download line data failed. Code: " + eddHex);
                        }
                    } else {
                        System.out.println("EmbossCleanLineData failed. Code: " + cldHex);
                        JOptionPane.showMessageDialog(null, "Cleaning line data failed. Code: " + cldHex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Name list is empty!");
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        M20Class m20Class = new M20Class();

        byte connectionResult = m20Class.CPSC1900Connect((byte) 0x30, "null");
        String connectionHex = m20Class.ConvertToHex(connectionResult);
        if (connectionHex.equals("0x00")) {
            System.out.println("Printer connected successfully.");
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new M20UI().setVisible(true);
                }
            });
        } else {
            System.out.println("Printer connection failed. Code: " + connectionHex);
            JOptionPane.showMessageDialog(null, "Printer is not connected. Please connect the printer and try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }  
    }
}
