package de.tecipe.gitcrypt.ui.config.project;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class GitCryptProjectSettingsConfigurable implements SearchableConfigurable {
  private GitCryptProjectSettingsForm mySettingsPane;
  private final Project myProject;

  public GitCryptProjectSettingsConfigurable(Project project) {
    this.myProject = project;
  }

  public String getDisplayName() {
    return "git-crypt project";
  }

  @NotNull
  public String getId() {
    return "git-crypt-project.settings";
  }

  public String getHelpTopic() {
    return null;
  }

  public JComponent createComponent() {
    if (mySettingsPane == null) {
      mySettingsPane = new GitCryptProjectSettingsForm();
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


  private GitCryptProjectSettings getSettings() {
    return GitCryptProjectSettings.getInstance(myProject);
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
