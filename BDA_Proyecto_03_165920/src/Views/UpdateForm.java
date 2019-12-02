package Views;

import BusinessObjects.User;
import Control.UserControl;
import Enums.Sex;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * @author Juan Enrique Solis Perla
 * @ID: 165920 Advanced Databases Class, ISW, ITSON
 */
public class UpdateForm extends javax.swing.JFrame implements ActionListener {

    private User user;
    private UserControl userControl;

    public UpdateForm(User user) {

        initComponents();
        this.user = user;

        jButtonUpdate.addActionListener(this);
        jButtonUpdate.setActionCommand("Update");

        jButtonCancel.addActionListener(this);
        jButtonCancel.setActionCommand("Cancel");

        jLabelTitle.setText("Update " + this.user.getUsername());
        setVisible(true);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(user.getBirthDate());
        String[] dates = date.split("-");

        jTextFieldBirthDateYear.setText(dates[0]);
        jTextFieldBirthDateMonth.setText(dates[1]);
        int day = Integer.parseInt(dates[2]) + 1;
        jTextFieldBirthDateDay.setText("" + day);

        jTextFieldEmail.setText(user.getEmail());
        jTextFieldUsername.setText(user.getUsername());

        jComboBoxSex.setSelectedIndex(user.getSex() == Sex.FEMALE ? 2 : 1);
        userControl = new UserControl();
    }

    private void update() {
        Date date = null;
        Sex sex = null;

        //Get Selected Sex
        String sexSelection = (String) jComboBoxSex.getSelectedItem();

        //Get Date from jTextFields
        String year = jTextFieldBirthDateYear.getText();
        String month = jTextFieldBirthDateMonth.getText();
        String day = jTextFieldBirthDateDay.getText();

        //Get email from jTextField
        String email = jTextFieldEmail.getText();

        //Get username from jTextField
        String username = jTextFieldUsername.getText();

        //Get password from jTextField
        String password = String.valueOf(jPasswordField.getText());

        //Email validation
        if (!Regex.Regex.matchEmail(email)) {
            JOptionPane.showMessageDialog(rootPane, "Invalid email format");
        } else {
            //Password validation
            if (!Regex.Regex.matchPassword(password)) {
                JOptionPane.showMessageDialog(rootPane, "Invalid password, must be from 4 to 8 digits and at least 1 number");
            } else {
                //Username validation
                if (!Regex.Regex.matchUsername(username)) {
                    JOptionPane.showMessageDialog(rootPane, "Invalid username, must be from 3 to 16 digits, no spaces, only letters, numbers, hyphen(-) and underscore(_)");
                } else {
                    //Date Validation
                    if (!Regex.Regex.matchDate(year, month, day)) {
                        JOptionPane.showMessageDialog(rootPane, "Invalid date, please follow this format: YYYY-MM-DD, 4 numbers year, 2 numbers month, 2 numbers day");
                    } else {
                        date = new Date(Integer.parseInt(year) - 1900, Integer.parseInt(month) - 1, Integer.parseInt(day));

                        //Sex validation
                        if (sexSelection.equalsIgnoreCase("FEMALE") || sexSelection.equalsIgnoreCase("MALE")) {
                            //Asign selected Sex value to local sex variable
                            if (sexSelection.equalsIgnoreCase("FEMALE")) {
                                sex = Sex.FEMALE;
                            } else {
                                sex = Sex.MALE;
                            }

                            //Once everything has been validated proceed to register new user
                            user.setBirthDate(date);
                            user.setEmail(email);
                            user.setPassword(password);
                            user.setSex(sex);
                            user.setUsername(username);

                            Boolean updated = userControl.update(user);
                            if (!updated) {
                                JOptionPane.showMessageDialog(rootPane, "Could't update user");
                            } else {
                                this.setVisible(false);
                                JOptionPane.showMessageDialog(rootPane, "Updated user successfully");
                            }
                            cleanTextInputs();
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Select sex please");
                        }
                    }
                }
            }
        }
    }

