package de.tecipe.gitcrypt.ui.config.application;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class GitCryptApplicationSettingsConfigurable implements SearchableConfigurable {
  private GitCryptApplicationSettingsForm mySettingsPane;

  public String getDisplayName() {
    return "git-crypt global";
  }

  @NotNull
  public String getId() {
    return "git-crypt.settings";
  }

  public String getHelpTopic() {
    return null;
  }

  public JComponent createComponent() {
    if (mySettingsPane == null) {
      mySettingsPane = new GitCryptApplicationSettingsForm();
    }
    reset();
    return mySettingsPane.getPanel();
  }

  public boolean isModified() {
    return mySettingsPane != null && mySettingsPane.isModified(getSettings());
  }

  public void apply() throws ConfigurationException {
    if (mySettingsPane != null) {
      mySettingsPane.applyEditorTo(getSettings());
    }
  }


  private GitCryptApplicationSettings getSettings() {
    return GitCryptApplicationSettings.getInstance();
  }

  public void reset() {
    if (mySettingsPane != null) {
      mySettingsPane.resetEditorFrom(getSettings());
    }
  }

  public void disposeUIResources() {
    mySettingsPane = null;
  }

  public Runnable enableSearch(String option) {
    return null;
  }
}
