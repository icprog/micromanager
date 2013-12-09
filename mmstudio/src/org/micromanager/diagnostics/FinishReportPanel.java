/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.micromanager.diagnostics;

/**
 *
 * @author Mark
 */
public class FinishReportPanel extends javax.swing.JPanel {

   /**
    * Creates new form FinishReportPanel
    */
   public FinishReportPanel() {
      initComponents();
   }

   /**
    * This method is called from within the constructor to initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is always
    * regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jLabel1 = new javax.swing.JLabel();
      jScrollPane1 = new javax.swing.JScrollPane();
      logTextArea_ = new javax.swing.JTextArea();
      jLabel2 = new javax.swing.JLabel();
      jLabel3 = new javax.swing.JLabel();
      jScrollPane2 = new javax.swing.JScrollPane();
      afterthoughtsTextArea_ = new javax.swing.JTextArea();
      nextButton_ = new javax.swing.JButton();
      backButton_ = new javax.swing.JButton();
      cancelButton_ = new javax.swing.JButton();
      jSeparator1 = new javax.swing.JSeparator();
      jSeparator2 = new javax.swing.JSeparator();

      jLabel1.setFont(jLabel1.getFont().deriveFont(jLabel1.getFont().getStyle() | java.awt.Font.BOLD, jLabel1.getFont().getSize()+3));
      jLabel1.setText("Finish Issue Report");

      logTextArea_.setEditable(false);
      logTextArea_.setColumns(20);
      logTextArea_.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
      logTextArea_.setRows(5);
      jScrollPane1.setViewportView(logTextArea_);

      jLabel2.setFont(jLabel2.getFont().deriveFont(jLabel2.getFont().getSize()+1f));
      jLabel2.setText("Captured Log:");

      jLabel3.setFont(jLabel3.getFont().deriveFont(jLabel3.getFont().getSize()+1f));
      jLabel3.setText("Additional Comments:");

      afterthoughtsTextArea_.setColumns(20);
      afterthoughtsTextArea_.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
      afterthoughtsTextArea_.setLineWrap(true);
      afterthoughtsTextArea_.setRows(5);
      afterthoughtsTextArea_.setWrapStyleWord(true);
      jScrollPane2.setViewportView(afterthoughtsTextArea_);

      nextButton_.setText("Next >");

      backButton_.setText("< Back");

      cancelButton_.setText("Cancel");

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
               .addComponent(jScrollPane1)
               .addComponent(jScrollPane2)
               .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                  .addComponent(cancelButton_)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 417, Short.MAX_VALUE)
                  .addComponent(backButton_)
                  .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                  .addComponent(nextButton_))
               .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                     .addComponent(jLabel1)
                     .addComponent(jLabel2)
                     .addComponent(jLabel3))
                  .addGap(0, 0, Short.MAX_VALUE))
               .addComponent(jSeparator2))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
            .addGap(18, 18, 18)
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(nextButton_)
               .addComponent(backButton_)
               .addComponent(cancelButton_))
            .addContainerGap())
      );
   }// </editor-fold>//GEN-END:initComponents


   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JTextArea afterthoughtsTextArea_;
   private javax.swing.JButton backButton_;
   private javax.swing.JButton cancelButton_;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JLabel jLabel2;
   private javax.swing.JLabel jLabel3;
   private javax.swing.JScrollPane jScrollPane1;
   private javax.swing.JScrollPane jScrollPane2;
   private javax.swing.JSeparator jSeparator1;
   private javax.swing.JSeparator jSeparator2;
   private javax.swing.JTextArea logTextArea_;
   private javax.swing.JButton nextButton_;
   // End of variables declaration//GEN-END:variables
}