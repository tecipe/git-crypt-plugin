package de.tecipe.gitcrypt.ui.config.project;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@Getter
public class GitCryptProjectSettingsForm {

  private JTextField keyPath;
  private JPanel panel;
  private JButton browse;


  public GitCryptProjectSettingsForm() {
    browse.addActionListener(e -> browseForFile(keyPath));
  }

  private void browseForFile(@NotNull final JTextField target) {
    final FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFileOrExecutableAppDescriptor();
    descriptor.setTitle("Select path to your default git-crypt key");
    String text = target.getText();
    final VirtualFile toSelect = text == null || text.isEmpty() ? null
      : LocalFileSystem.getInstance().findFileByPath(text);

    VirtualFile virtualFile = FileChooser.chooseFile(descriptor, null, toSelect);
    if (virtualFile != null) {
      target.setText(virtualFile.getPath());
    }
  }

  public void applyEditorTo(GitCryptProjectSettings settings) {
    settings.setKeyPath(keyPath.getText());
  }

  public boolean isModified(GitCryptProjectSettings settings) {
    return settings == null || !keyPath.getText().equals(settings.getKeyPath());
  }

  public void resetEditorFrom(GitCryptProjectSettings settings) {
    keyPath.setText(settings.getKeyPath());
  }
}
