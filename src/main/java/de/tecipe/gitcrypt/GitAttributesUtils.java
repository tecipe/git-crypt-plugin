package de.tecipe.gitcrypt;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GitAttributesUtils {

  private static final GitAttributesUtils INSTANCE = new GitAttributesUtils();

  private Project project;
  @Getter
  private long lastModified = 0;
  @Getter
  private boolean gitCrypt = false;

  public static GitAttributesUtils get(Project project) {
    INSTANCE.set(project);
    return INSTANCE;
  }

  public void set(Project project) {
    if (this.project != project) {
      this.project = project;
      this.lastModified = 0;
      this.gitCrypt = false;
    }
    long lastMod = exists() ? readLastModified() : 0;
    if (this.lastModified != lastMod) {
      this.lastModified = lastMod;
      this.gitCrypt = exists() && checkForGitCrypt();
    }
  }

  private boolean checkForGitCrypt() {
    Path path = createPath();
    System.out.println("reading file " + path.toString());
    try {
      String content = new String(Files.readAllBytes(path));
      return content.contains("filter=git-crypt");
    } catch (IOException e) {
      Notifications.Bus.notify(new Notification("git-crypt", "git-crypt error",
          "Failed to read " + path.toFile().getAbsolutePath(), NotificationType.ERROR));
      return false;
    }
  }

  private long readLastModified() {
    Path path = createPath();
    try {
      FileTime lastModifiedTime = Files.getLastModifiedTime(path);
      return lastModifiedTime.toMillis();
    } catch (IOException e) {
      Notifications.Bus.notify(new Notification("git-crypt", "git-crypt error",
          "Failed to read " + path.toFile().getAbsolutePath(), NotificationType.ERROR));
    }
    return 0;
  }

  private boolean exists() {
    Path path = createPath();
    return Files.exists(path);
  }

  private Path createPath() {
    String basePath = Objects.requireNonNull(project.getBasePath());
    return Paths.get(basePath, ".gitattributes");
  }


}
