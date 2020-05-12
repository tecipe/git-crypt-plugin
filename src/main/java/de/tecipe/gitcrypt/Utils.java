package de.tecipe.gitcrypt;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vcs.ProjectLevelVcsManager;
import de.tecipe.gitcrypt.ui.config.application.GitCryptApplicationSettings;
import git4idea.GitVcs;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jetbrains.annotations.NotNull;

public class Utils {

  public static String executeCommand(String command, String workingDir) throws InterruptedException, IOException {
    Runtime runTime = Runtime.getRuntime();
    Process process = runTime.exec(command, null, new File(workingDir));
    process.waitFor();
    try (BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
      StringBuilder sb = new StringBuilder();
      String s;
      while ((s = stdInput.readLine()) != null) {
        sb.append(s);
      }
      return sb.toString();
    }
  }

  public static boolean isUnlocked(Project project) {
    if (!isGitRepository(project)) {
      return false;
    }
    File defaultKey = getStoredKeyFile(project.getBasePath());
    return defaultKey.isFile();
  }

  @NotNull
  public static File getStoredKeyFile(String projectBasePath) {
    return createFile(projectBasePath, ".git", "git-crypt", "keys", "default");
  }

  public static File createFile(String mainPath, String... pathParts) {
    File result = new File(mainPath);
    for (String pathPart : pathParts) {
      result = new File(result, pathPart);
    }
    return result;
  }

  public static boolean isGitCryptInitialized(@NotNull Project project) throws IOException {
    return GitAttributesUtils.get(project).isGitCrypt();
  }

  public static boolean isGitRepository(@NotNull Project project) {
    ProjectLevelVcsManager vcsManager = ProjectLevelVcsManager.getInstance(project);
    return vcsManager.getRootsUnderVcs(GitVcs.getInstance(project)).length > 0;
  }

  public static boolean isGitCryptPathConfigured() {
    String gitCryptPath = GitCryptApplicationSettings.getInstance().getGitCryptPath();
    return gitCryptPath != null && !gitCryptPath.trim().isEmpty();
  }


}
