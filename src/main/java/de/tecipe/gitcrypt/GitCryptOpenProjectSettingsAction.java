package de.tecipe.gitcrypt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import de.tecipe.gitcrypt.ui.config.project.GitCryptProjectSettingsConfigurable;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class GitCryptOpenProjectSettingsAction extends AnAction {

  public GitCryptOpenProjectSettingsAction() {
    super("git-crypt configure project key");
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
  }

  @SneakyThrows
  @Override
  public void actionPerformed(AnActionEvent e) {
    ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), GitCryptProjectSettingsConfigurable.class);
  }

}