    private void cancel() {
        cleanTextInputs();
    }

    private void cleanTextInputs() {
        jTextFieldEmail.setText("");
        jTextFieldUsername.setText("");
        jComboBoxSex.setSelectedIndex(0);
        jTextFieldBirthDateYear.setText("YYYY");
        jTextFieldBirthDateMonth.setText("MM");
        jTextFieldBirthDateDay.setText("DD");
        jPasswordField.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        switch (action) {

            case "Update":
                update();
                break;
            case "Cancel":
                cancel();
                break;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        jPanelForms = new javax.swing.JPanel();
        jLabelEmail = new javax.swing.JLabel();
        jLabelPassword = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        jLabelUsername = new javax.swing.JLabel();
        jTextFieldUsername = new javax.swing.JTextField();
        jLabelBirthDate = new javax.swing.JLabel();
        jTextFieldBirthDateYear = new javax.swing.JTextField();
        jLabelSex = new javax.swing.JLabel();
        jComboBoxSex = new javax.swing.JComboBox<>();
        jTextFieldBirthDateMonth = new javax.swing.JTextField();
        jTextFieldBirthDateDay = new javax.swing.JTextField();
        jLabelTitle = new javax.swing.JLabel();
        jButtonUpdate = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        setTitle("Update User");
        setResizable(false);

        rootPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabelEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelEmail.setText("Email: ");

        jLabelPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelPassword.setText("Password: ");

        jLabelUsername.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelUsername.setText("Username: ");

        jLabelBirthDate.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelBirthDate.setText("Birth Date: ");

        jTextFieldBirthDateYear.setText("YYYY");
        jTextFieldBirthDateYear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldBirthDateYearActionPerformed(evt);
            }
        });

        jLabelSex.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabelSex.setText("Sex: ");

        jComboBoxSex.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Sex", "MALE", "FEMALE" }));

        jTextFieldBirthDateMonth.setText("MM");

        jTextFieldBirthDateDay.setText("DD");

        javax.swing.GroupLayout jPanelFormsLayout = new javax.swing.GroupLayout(jPanelForms);
        jPanelForms.setLayout(jPanelFormsLayout);
        jPanelFormsLayout.setHorizontalGroup(
            jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFormsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanelFormsLayout.createSequentialGroup()
                        .addComponent(jLabelSex)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxSex, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanelFormsLayout.createSequentialGroup()
                        .addComponent(jLabelBirthDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBirthDateYear)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBirthDateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldBirthDateDay, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanelFormsLayout.createSequentialGroup()
                        .addComponent(jLabelUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUsername))
                    .addGroup(jPanelFormsLayout.createSequentialGroup()
                        .addComponent(jLabelPassword)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPasswordField))
                    .addGroup(jPanelFormsLayout.createSequentialGroup()
                        .addComponent(jLabelEmail)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanelFormsLayout.setVerticalGroup(
            jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFormsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPassword)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelUsername)
                    .addComponent(jTextFieldUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBirthDate)
                    .addComponent(jTextFieldBirthDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBirthDateMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBirthDateDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelFormsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelSex)
                    .addComponent(jComboBoxSex, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelTitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabelTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitle.setText("Update User");

        jButtonUpdate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonUpdate.setText("Update");

        jButtonCancel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButtonCancel.setText("Cancel");

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButtonUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelForms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, rootPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelForms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(45, 45, 45))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rootPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 306, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldBirthDateYearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldBirthDateYearActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldBirthDateYearActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonUpdate;
    private javax.swing.JComboBox<String> jComboBoxSex;
    private javax.swing.JLabel jLabelBirthDate;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelPassword;
    private javax.swing.JLabel jLabelSex;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JPanel jPanelForms;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextFieldBirthDateDay;
    private javax.swing.JTextField jTextFieldBirthDateMonth;
    private javax.swing.JTextField jTextFieldBirthDateYear;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldUsername;
    private javax.swing.JPanel rootPanel;
    // End of variables declaration//GEN-END:variables
}
