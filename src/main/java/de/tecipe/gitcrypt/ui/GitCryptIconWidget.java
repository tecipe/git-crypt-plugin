package de.tecipe.gitcrypt.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import de.tecipe.gitcrypt.ui.config.application.GitCryptApplicationSettings;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GitCryptIconWidget implements StatusBarWidget {

  private boolean forceExit = false;
  private Project project;

  @Contract(pure = true)
  public GitCryptIconWidget(Project project) {
    this.project = project;
  }

  @NotNull
  @Override
  public String ID() {
    return "git-crypt.widget";
  }

  @Nullable
  @Override
  public WidgetPresentation getPresentation() {
    return new GitCryptWidgetPresentation(project);
  }

  @Override
  public void install(@NotNull StatusBar statusBar) {
    ApplicationManager.getApplication().executeOnPooledThread(() -> continuousGitCryptStatusWidgetUpdate(statusBar));
  }

  private void continuousGitCryptStatusWidgetUpdate(StatusBar statusBar) {
    try {
      GitCryptApplicationSettings settingsService = ServiceManager.getService(GitCryptApplicationSettings.class);
      while (!forceExit) {
        statusBar.updateWidget("git-crypt.widget");
        Thread.sleep(settingsService.getRefreshIntervalInMs());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void dispose() {
    forceExit = true;
  }
}
