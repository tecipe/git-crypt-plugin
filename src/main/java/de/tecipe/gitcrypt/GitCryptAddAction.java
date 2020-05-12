package de.tecipe.gitcrypt;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileWriter;

import static de.tecipe.gitcrypt.Utils.isGitCryptPathConfigured;

public class GitCryptAddAction extends AnAction {


  @Override
  public void update(@NotNull AnActionEvent e) {
    e.getPresentation().setEnabled(Utils.isGitRepository(e.getProject()) && Utils.isUnlocked(e.getProject()) && isGitCryptPathConfigured());
    super.update(e);
  }

  @SneakyThrows
  @Override
  public void actionPerformed(AnActionEvent e) {
    VirtualFile virtualFile = (VirtualFile) e.getDataContext().getData(CommonDataKeys.VIRTUAL_FILE.getName());
    File file = new File(virtualFile.getPath());
    String relativePath = new File(e.getProject().getBasePath()).toURI().relativize(file.toURI()).getPath();
    File gitattributesFile = new File(e.getProject().getBasePath(), ".gitattributes");
    try (FileWriter fw = new FileWriter(gitattributesFile, true)) {
      fw.write("/" + relativePath + " filter=git-crypt diff=git-crypt");
    }

    Utils.executeCommand("git rm --cached " + relativePath, e.getProject().getBasePath());
    Utils.executeCommand("git add " + relativePath, e.getProject().getBasePath());
  }

}
