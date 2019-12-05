package Views;

import BusinessObjects.Comment;
import BusinessObjects.Post;
import BusinessObjects.Tag;
import BusinessObjects.User;
import Control.PostControl;
import Control.UserControl;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.bson.types.ObjectId;

public class Home extends javax.swing.JFrame implements ActionListener {

    private UserControl userControl;
    private PostControl postControl;
    private ArrayList<Tag> newPostTags;

    private User user;

    public Home(User user) {
        initComponents();

        jButtonAddTag.addActionListener(this);
        jButtonAddTag.setActionCommand("AddTag");

        jButtonNewPost.addActionListener(this);
        jButtonNewPost.setActionCommand("NewPost");

        jButtonCancelPost.addActionListener(this);
        jButtonCancelPost.setActionCommand("Cancel");

        jButtonRefresh.addActionListener(this);
        jButtonRefresh.setActionCommand("Refresh");

        jButtonRemoveTag.addActionListener(this);
        jButtonRemoveTag.setActionCommand("RemoveTag");

        jButtonSearch.addActionListener(this);
        jButtonSearch.setActionCommand("Search");

        jButtonUpdateUser.addActionListener(this);
        jButtonUpdateUser.setActionCommand("UpdateUser");

        postControl = new PostControl();
        userControl = new UserControl();

        this.user = user;
        newPostTags = new ArrayList<>();

        jListTags.setModel(new DefaultListModel());
        jLabelUsername.setText("Welcome " + user.getUsername() + "!!");
        this.setVisible(true);

        refreshPane();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        switch (action) {
            case "AddTag":
                addTagToJList();
                break;
            case "RemoveTag":
                deleteTagJList();
                break;
            case "Refresh":
                refreshPane();
                break;
            case "Search":
                search();
                break;
            case "NewPost":
                newPost();
                break;
            case "Cancel":
                clearJList();
                cleanTextFields();
                break;
            case "UpdateUser":
                new UpdateForm(user);
                break;
        }
    }

    private void refreshPane() {
        refreshJTextPane(postControl.findAll());
    }

    private void refreshJTextPane(ArrayList<Post> posts) {
        //Clean jTextPane
        jTextPanePosts.setText("");

        Style styleDate = jTextPanePosts.addStyle("date", null);
        StyleConstants.setForeground(styleDate, Color.MAGENTA);

        Style stylePost = jTextPanePosts.addStyle("post", null);
        StyleConstants.setForeground(stylePost, Color.BLUE);
        
        Style styleComments = jTextPanePosts.addStyle("comments", null);
        StyleConstants.setForeground(styleComments, Color.darkGray);

//        StyledDocument doc = jTextPaneChat.getStyledDocument();
        StyledDocument doc = jTextPanePosts.getStyledDocument();
//        ArrayList<Post> posts = postControl.findAll();

        if (posts.size() > 0) {
            for (Post p : posts) {
                try {
                    doc.insertString(doc.getLength(), "By user: " + p.getUser().getUsername() + ", Date: " + p.getDate().toString() + "\n", styleDate);
                    doc.insertString(doc.getLength(), p.getMessage() + "\n", stylePost);
                    String tags = "Tags: ";
                    for (Tag t : p.getTags()) {
                        tags = tags + "#" + t.getTag() + " ";
                    }
                    doc.insertString(doc.getLength(), tags + "\n", stylePost);

                    //Add button
                    JFrame parent = this;
                    JButton boton = new JButton("Comment");
                    boton.setName(p.getId().toString());
                    boton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton btn = (JButton) e.getSource();
                            String id = btn.getName(); // puedes guardar el id
//                            JOptionPane.showMessageDialog(parent, btn.getText());
//                            JOptionPane.showMessageDialog(parent, id);
                            String input = JOptionPane.showInputDialog("Add comment: ");
                            System.out.println("Comment: " + input);

                            Comment comment = new Comment(new ObjectId(), new Date(), input, user);
                            postControl.addComment(postControl.findByID(new ObjectId(id)), comment);
                            refreshPane();
                        }
                    });
                    jTextPanePosts.setCaretPosition(jTextPanePosts.getDocument().getLength());
                    jTextPanePosts.insertComponent(boton);

                    //Print comments
                    doc.insertString(doc.getLength(), "\nComments:" + "\n", stylePost);
                    for (Comment c : p.getComments()) {
                        doc.insertString(doc.getLength(), "By user: " + c.getUser().getUsername() + ", Comment: " + c.getComment(), styleComments);
                        
                        //Add remove button to comments of this.user
                        if (user.getId().equals(c.getUser().getId())) {
                            JButton btnRemoveComment = new JButton("Remove");
                            btnRemoveComment.setName(c.getId().toString());
                            btnRemoveComment.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JButton btn = (JButton) e.getSource();
                                    String buttonID = btn.getName();
                                    postControl.removeComment(p, buttonID);
                                    refreshPane();
                                }
                            });
                            jTextPanePosts.setCaretPosition(jTextPanePosts.getDocument().getLength());
                            jTextPanePosts.insertComponent(btnRemoveComment);
                            doc.insertString(doc.getLength(), "\n", stylePost);
                        }

                    }
                    doc.insertString(doc.getLength(), "\n--------------------------------------------------------------------------------------------------" + "\n", null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
            jTextPanePosts.setStyledDocument(doc);
        }

