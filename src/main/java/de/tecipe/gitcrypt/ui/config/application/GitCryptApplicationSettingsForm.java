package de.tecipe.gitcrypt.ui.config.application;

import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@Getter
public class GitCryptApplicationSettingsForm {

  private JTextField keyPath;
  private JPanel panel;
  private JButton browseKey;
  private JTextField gitCryptPath;
  private JButton browsePath;
  private JComboBox lockedIcon;
  private JComboBox unlockedIcon;


  public GitCryptApplicationSettingsForm() {
    browseKey.addActionListener(e -> browseForFile(keyPath));
    browsePath.addActionListener(e -> browseForFile(gitCryptPath));
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

  public void applyEditorTo(GitCryptApplicationSettings settings) {
    settings.setKeyPath(keyPath.getText());
    settings.setGitCryptPath(gitCryptPath.getText());
    settings.setLockedIconOption(IconOptions.values()[lockedIcon.getSelectedIndex()]);
    settings.setUnlockedIconOption(IconOptions.values()[unlockedIcon.getSelectedIndex()]);
  }

  public boolean isModified(GitCryptApplicationSettings settings) {
    return settings == null
      || !keyPath.getText().equals(settings.getKeyPath())
      || !gitCryptPath.getText().equals(settings.getGitCryptPath())
      || lockedIcon.getSelectedIndex() != settings.getLockedIconOption().ordinal()
      || unlockedIcon.getSelectedIndex() != settings.getUnlockedIconOption().ordinal();
  }

  public void resetEditorFrom(GitCryptApplicationSettings settings) {
    keyPath.setText(settings.getKeyPath());
    gitCryptPath.setText(settings.getGitCryptPath());
    lockedIcon.setSelectedIndex(settings.getLockedIconOption().ordinal());
    unlockedIcon.setSelectedIndex(settings.getUnlockedIconOption().ordinal());
  }
}
