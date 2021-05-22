# Homework Tabmo Backend Engineer Scala (2021)

_Time to get things done!_

## Présentation

Ce homework a pour objectif de développer un mini-serveur charger d'aggréger des statistiques à partir d'un flux d'éléments, et de le mettre à disposition ces statistiques via une api HTTP.

Les données brutes seront disponibles dans un topic kafka, qui devra être consommé en streaming pour mettre à jour en (quasi)temps-réel des compteurs dans une base de données clé-valeur.

Ces statistiques seront exposées dans une API HTTP REST JSON.

Les données de tests mises à disposition sont un sampling de tweets (100k messages).

### Contraintes techniques

Ce coding-challenge doit être réalisé en *Scala* avec la plateforme de messagerie *Kafka*.

Vous pouvez intégrer à votre projet n'importe quel framework, librairie, sans contrainte. Une approche asynchrone dans la définition des traitements est à privilégier.

Vous devrez également intégrer une base de données clé-valeur (Redis, RocksDB, Aerospike, LevelDB...) que vous sélectionnerez en fonction de vos préférences (pas de contrainte sur la performance, la réplication, etc).

### Pré-requis :

 * Serveur Kafka
 * Base de données clé-valeur
 * Jeu de données 
([télécharger](https://s3-eu-west-1.amazonaws.com/static.tabmo.io/jobs/dataeng/tweets-v2.json))

Le jeu de données devra être ingéré dans kafka: `./kafka-console-producer.sh --broker-list 0.0.0.0:9092 --topic tweets < tweets.json`

## Epic 1 : Mise à jour de compteurs en temps réel

L'objectif est d'enregistrer dans la base de données clé-valeur des compteurs sur l'activité de chaque utilisateur référencé par son `userid`.

 * Nombre de tweets envoyés
 * Nombre de favorites (likes) placés sur l'ensemble de ses tweets
 * Nombre de hashtags présents dans l'ensemble des tweets de l'utilisateur
 * Nombre de fois où l'utilisateur est mentionné par un autre tweet
 * (Bonus) Screen name de l'utilisateur (récupéré à partir du premier tweet)

Ces enregistrements devront être réalisés en (quasi)temps-réel pour permettre à l'API d'exposer les données les plus à jour possible.

Il est important de ne jamais comptabiliser plusieurs fois le même tweet. En revanche, il est accepté que certains tweets ne soient pas comptabilisés en cas de dysfonctionnement du processus.

## Epic 2 : Exposition des données via une API REST

L'objectif est d'exposer les données enregistrées dans la base clé-valeur par le premier traitement via une API HTTP. Les données seront exposées au format JSON.

Les endpoints attendus sont décrits ci-dessous :

### 2.1. Statistiques d'un utilisateur

#### Requête 

`GET /users/{userid}`

#### Réponse

Si l'utilisateur n'est pas connu : `404 Not Found`

Si l'utilisateur est connu `200 OK`

```
{
    "id": 1006566900351062000,
    "screenname": "InklingMario",
    "tweets": 43,
    "favorites": 12
    "hashtags": 105,
    "mentions": 22 
}
```

### 2.2. Nombre de tweets d'un utilisateur

#### Requête 

`GET /users/{userid}/tweets`

#### Réponse

Si l'utilisateur n'est pas connu : `404 Not Found`

Si l'utilisateur est connu `200 OK`

```
43
```

### (BONUS) 2.3. Liste de tous les utilisateurs connus

Retourne les `userid` de tous les utilisateurs connus par le système

#### Requête 

`GET /users`

#### Réponse

`200 OK`

```
[
    1006566900351062000,
    1006566900392964100
]
```

## Un dernier mot :

Faites simple : nul besoin de développer une usine à gaz gérant 100% des pires scénarios. L'objectif est d'avoir un programme fonctionnel, avec une qualité de code élevée.

De même, utilisez les technologies avec lesquelles vous vous sentez confortable. Nous n'évaluerons pas la maitrise d'un framework particulier, nous porterons notre attention sur la manière dont vous résolvez les problèmes, sur la manière dont vous architecturez vos traitements ainsi que sur la qualité du code produit.

N'hésitez pas à ajouter votre touche à ce projet pour nous démontrer vos compétences dans des domaines qui ne seraient pas couverts.
