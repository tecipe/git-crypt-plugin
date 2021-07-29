# ReadMe

This Intellij IDEA Plugin makes life with git-crypt way better 

## Jetbrains Marketplace

You can find this plugin here: https://plugins.jetbrains.com/plugin/14997-git-crypt

## How to use it

### Prerequisite

You need to install git-crypt on your system.

### Configure git-crypt program path
Click on the 'git-crypt' widget in the toolbar at the bottom of Intellij.  
In the popup menu that opens click on 'git-crypt configure program path'
to open the settings.  
Now set the path to your git-crypt program location.

You will also find the settings under `Preferences -> Other Settings -> git-crypt global`

### Configure git-crypt key path
To lock/unlock your repo you will need to configure the correct key.  
You can do that clicking on the 'git-crypt' widget in the toolbar at the bottom of Intellij.  
In the popup menu that opens click on 'git-crypt configure project key'
to open the project settings.  
Now you can set the path to your git-crypt key location for the **current project**.  
If you prefer to set the path globally you need to navigate to `Preferences -> Other Settings -> git-crypt global`
and set the path to your key here.

### Features

- unlock
- lock
- force key

#### Unlock/Lock
This should be self explanatory

#### Force Key / Fix corrupted encrypted file
Unluckily Git-crypt has a bug where encrypted files get corrupted when unlocked with a wrong key. Now that the file has been changed you have local changes.  
If you try to revert the wrong encrypted files you will get a filter-error because the key is also needed to reverse the correct file.  
To solve this problem you have to set the correct key in the preferences and force the key into the .git directory with the `git-crypt force key` feature.  
After that you can revert the process because now the right key is used for the filter.
