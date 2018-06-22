# Homework Tabmo API

_Time to get things done!_

L'objectif du homework est de développer une application répondant aux besoins exprimés par le responsable fonctionnel. L'application devra exposer une API respectant le style d'architecture REST. Le développement du client (HTML/JS) ne rentre pas dans le cadre de ce homework.

N'hésitez pas à contacter la personne jouant le rôle de responsable fonctionnel (julien.lafont@tabmo.io) si vous avez la moindre question ou incertitude concernant les spécifications décrites ci-dessous.

**Important** : Sur les 3 [epics](http://www.fabrice-aimetti.fr/dotclear/index.php?post/2009/08/20/La-difference-entre-les-termes-Agiles-Themes-Epics-et-User-Stories) décrites ci-dessous, nous vous demandons d'en réaliser **2** pour ce homework. Vous êtes libre de sélectionner les thématiques que vous trouvez les plus intéressantes ou les plus challengeantes.

Voici les fonctionnalités telles que décrites par le responsable fonctionnel :

## Epic 1 : Gestion d'une médiathèque

La première partie de l'application simulera les fonctions essentielles d'une médiathèque en ligne, c'est à dire ajouter et lister les films que possède l'utilisateur.

#### US 1-1 : En tant qu'utilisateur, je veux ajouter un film à la médiathèque pour le retrouver ultérieurement.

Les données du films envoyées par le client doivent respecter le format suivant :

Type Movie :

Champ                    | Champ json     | requis ?  | Commentaire
-------------------------|----------------|-----------|----------------------------
Titre                    | title          | oui       | 250 caractères maximum
Nationalité              | country        | oui       | Format ISO 3166-1 alpha-3 (3 caractères)
Années de production     | year           | oui
Titre original           | original_title | *         | Requis si le film est de nationalité étrangère (country != "FRA"), 250 caractères maximum
Date de sortie française | french_release | non       | format YYYY/MM/DD (ex: 2016/08/23)
Synopsis                 | synopsis       | non       |
Genres                   | genre          | requis    | Liste d'au moins un élément, chaque genre étant une chaine de caractères de 50 caractères max à stocker en minuscule
Note                     | ranking        | requis    | note entre 0 et 10, pas de 0.1

Si les données envoyées par le client ne sont pas valides, une (ou des) erreurs doivent êtres retournées pour faciliter la correction des données invalides

**Indications** : Afin de réduire le périmètre du homework, les données seront stockées en mémoire (ex: dans un ListBuffer) plutôt que dans une base de données.

**Bonus** : Persister les données dans une base de donnée

#### US 1-2 : En tant qu'utilisateur, je veux lister les films préalablement enregistées, en filtrant par genre

Les films doivent être triés par années de production puis par ordre alphabétique sur le titre.<br>
Le filtre sur le genre est facultatif. S'il n'est pas défini, tous les films sont retournés.<br>
Il n'est pas nécessaire de paginer les résultats.

#### US 1-3 : En tant qu'utilisateur, je veux connaitre le nombre de films présents dans la médiathèque par année de production

## Epic 2 : Récupération de statistiques sur Github

Dans cette seconde partie de l'application (totalement décorrélée de la première), l'objectif sera de récupérer/aggréger/retourner des données provenant de l'api Github.

Notes : Toutes les données peuvent être récupérées sans authentification.

#### US 2-1 : En tant qu'utilisateur, je veux connaitre la liste des 10 plus importants committers d'un projet, ordonnés en fonction de leur participation, afin de leur envoyer un email de remerciement

La participation doit être déduite en fonction du nombre de commits réalisés par les contributeurs.<br>
Seuls les 100 premiers commits doivent être pris en compte.

#### US 2-2 : En tant qu'utilisateur, je veux connaître les 10 languages les plus utilisés d'un utilisateur Github pour les classifier dans notre base de données RH.

Effectuer le classement à partir des statistiques de langage fournies pour chaque dépôt de l'utilisateur désigné.

Note : Ne pas se contenter de récupérer le language majoritaire de chaque dépôt.

#### US 2-3 : En tant qu'utilisateur, je veux connaître le nombre d'issues ajoutées jour par jour pour suivre la santé du projet.

Calculer le nombre d'issues ajoutées sur un projet par jour, au cours des 30 derniers jours.

Ces données serviront au client à générer un graphique présentant en ordonnée le nombre d'issue, et en abscisse la projection des 30 derniers jours. La valeur 0 doit être indiquée pour les jours sans contributions.

**Bonus** : Utiliser l'API GraphQL de github à la place de l'api REST

## Epic 3 : Indicateurs temps réel

Cette épic a pour objectif la mise en place d'une communication temps réel entre un client et un serveur via une connexion web-socket.

#### US 3-1 : En tant qu'utilisateur, je veux m'abonner à un repository pour recevoir à un intervalle régulier le nombre d'étoiles (stars) de ce dépôt.

Exemple d'ordre communiqué à la websocket : ```{"action": "subscribe", "repository": "xx/yy", "interval": 10 }```

#### US 3-2 : En tant qu'utilisateur, je veux me désabonner à un repository pour ne plus recevoir les informations le concernant

Exemple d'ordre communiqué à la websocket : ```{"action": "unsubscribe", "repository": "xx/yy"}```

#### US 3-3 : En tant qu'utilisateur, je veux recevoir en temps réel le nombre de stars des projets auxquels je me suis abonné, selon l'intervalle de temps défini lors de la soubscription.

Exemple de scénario de communication avec la websocket :

```
00:00:00 >> {"action": "subscribe", "repository": "xx/yy", "interval": 10}
00:00:03 >> {"action": "subscribe", "repository": "yy/zz", "interval": 5}
00:00:08 << {"repository": "xx/zz", "stars": 80 }
00:00:10 << {"repository": "xx/yy", "stars": 3 }
00:00:16 << {"repository": "xx/zz", "stars": 81 }
```

# Contraintes techniques :

Nous n'imposons pas de choix concernant le language et le framework à utiliser. Choisissez un language/framework qui vous permettra de répondre efficacement à ces problématiques.
Seule l'utilisation d'un modèle d'acteur est imposée pour l'épic 3.

# Un dernier mot :

Il est essentiel dans cet exercice d'obtenir un résultat final présentable. Nous préférons voir une application ne répondant pas à toutes les user-stories, mais qui mise sur la qualité de code/stabilité. A ce titre, concevoir l'application de manière à ce qu'elle soit facilement testable et/ou testée sera grandement apprécié.

De même, utilisez les technologies avec lesquelles vous vous sentez confortable. Nous n'évaluerons pas la maitrise d'un framework particulier, nous porterons notre attention sur la manière dont vous résolvez les problèmes, sur la manière dont vous architecturez votre application ainsi que sur la qualité du code produit.

Evidemment, visez une solution simple et propre, nul besoin de mettre en place une usine à gaz.

N'hésitez pas à ajouter votre touche à ce projet pour nous démontrer vos compétences dans des domaines qui ne seraient pas couverts.
