package de.tecipe.gitcrypt.ui;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Conditions;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.util.Consumer;
import de.tecipe.gitcrypt.Utils;
import de.tecipe.gitcrypt.ui.config.application.GitCryptApplicationSettings;
import de.tecipe.gitcrypt.ui.config.application.IconOptions;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class GitCryptWidgetPresentation implements StatusBarWidget.MultipleTextValuesPresentation {
  private Project project;
  private final RootActions rootActions;

  public GitCryptWidgetPresentation(Project project) {
    this.project = project;
    rootActions = new RootActions();
  }

  @Nullable("null means the widget is unable to show the popup")
  @Override
  public ListPopup getPopupStep() {
    if (!Utils.isGitRepository(project)) {
      return null;
    }

    if (!Utils.isGitCryptPathConfigured()) {
      Notifications.Bus.notify(new Notification("git-crypt", "git-crypt warning", "No path set to your git-crypt installation, some actions are disabled until its configured", NotificationType.WARNING));
    }

    return new GitCryptWidgetPopup("git-crypt", rootActions, this.project, Conditions.alwaysTrue());
  }

  @Nullable
  @Override
  public String getSelectedValue() {
    return Utils.isGitRepository(project) ? "git-crypt" : null;
  }

  @SneakyThrows
  @Nullable
  @Override
  public Icon getIcon() {
    Icon icon = null;
    if (Utils.isGitRepository(project) && (Utils.isGitCryptInitialized(project))) {
      if (Utils.isUnlocked(project)) {
        icon = getIcon(GitCryptApplicationSettings.getInstance().getUnlockedIconOption());
      } else {
        icon = getIcon(GitCryptApplicationSettings.getInstance().getLockedIconOption());
      }
    }
    return icon;
  }

  @SneakyThrows
  @Nls(capitalization = Nls.Capitalization.Sentence)
  @Nullable
  @Override
  public String getTooltipText() {
    if (Utils.isGitCryptInitialized(project)) {
      return Utils.isUnlocked(project) ? "unlocked" : "locked";
    }
    return "not initialized";
  }

  @Nullable
  @Override
  public Consumer<MouseEvent> getClickConsumer() {
    return null;
  }

  private Icon getIcon(IconOptions iconOptions) {
    if (iconOptions == IconOptions.DISABLED) {
      return null;
    }

    String prefix = "locked";
    if (Utils.isUnlocked(project)) {
      prefix = "unlocked";
    }

    return IconLoader.getIcon("/icons/" + prefix + "-" + iconOptions.name().toLowerCase() + ".png");
  }


}
