package de.tecipe.gitcrypt;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import de.tecipe.gitcrypt.ui.config.application.GitCryptApplicationSettings;
import de.tecipe.gitcrypt.ui.config.project.GitCryptProjectSettings;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static de.tecipe.gitcrypt.Utils.isGitCryptPathConfigured;

public class GitCryptForceKeyAction extends AnAction {

  public GitCryptForceKeyAction() {
    super("git-crypt force key");
  }

  @Override
  public void update(@NotNull AnActionEvent e) {
    e.getPresentation().setEnabled(Utils.isGitRepository(e.getProject()) && isGitCryptPathConfigured());
    super.update(e);
  }

  @SneakyThrows
  @Override
  public void actionPerformed(AnActionEvent e) {
    GitCryptApplicationSettings applicationSettings = GitCryptApplicationSettings.getInstance();
    GitCryptProjectSettings projectSettings = GitCryptProjectSettings.getInstance(e.getProject());
    String applicationKeyPath = applicationSettings.getKeyPath();
    String projectKeyPath = projectSettings.getKeyPath();
    String relevantKey = projectKeyPath != null && !projectKeyPath.trim().isEmpty() ? projectKeyPath : applicationKeyPath;
    if (relevantKey == null || relevantKey.trim().isEmpty()) {
      Notifications.Bus.notify(new Notification("git-crypt", "git-crypt error", "No key found, go to settings to configure your key path", NotificationType.ERROR));
      return;
    }

    Path copied = Paths.get(Utils.getStoredKeyFile(e.getProject().getBasePath()).getPath());
    Path originalPath = Paths.get(relevantKey);
    Files.copy(originalPath, copied, StandardCopyOption.REPLACE_EXISTING);
    Notifications.Bus.notify(new Notification("git-crypt", "git-crypt success", "Successfully force added your key. To fix your repository you probably need to revert your local changes now.", NotificationType.INFORMATION));
  }

}
