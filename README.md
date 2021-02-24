![WarnXS](http://erethon.de/resources/logos/WarnXS.png)

[![Download](http://erethon.de/resources/buttons/Download.png)](http://erethon.de/repo/de/erethon/warnxs)

# What is WarnXS?
WarnXS is a punishment plugin.

You can add penalty points to a player and a configurable console command that arrests, (temporarily) bans or mutes the player for a certain amount of time depending on how many points the player has.

# Configuration instructions
## config.yml
```
# Do not modify this.
configVersion: 1
# The name of the language file.
language: english
# The command index
commands:
  1: "mute <player> 5min"
  2: "jail <player> 10min"
  3: "jail <player> 1h"
  4: "tempban <player> 1d"
  5: "tempban <player> 7d"
# The command to execute if a player gets more PPs
# than the index covers.
deathPenalty: "ban <player>"
# The time in hours until a penalty point gets removed
removeTime: 168
# This is the message a banned player sees above the list
# of warnings. Use &nl for a new line.
infoMessage: Add your message here!
# Remove PPs depending on how many PPs the player already has
removeTimeDelays:
  '8': 24
  '9': 72
  '10': 168
```

## Commands and permissions
### /wxs reload | wxs.reload
Reloads the configuration file and saves the data.

### /wxs add [player] [amount] [reason] | wxs.add
Adds penalty points.

### /wxs list [player=sender] | wxs.list / wxs.list.others
Lists all penalty points that a player has.

### /wxs remove [player] [index] | wxs.remove
Removes a warn entry. Removes a player's warn entry. Leave the index number empty to select (only for SpigotAPI servers!).

# Compatibilty
### Server
_WarnXS_ should work with any version.

### Java
7 and higher

### Known incompatibilities
None
