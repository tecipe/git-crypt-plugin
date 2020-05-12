package de.tecipe.gitcrypt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import de.tecipe.gitcrypt.ui.config.application.GitCryptApplicationSettingsConfigurable;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class GitCryptOpenApplicationSettingsAction extends AnAction {

  public GitCryptOpenApplicationSettingsAction() {
    super("git-crypt configure program path");
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    super.update(e);
  }

  @SneakyThrows
  @Override
  public void actionPerformed(AnActionEvent e) {
    ShowSettingsUtil.getInstance().showSettingsDialog(e.getProject(), GitCryptApplicationSettingsConfigurable.class);
  }

}