//        if (chatMessages.size() > 0) {
//            for (Message m : chatMessages) {
//                try {
//                    if (m.getUser().getId() == this.user.getId()) {
//                        doc.insertString(doc.getLength(), m.getUser().getUserName() + ": ", style1);
//                        doc.insertString(doc.getLength(), m.getMessage() + "\n\n", null);
//                    } else {
//                        doc.insertString(doc.getLength(), m.getUser().getUserName() + ": ", style2);
//                        doc.insertString(doc.getLength(), m.getMessage() + "\n\n", null);
//                    }
//                } catch (BadLocationException e) {
//                    e.printStackTrace();
//                }
//            }
//            jTextPaneChat.setStyledDocument(doc);
//        }
    }
    
    private void search(){
        String tag = jTextFieldTag.getText();
        ArrayList<Post> posts = postControl.findByTag(tag);
        
        if(posts.size() < 1){
            refreshPane();
        }
        refreshJTextPane(posts);
    }

    private void crearBotonesDinamicos(int numeroBotones) {
        JFrame parent = this;
        for (int i = 1; i <= numeroBotones; i++) {
//            JButton boton = new JButton(String.format("Botón %s", i));
            JButton boton = new JButton("Boton");
            boton.setName(i + " this is the id");
            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    String id = btn.getName(); // puedes guardar el id
                    JOptionPane.showMessageDialog(parent, btn.getText());
                    JOptionPane.showMessageDialog(parent, id);
                }
            });
            this.add(boton);
        }
    }

    private void cleanTextFields() {
        jTextFieldSearch.setText("");
        jTextFieldTag.setText("");
        jTextPanePosts.setText("");
        jTextAreaNewPost.setText("");
    }

    private void newPost() {
        String message = jTextAreaNewPost.getText();
        if (message.length() > 1) {
            fillTagsArray();
            Post post = new Post(new ObjectId(), new Date(), this.user, message, newPostTags, new ArrayList<>());
            postControl.save(post);
            cleanTextFields();
            clearJList();
            newPostTags.clear();
        } else {
            JOptionPane.showMessageDialog(rootPane, "Can't submit an empty post!");
        }
    }

    private void fillTagsArray() {
        DefaultListModel model = (DefaultListModel) jListTags.getModel();

        for (int i = 0; i < model.getSize(); i++) {
            String s = (String) model.getElementAt(i);
            Tag tag = new Tag(new ObjectId(), s);
            newPostTags.add(tag);
        }
    }

    private void addTagToJList() {
        DefaultListModel model = (DefaultListModel) jListTags.getModel();
        model.addElement(jTextFieldTag.getText());
        jTextFieldTag.setText("");
    }

    private void deleteTagJList() {
        int index = jListTags.getSelectedIndex();
        DefaultListModel model = (DefaultListModel) jListTags.getModel();
        model.remove(index);
    }

    private void clearJList() {
        DefaultListModel model = (DefaultListModel) jListTags.getModel();
        model.clear();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelPosts = new javax.swing.JPanel();
        jLabelTitle = new javax.swing.JLabel();
        jLabelSearch = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();
        jScrollPanePosts = new javax.swing.JScrollPane();
        jTextPanePosts = new javax.swing.JTextPane();
        jButtonSearch = new javax.swing.JButton();
        jPanelNewPost = new javax.swing.JPanel();
        jLabelNewPost = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaNewPost = new javax.swing.JTextArea();
        jButtonNewPost = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButtonAddTag = new javax.swing.JButton();
        jLabelTag = new javax.swing.JLabel();
        jTextFieldTag = new javax.swing.JTextField();
        jButtonRemoveTag = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListTags = new javax.swing.JList<>();
        jButtonCancelPost = new javax.swing.JButton();
        jLabelFaceBlog = new javax.swing.JLabel();
        jLabelUsername = new javax.swing.JLabel();
        jButtonRefresh = new javax.swing.JButton();
        jButtonUpdateUser = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FaceBlog");
        setMinimumSize(new java.awt.Dimension(750, 500));
        setResizable(false);

        jPanelPosts.setBorder(new javax.swing.border.MatteBorder(null));

        jLabelTitle.setText("Posts");

        jLabelSearch.setText("Search: ");

        jScrollPanePosts.setViewportView(jTextPanePosts);

        jButtonSearch.setText("Search");

        javax.swing.GroupLayout jPanelPostsLayout = new javax.swing.GroupLayout(jPanelPosts);
        jPanelPosts.setLayout(jPanelPostsLayout);
        jPanelPostsLayout.setHorizontalGroup(
            jPanelPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPostsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPanePosts)
                    .addGroup(jPanelPostsLayout.createSequentialGroup()
                        .addComponent(jLabelTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabelSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch)
                        .addGap(7, 7, 7)))
                .addContainerGap())
        );
        jPanelPostsLayout.setVerticalGroup(
            jPanelPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPostsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelPostsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitle)
                    .addComponent(jLabelSearch)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPanePosts, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelNewPost.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabelNewPost.setText("New Post");

        jLabel1.setText("Text: ");

        jTextAreaNewPost.setColumns(20);
        jTextAreaNewPost.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        jTextAreaNewPost.setRows(5);
        jScrollPane1.setViewportView(jTextAreaNewPost);

        jButtonNewPost.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jButtonNewPost.setText("Post");
        jButtonNewPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNewPostActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jButtonAddTag.setText("Add");
        jButtonAddTag.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddTagActionPerformed(evt);
            }
        });

        jLabelTag.setText("Add Tag:");

        jButtonRemoveTag.setText("Remove");

        jScrollPane2.setViewportView(jListTags);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelTag)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldTag, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonAddTag, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRemoveTag)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelTag)
                            .addComponent(jTextFieldTag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonAddTag)
                            .addComponent(jButtonRemoveTag)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonCancelPost.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jButtonCancelPost.setText("Cancel");

        javax.swing.GroupLayout jPanelNewPostLayout = new javax.swing.GroupLayout(jPanelNewPost);
        jPanelNewPost.setLayout(jPanelNewPostLayout);
        jPanelNewPostLayout.setHorizontalGroup(
            jPanelNewPostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelNewPostLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNewPostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelNewPost)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelNewPostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonNewPost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelNewPostLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonCancelPost)))
                .addGap(12, 12, 12))
        );
        jPanelNewPostLayout.setVerticalGroup(
            jPanelNewPostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelNewPostLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelNewPostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelNewPostLayout.createSequentialGroup()
                        .addComponent(jLabelNewPost)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanelNewPostLayout.createSequentialGroup()
                        .addGap(0, 11, Short.MAX_VALUE)
                        .addGroup(jPanelNewPostLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelNewPostLayout.createSequentialGroup()
                                .addComponent(jButtonNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonCancelPost, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );

        jLabelFaceBlog.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabelFaceBlog.setText("FaceBlog");

        jLabelUsername.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabelUsername.setText("Welcome Username");

        jButtonRefresh.setText("Refresh");

        jButtonUpdateUser.setText("Update User");

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanelNewPost, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelMainLayout.createSequentialGroup()
                        .addComponent(jLabelFaceBlog)
                        .addGap(198, 198, 198)
                        .addComponent(jLabelUsername)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonUpdateUser)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRefresh))
                    .addComponent(jPanelPosts, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelFaceBlog)
                            .addComponent(jLabelUsername))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanelMainLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonRefresh)
                            .addComponent(jButtonUpdateUser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addComponent(jPanelPosts, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelNewPost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(113, 113, 113))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNewPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNewPostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonNewPostActionPerformed

    private void jButtonAddTagActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddTagActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonAddTagActionPerformed

//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Home().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAddTag;
    private javax.swing.JButton jButtonCancelPost;
    private javax.swing.JButton jButtonNewPost;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRemoveTag;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JButton jButtonUpdateUser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelFaceBlog;
    private javax.swing.JLabel jLabelNewPost;
    private javax.swing.JLabel jLabelSearch;
    private javax.swing.JLabel jLabelTag;
    private javax.swing.JLabel jLabelTitle;
    private javax.swing.JLabel jLabelUsername;
    private javax.swing.JList<String> jListTags;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelNewPost;
    private javax.swing.JPanel jPanelPosts;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPanePosts;
    private javax.swing.JTextArea jTextAreaNewPost;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldTag;
    private javax.swing.JTextPane jTextPanePosts;
    // End of variables declaration//GEN-END:variables
}
