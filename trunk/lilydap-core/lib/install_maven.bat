mvn install:install-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.1.0 -Dpackaging=jar -Dfile=ojdbc14-10.2.0.1.0.jar
mvn deploy:deploy-file -DgroupId=com.oracle -DartifactId=ojdbc14 -Dversion=10.2.0.1.0 -Dpackaging=jar -Dfile=ojdbc14-10.2.0.1.0.jar -Durl=http://10.101.126.10:9000/nexus/content/repositories/thirdparty -DrepositoryId=thirdparty 