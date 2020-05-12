package de.tecipe.gitcrypt.ui;

import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import de.tecipe.gitcrypt.GitCryptAction;
import de.tecipe.gitcrypt.GitCryptForceKeyAction;
import de.tecipe.gitcrypt.GitCryptOpenApplicationSettingsAction;
import de.tecipe.gitcrypt.GitCryptOpenProjectSettingsAction;

public class RootActions extends DefaultActionGroup {

  public RootActions() {
    super("", true);
    add(new GitCryptForceKeyAction());
    add(new GitCryptAction());
    add(new GitCryptOpenProjectSettingsAction());
    add(new GitCryptOpenApplicationSettingsAction());
  }


  @Override
  public boolean canBePerformed(DataContext context) {
    return true;
  }
}
