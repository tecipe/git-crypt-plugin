package de.tecipe.gitcrypt;

import static de.tecipe.gitcrypt.Utils.isGitCryptPathConfigured;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import de.tecipe.gitcrypt.ui.config.application.GitCryptApplicationSettings;
import de.tecipe.gitcrypt.ui.config.project.GitCryptProjectSettings;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public class GitCryptAction extends AnAction {

  public GitCryptAction() {
    super("git-crypt unlock");
  }

  @SneakyThrows
  @Override
  public void update(@NotNull AnActionEvent e) {
    boolean isGitCryptInitialized = Utils.isGitCryptInitialized(e.getProject());

    e.getPresentation().setText(Utils.isUnlocked(e.getProject()) ? "git-crypt lock" : "git-crypt unlock");
    e.getPresentation().setEnabled(Utils.isGitRepository(e.getProject()) && isGitCryptInitialized && isGitCryptPathConfigured());

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

    boolean isUnlocked = Utils.isUnlocked(e.getProject());
    Runtime runTime = Runtime.getRuntime();
    String[] commandArray = isUnlocked ?
        new String[]{applicationSettings.getGitCryptPath(), "lock"} :
        new String[]{applicationSettings.getGitCryptPath(), "unlock", relevantKey};
    Process process = runTime.exec(commandArray, null, new File(e.getProject().getBasePath()));
    process.waitFor();
    boolean isUnlockedAfter = Utils.isUnlocked(e.getProject());
    if (isUnlocked == isUnlockedAfter) {
      Notifications.Bus.notify(new Notification("git-crypt", "git-crypt error",
          "Failed to execute " + Arrays.toString(commandArray) + "  "
          + readError(process), NotificationType.ERROR));
    } else {
      Notifications.Bus.notify(new Notification("git-crypt", "git-crypt success",
          "Successfully executed " + Arrays.toString(commandArray), NotificationType.INFORMATION));
    }
  }

  private static String readError(Process process) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader in = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
      String str;
      while ((str = in.readLine()) != null) {
        sb.append(str);
      }
    } catch (IOException e) {
      System.out.println("failed to read error");
    }
    return sb.toString();
  }


}
