# Taux de conversion de devises monétaires

## Objectif

Ce workshop consiste en plusieurs fonctions travaillant sur des devises monétaires et leur taux de conversion.

Le code doit etre implémenté dans les fichiers CurrencyRateFetcher.scala et CurrencyRateService.scala. Chaque instruction `???` doit etre remplacée par le code répondant au problème exposé.

Ces exercices ont pour objectif de montrer votre compréhension de l'exécution d'opération asynchrone à l'aide de Future (dans ce cas-ci, pour effectuer un appel HTTP).
Toutes les méthodes fournies par l'api scala sont autorisées.

Des jeux de tests sont déjà fournis dans les classes `CurrencyRateFetcherSpec.scala` et `CurrencyRateServiceSpec.scala`. A minima, tous les tests déjà écrits doivent passer avec succès, et il vous est possible
d'en écrire des supplémentaires si nécessaire.

## Comment lancer le projet ?

Le seul outil nécessaire pour exécuter les tests du projet est [sbt](http://www.scala-sbt.org/download.html).

Une fois `sbt` disponible dans le PATH du terminal, se placer à la racine du projet (attention, pas au niveau de ce readme,
mais au niveau du fichier `build.sbt`), et exécuter les commandes :

```
julien$ sbt
[info] Loading global plugins from /home/julien/.sbt/0.13/plugins
[info] Loading project definition from /home/julien/work/projects/homework/backend/scala/project
[info] Set current project to tabmo-homework-scala (in build file:/home/julien/work/projects/homework/backend/scala/)
> project currencies
[info] Set current project to dna (in build file:/home/julien/work/projects/homework/backend/scala/)
> test
```

Pour travailler sur le code, le plus simple est d'ouvrir le projet SBT avec IDEA. La version communautaire (gratuite) d'intellij avec le plugin `scala` est suffisante.
Une fois installé, il suffit d'ouvrir le dossier racine du projet dans IDEA.

En cas de difficulté sur la mise en place de l'environnement, ne pas hésiter à me contacter !

