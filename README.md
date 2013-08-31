TeamCity plugin embedding a clojure nrepl.

Starts nrepl on port 7888. Provides some commonly used beans as vars
inside ```tc``` namespace. Loads file
```<TEAMCITY_DATA_DIR>/config/init.clj``` so you can save your scripts
and experiments there.