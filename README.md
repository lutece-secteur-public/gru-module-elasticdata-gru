![](http://dev.lutece.paris.fr/jenkins/buildStatus/icon?job=gru-module-elasticdata-gru-deploy)
# Module elasticdata gru

![](http://dev.lutece.paris.fr/plugins/module-elasticdata-gru/images/elastic.png)

## Introduction

Module de production de donn&eacute;es de la GRU pour ElasticSearch / Kibana

## Configuration

Configurer les labels des types de demande dans le fichier **elasticdata-gru.properties** 

L'initialisation des beans de DataSource n&eacute;cessite la valorisation des DAOs&nbsp;&agrave; utiliser. De plus, les DataSource impl&eacute;mente les interfaces IDemandListener et INotificationListener qui sont donc utilisable dans DemandService.


[Maven documentation and reports](http://dev.lutece.paris.fr/plugins/module-elasticdata-gru/)



 *generated by [xdoc2md](https://github.com/lutece-platform/tools-maven-xdoc2md-plugin) - do not edit directly.*