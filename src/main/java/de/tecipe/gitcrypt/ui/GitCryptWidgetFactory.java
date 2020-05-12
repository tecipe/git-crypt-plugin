package de.tecipe.gitcrypt.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class GitCryptWidgetFactory implements StatusBarWidgetFactory {

  @NotNull
  @Override
  public String getId() {
    return "git-crypt.widget";
  }

  @Nls
  @NotNull
  @Override
  public String getDisplayName() {
    return "";
  }

  @SneakyThrows
  @Override
  public boolean isAvailable(@NotNull Project project) {
    return true;
  }

  @NotNull
  @Override
  public StatusBarWidget createWidget(@NotNull Project project) {
    return new GitCryptIconWidget(project);
  }

  @Override
  public void disposeWidget(@NotNull StatusBarWidget widget) {
  }

  @Override
  public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
    return true;
  }
}
