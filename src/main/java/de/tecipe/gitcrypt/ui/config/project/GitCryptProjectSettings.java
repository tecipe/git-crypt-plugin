package de.tecipe.gitcrypt.ui.config.project;


import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import lombok.Getter;
import lombok.Setter;
import org.jdom.Element;

@State(
  name = "GitCryptProjectSettings",
  storages = {
    @Storage("git-crypt-project.xml")
  }
)
@Getter
@Setter
public class GitCryptProjectSettings implements PersistentStateComponent<Element> {
  public static final String GITCRYPT_SETTINGS = "GitCryptProjectSettings";
  public static final String KEYPATH = "KEYPATH";
  private String keyPath = "";
  private long refreshIntervalInMs = 1000;


  public static GitCryptProjectSettings getInstance(Project project) {
    return ServiceManager.getService(project, GitCryptProjectSettings.class);
  }

  @Override
  public void loadState(Element state) {
    keyPath = state.getAttributeValue(KEYPATH, "");
  }

  @Override
  public Element getState() {
    final Element element = new Element(GITCRYPT_SETTINGS);
    element.setAttribute(KEYPATH, keyPath);
    return element;
  }
}
