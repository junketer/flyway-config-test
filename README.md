# flyway-config-test
Test code for configuring flyway in java

Shows how we can add in scripts in a folder other than the default (, and set the schema to manage the flyway config
Should allow us to isolate the SSIMOUT schema for each product from the common snapshot data load

see src/main/resources/flyway.config - provides the config to the flyway instance

see src\main\resources\ssim\sql - the migration scripts
