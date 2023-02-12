# Thermostate

A thermostate to be used with our Raspberry pi 2B (or 3B) at home.

# Make it run

You would need a DB created to run app. Nowadays we don't use any DB change control as liquibase, so just reset the
one that is on _/assets_ or create one with the SQLs in each repository.

It reads, in prod, the temperature from a file which is established in thermostate.roomTemp.file in application.yaml

To be executed run, in ./scripts:

```./run-dev.sh```

Or to run in prod environment:

```./run-prod```

# Debug

To debug server, please add --debug-jvm to prompt and then attach intellij to process  

```./run-dev.sh --debug-jvm```  

Flutter frontend is on the way
