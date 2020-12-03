package de.tecipe.gitcrypt.ui.config.application;


import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import lombok.Getter;
import lombok.Setter;
import org.jdom.Element;

@State(
  name = "GitCryptApplicationSettings",
  storages = {
    @Storage("git-crypt.xml")
  }
)
@Getter
@Setter
public class GitCryptApplicationSettings implements PersistentStateComponent<Element> {
  public static final String GITCRYPT_SETTINGS = "GitCryptApplicationSettings";
  public static final String KEYPATH = "KEYPATH";
  public static final String GITCRYPTPATH = "GITCRYPTPATH";
  public static final String LOCKEDICONOPTION = "LOCKEDICONOPTION";
  public static final String UNLOCKEDICONOPTION = "UNLOCKEDICONOPTION";
  private String keyPath = "";
  private String gitCryptPath = "";
  private IconOptions lockedIconOption = IconOptions.RED;
  private IconOptions unlockedIconOption = IconOptions.BLACK;
  private long refreshIntervalInMs = 1000;


  public static GitCryptApplicationSettings getInstance() {
    return ServiceManager.getService(GitCryptApplicationSettings.class);
  }

  @Override
  public void loadState(Element state) {
    keyPath = state.getAttributeValue(KEYPATH, "");
    gitCryptPath = state.getAttributeValue(GITCRYPTPATH, "");
    lockedIconOption = IconOptions.valueOf(state.getAttributeValue(LOCKEDICONOPTION, IconOptions.RED.name()));
    unlockedIconOption = IconOptions.valueOf(state.getAttributeValue(UNLOCKEDICONOPTION, IconOptions.BLACK.name()));
  }

  @Override
  public Element getState() {
    final Element element = new Element(GITCRYPT_SETTINGS);
    element.setAttribute(KEYPATH, keyPath);
    element.setAttribute(GITCRYPTPATH, gitCryptPath);
    element.setAttribute(LOCKEDICONOPTION, lockedIconOption.name());
    element.setAttribute(UNLOCKEDICONOPTION, unlockedIconOption.name());
    return element;
  }
}


