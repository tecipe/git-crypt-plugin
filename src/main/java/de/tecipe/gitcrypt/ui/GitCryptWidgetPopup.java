package de.tecipe.gitcrypt.ui;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.util.Condition;
import com.intellij.ui.popup.PopupFactoryImpl;
import org.jetbrains.annotations.NotNull;

public class GitCryptWidgetPopup extends PopupFactoryImpl.ActionGroupPopup {

  public GitCryptWidgetPopup(String title, @NotNull RootActions actionGroup, @NotNull DataContext dataContext,
      Condition<AnAction> preselectActionCondition) {
    super(title, actionGroup, dataContext,
        false, false, true, false,
        null, -1, preselectActionCondition, null);
  }
}
