# Homework Tabmo Data-Engineer

_Time to get things done!_

## Présentation

L'objectif du homework est de développer des traitements en temps réel à partir d'un flux de tweets.

Les données seront mises à disposition dans un topic kafka, qui devra être consommé en streaming pour effectuer deux traitements :
 * Mettre à jour en (quasi)temps-réel des compteurs dans une base de données key-value
 * Transmettre dans un nouveau topic un résumé des tweets correspondant à des critères prédéfinis

Les données de tests mises à disposition sont un sampling de tweets (100k messages).

### Contraintes techniques

Aucune technologie n'est imposée pour cet exercice. Vous pouvez utiliser n'importe quel framework/langage, sans contrainte de haute disponibilité, distribution du traitement, etc.

Seule la consommation et la production de message depuis un serveur kafka est imposée.

Vous devrez également intégrer une base de données key-value (RocksDB, Redis, Aerospike, LevelDB...) que vous sélectionnerez en fonction de vos préférences et du contexte technique (pas de contrainte sur la performance, la réplication, etc).

### Pré-requis :

 * Serveur Kafka 1.0.0+
 * Base de données key-value
 * Jeu de données ([télécharger](TODO))

Le jeu de données devra être ingéré dans kafka: `./kafka-console-producer.sh --broker-list 0.0.0.0:9092 --topic tweets < tweets.json`

## Traitement 1 : Mise à jour de compteurs en temps réel

L'objectif est d'enregistrer dans la base de données key-value des compteurs sur l'activité de chaque utilisateur :
 * `tw.$userid.count`: Nombre de tweets envoyés
 * `tw.$userid.fav`: Nombre de favorites (likes) placés sur l'ensemble de ses tweets
 * `tw.$userid.hashtags`: Nombre de hashtags présents dans l'ensemble des tweets de l'utilisateurs
 * `tw.$userid.mentions`: Nombre de fois où l'utilisateur est mentionné par un autre tweet
 * `tw.$userid.name`: Screen name de l'utilisateur (défini à partir du premier tweet traité de l'utilisateur)

Ces enregistrements devront être réalisés en (quasi)temps-réel pour permettre à une API (hors scope du projet) de récupérer en temps réel les statistiques de chaque utilisateur.

Il est important de ne jamais comptabiliser plusieurs fois le même tweet. En revanche, il est accepté que certains tweets ne soient pas comptabilisés en cas de dysfonctionnement du processus.

## Traitement 2 : Production de messages résumés

L'objectif est de filtrer le flux de tweets pour ne garder que ceux qui correspondent à des critères prédéfinis, et d'en envoyer un résumé dans un topic kafka dédié.

Seuls les tweets qui contiennent le terme `mario` (case insensitive) dans le champ `text` du tweet, et sont écrits en langue française devront être gardés.

Le résumé du tweet devra contenir :
 * `id`: identifiant du tweet
 * `user_id`: Identifiant de l'utilisateur
 * `screen_name`: Pseudo de l'utilisateur
 * `created_at`: Date de création du tweet (format: YYYY/MM/DD)
 * `text`: Contenu original du tweet
 * `medias`: Une map contenant l'id de chaque média, et son URL (https) associée (optionnel)
 * `hashtags`: Liste des hashtags associés au tweet (optionnel)

Les tweets résumés devront être envoyés au format `json` dans un topic `tweets-mario-fr`.

Il est important que tous les tweets sans exception correspondant au critère soient envoyés dans le topic kafka. Il est acceptable que le même tweet soit transféré plusieurs fois dans le nouveau topic (le champ `id` permettant de faire une déduplication au besoin).

## Un dernier mot :

Faites simple : nul besoin de développer une usine à gaz gérant 100% des pires scénarios. L'objectif est d'avoir un programme fonctionnel, avec une qualité de code élevée.

De même, utilisez les technologies avec lesquelles vous vous sentez confortable. Nous n'évaluerons pas la maitrise d'un framework particulier, nous porterons notre attention sur la manière dont vous résolvez les problèmes, sur la manière dont vous architecturez vos traitements ainsi que sur la qualité du code produit.

N'hésitez pas à ajouter votre touche à ce projet pour nous démontrer vos compétences dans des domaines qui ne seraient pas couverts.