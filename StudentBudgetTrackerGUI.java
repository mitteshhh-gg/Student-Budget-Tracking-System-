//to run the file ->
//1. cd "C:\Users\DELL\Desktop\java"
//2. javac StudentBudgetTrackerGUI.java
//3. java mpjava.StudentBudgetTrackerGUI

package mpjava;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Expense {
    String category;
    double amount;

    Expense(String category, double amount) {
        this.category = category;
        this.amount = amount;
    }
}

public class StudentBudgetTrackerGUI extends JFrame {

    private JTextField budgetField, amountField;
    private JComboBox<String> categoryBox;

    private JLabel remainingLabel, totalLabel;
    private JProgressBar budgetBar;

    private DefaultTableModel tableModel;
    private ArrayList<Expense> expenses = new ArrayList<>();

    private double budget = 0;
    private int expenseCount = 0;

    public StudentBudgetTrackerGUI() {

        setTitle("Poor Student Survival Budget Tracker 💸");
        setSize(600,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // TOP PANEL
        JPanel topPanel = new JPanel();

        topPanel.add(new JLabel("Monthly Budget:"));

        budgetField = new JTextField(10);
        JButton setBudgetBtn = new JButton("Set Budget");
        JButton increaseBudgetBtn = new JButton("Increase Budget");

        topPanel.add(budgetField);
        topPanel.add(setBudgetBtn);
        topPanel.add(increaseBudgetBtn);

        add(topPanel, BorderLayout.NORTH);

        // TABLE
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Category");
        tableModel.addColumn("Amount");

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        // BOTTOM PANEL
        JPanel bottomPanel = new JPanel(new GridLayout(6,2,5,5));

        bottomPanel.add(new JLabel("Category:"));

        String[] categories = {
                "🍔 Food",
                "🎉 Party",
                "📚 Books",
                "☕ Coffee",
                "😭 Unexpected Expense"
        };

        categoryBox = new JComboBox<>(categories);
        bottomPanel.add(categoryBox);

        bottomPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        bottomPanel.add(amountField);

        JButton addExpenseBtn = new JButton("Add Expense");
        JButton remainingBtn = new JButton("Show Remaining");
        JButton saveBtn = new JButton("Save Data");
        JButton resetBtn = new JButton("Reset Tracker");
        bottomPanel.add(resetBtn);
        bottomPanel.add(addExpenseBtn);
        bottomPanel.add(remainingBtn);

        JLabel budgetLabel;

        budgetLabel = new JLabel("Total Budget: ₹0");
        bottomPanel.add(budgetLabel);

        totalLabel = new JLabel("Total Expense: ₹0");
        bottomPanel.add(totalLabel);

        remainingLabel = new JLabel("Remaining Balance: ₹0");
        bottomPanel.add(remainingLabel);

        // Progress Bar
        bottomPanel.add(new JLabel("Budget Usage:"));

        budgetBar = new JProgressBar(0,100);
        budgetBar.setStringPainted(true);

        bottomPanel.add(budgetBar);

        add(bottomPanel, BorderLayout.SOUTH);

        // SET BUDGET
        setBudgetBtn.addActionListener(e -> {
            try{
                int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Setting a new budget will reset all expenses. Continue?",
                    "Confirm Reset",
                    JOptionPane.YES_NO_OPTION
        );
            if(confirm == JOptionPane.YES_OPTION){
        
                // Reset everything
                expenses.clear();              // clear expense list
                tableModel.setRowCount(0);     // clear table
                expenseCount = 0;
            
                totalLabel.setText("Total Expense: ₹0");
            
                budgetBar.setValue(0);
            
                // Set new budget
                budget = Double.parseDouble(budgetField.getText());
            
                budgetLabel.setText("Total Budget: ₹" + budget);
                remainingLabel.setText("Remaining Balance: ₹" + budget);
            
                budgetField.setText("");
            
                JOptionPane.showMessageDialog(null,"New Budget Set. Tracker Reset.");
            }
        }catch(Exception ex){

                JOptionPane.showMessageDialog(null,"Enter valid budget");
            
            }
        
        });;            

        increaseBudgetBtn.addActionListener(e -> {

            try{
            
                double extra = Double.parseDouble(budgetField.getText());

                double total = 0;
                for(Expense ex : expenses){
                    total += ex.amount;
                }
                
                double remaining = budget - total;
                
                // update budget
                budget = remaining + extra + total;
                
                // new remaining balance
                remaining = remaining + extra;
                
                budgetLabel.setText("Total Budget: ₹" + budget);
                remainingLabel.setText("Remaining Balance: ₹" + remaining);
                
                JOptionPane.showMessageDialog(null,
                        "Budget increased by ₹" + extra +
                        "\nAvailable Balance: ₹" + remaining);
                
                budgetField.setText("");
                                
            }catch(Exception ex){
            
                JOptionPane.showMessageDialog(null,"Enter valid amount to increase");
            
            }
        
        });     
        // ADD EXPENSE
        addExpenseBtn.addActionListener(e -> {

            try{

                String category = (String) categoryBox.getSelectedItem();
                double amount = Double.parseDouble(amountField.getText());

                expenses.add(new Expense(category,amount));

                tableModel.addRow(new Object[]{category,amount});

                expenseCount++;

                if(amount > 1000){

                    String[] funnyMsg = {
                            "maggi days coming soon 🍜😭",
                            "Bro paisa paani ki tarah mat baha 💸",
                            "Control kar bhai! Budget toot raha hai",
                            "Wallet ro raha hai 😭"
                    };

                    int r = (int)(Math.random()*funnyMsg.length);

                    JOptionPane.showMessageDialog(null,funnyMsg[r]);
                }

                // TOTAL EXPENSE
                double total = 0;

                for(Expense ex:expenses){
                    total += ex.amount;
                }

                totalLabel.setText("Total Expense: ₹"+total);

                // UPDATE PROGRESS BAR
                if(budget > 0){

                    int percent = (int)((total/budget)*100);

                    budgetBar.setValue(percent);
                }

                amountField.setText("");

            }catch(Exception ex){

                JOptionPane.showMessageDialog(null,"Enter valid amount");

            }

        });

        // SHOW REMAINING
        remainingBtn.addActionListener(e -> {

            double total = 0;

            for(Expense ex:expenses){
                total += ex.amount;
            }

            double remaining = budget - total;

            remainingLabel.setText("Remaining Budget: ₹"+remaining);

            if(remaining > 4000){
                JOptionPane.showMessageDialog(null,"Rich Student Mode 😎\nAvailable Balance: ₹" + remaining);
            }
            
            else if(remaining > 2000){
                JOptionPane.showMessageDialog(null,"Paisa Hi Paisa Hai💸\nAvailable Balance: ₹" + remaining);
            }

            else if(remaining > 1000){
                JOptionPane.showMessageDialog(null,"Thoda sambhal ke kharch karo 🧐\nAvailable Balance: ₹" + remaining);
            }

            else if(remaining > 0){
                JOptionPane.showMessageDialog(null,"Low budget warning ⚠\nAvailable Balance: ₹" + remaining);
            }

            else{
                JOptionPane.showMessageDialog(null,
                        "💀 BROKE MODE ACTIVATED\nTime to borrow from friends 😭\nAvailable Balance: ₹" + remaining);
            }

        });

        // SAVE DATA
        saveBtn.addActionListener(e -> {

            try{

                PrintWriter writer = new PrintWriter(new FileWriter("expenses.txt"));

                writer.println("Category\tAmount");

                for(Expense ex:expenses){

                    writer.println(ex.category+"\t"+ex.amount);

                }

                writer.close();

                JOptionPane.showMessageDialog(null,
                        "Data Saved Successfully in expenses.txt");

            }catch(Exception ex){

                JOptionPane.showMessageDialog(null,"Error saving file");

            }

        });
        resetBtn.addActionListener(e -> {

            int confirm = JOptionPane.showConfirmDialog(null,
                    "Clear all budget data?",
                    "Reset Confirmation",
                    JOptionPane.YES_NO_OPTION);
            
            if(confirm == JOptionPane.YES_OPTION){
            
                expenses.clear();
                tableModel.setRowCount(0);
            
                budget = 0;
                expenseCount = 0;
            
                budgetField.setText("");
                amountField.setText("");
            
                totalLabel.setText("Total Expense: ₹0");
                remainingLabel.setText("Remaining Budget: ₹0");
            
                budgetBar.setValue(0);
            
                JOptionPane.showMessageDialog(null,"Tracker Reset Successful");
            
    }

});
    }

    public static void main(String[] args) {

        new StudentBudgetTrackerGUI().setVisible(true);

    }
}