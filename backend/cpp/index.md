# Coding Challenge Tabmo C++

_Time to get things done!_

L'objectif du coding challenge est de développer une application répondant aux besoins exprimés par le responsable fonctionnel.
L'application prendra la forme d'un serveur HTTP simulant à échelle réduite le mécanisme d'achat de publicité programmatique.

N'hésitez pas à contacter la personne jouant le rôle de responsable fonctionnel (julien.lafont@tabmo.io) si vous avez la moindre question ou incertitude concernant les spécifications décrites ci-dessous.

## Préconisations technologiques

Le coeur du challenge réside dans les mécanismes mis en place pour sélectionner et répondre aux propositions d'emplacements publicitaire avec la publicité la plus adaptée.

Afin d'avoir un serveur HTTP rapidement opérationel, nous vous conseillons de vous baser sur un framework HTTP haut-niveau tel que [Drogon](https://github.com/an-tao/drogon), [Restbed](https://github.com/Corvusoft/restbed)
ou toute autre solution équivalente. Vous pouvez également intégrer au projet n'importe quelle librairie que vous jugerez adaptée.

Une attention toute particulière devra être portée sur la **fiabilité** et la **performance** des algorithmes développés.

## Rendu et Code-Review

Le projet devra être hébergé sur un dépôt github ou gitlab public, que vous nous partagerez une fois le challenge terminé.

Après une première analyse du projet par l'équipe, vous serez convié à participer à une revue de code avec des membres
de l'équipe TabMo.

Durant cette revue, vous serez emmené à effectuer une démonstration de votre application, une présentation des choix techniques et architecturaux faits,
ainsi qu'une revue des "limites" actuelles de l'application, ce que vous auriez fait différement sans contrainte de temps.

## Spécifications fonctionnelles

### Cadre Général

L'application reprendra dans les grandes lignes le principe de la publicité programmatique.

- Des requêtes HTTP seront envoyées au serveur,
  correspondant à la mise sur le marché d'un emplacement publicitaire ayant des caractéristiques précises (taille de l'emplacement, application d'origine, etc).

- Le serveur aura connaissance d'une liste de campagnes publicitaires préalablement configurées qui seront explorés à chaque requête pour déterminer
  les campagnes compatibles et sélectionner la campagne qui sera proposée pour cet emplacement publicitaire.

Dans le vocabulaire RTB, la requête proposant un emplacement publicitaire est appellée `bid request`, et la réponse envoyée par le serveur est appellée `bid response`.

Plusieurs règles devront être vérifées pour déterminer si une campagne publicitaire est compatible avec une requête reçu.
Il est possible qu'aucune campagne ne soit compatible. Il est également possible que plusieurs campagnes soient compatibles, dans ce cas une sélection sera faite sur des critères qui seront définis ultérieurement pour ne sélectionner qu'une seule campagne finale.

### Chargement des campagnes publicitaires

Un serveur RTB réel supportera des endpoints HTTP permettant de configurer dynamiquement les campagnes publicités disponible à un instant T
sur le serveur d'enchère.

Pour ce challenge, une solution plus simple sera mise en place : la liste des campagnes sera lue depuis un fichier au démarage de l'application et ne sera plus jamais modifiée.
Il suffira donc de la lire/parser au chargement de l'application, et de garder la liste des campagnes en mémoire.

Le format d'une campagne publicitaire suit le schéma JSON suivant :

#### Représentation d'une campagne

```
{
    "id": "c9c71e5a-4c24-493a-a7a0-436021af8252",
    "budget": "100",
    "bidPrice": "0.12",
    "width": 100,
    "height": 300,
    "responsive": false,
    "filters": {
        "include": {
            "language": ["EN", "FR"],
            "application": ["Puzzle", "Programme TV"]
        },
        "exclude": {
            "ifa": ["553144ea-63e1-49ec-88b0-9d5a8f47d5a0", "23c42e63-7c3f-4510-865f-b26353fb7583"]
        }
    },
    "url": "http://www.myAdUrl.com/FgTvfvoP"
}

```

| Champ                      | Champ json        | requis ? | Commentaire                                                                                                                                                                                 |
| -------------------------- | ----------------- | -------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Identifiant de la campagne | `id`              | oui      | Identifiant de la campagne (UUID)                                                                                                                                                           |
| Budget disponible          | `budget`          | oui      | Budget disponible sur la publicité, en $. Min: 1$, Max: 1000\$. Précision: 2 décimales                                                                                                      |
| Montant de l'enchère       | `bidPrice`        | oui      | Montant de l'enchère qui sera envoyé pour acheter l'emplacement publicitaire. Min: 0.01$, Max: 10$. Précision: 4 décimales                                                                  |
| Largeur de la creative     | `width`           | oui      | Largeur de la publicité                                                                                                                                                                     |
| Longueur de la creative    | `height`          | oui      | Hauteur de la publicité                                                                                                                                                                     |
| Publicité responsive ?     | `responsive`      | non      | Indique si la créative est responsive, c'est à dire si elle peut être diffusée sur un emplacement plus grand ou plus petit que ça taille originale, à condition que le ratio soit respecté. |
| Filtres                    | `filters`         | non      | Filtres en place sur la campagne                                                                                                                                                            |
| Filtres d'inclusions       | `filters.include` | non      | Filtres pour lesquels le matching avec l'emplacement positif doit être POSITIF. Le résultat de CHAQUE filtre doit être POSITIF pour que la campagne soit compatible.                        |
| Filtres d'exclusions       | `filters.exclude` | non      | Filtres pour lesquels le matching avec l'emplacement positif doit être NEGATIF. Le résultat de TOUS les filtres doit être NEGATIF pour que la campagne soit compatible.                     |
| URL de la publicité        | `url`             | oui      | URL de la publicité à diffuser                                                                                                                                                              |

#### Liste des filtres

Un filtre pourra être défini soit en inclusion, soit en exclusion, jamais les deux. Un filtre peut aussi ne pas être défini.
Les filtres sont obligatoires : s'il n'est pas possible de le vérifier en raison de l'absence d'une donnée,
la campagne est immédiatement déclarée comme non compatible.

`language`

La campagne peut contenir un filtre sur le ou les langages acceptés. Les langages sont exprimés sous forme de code ISO-3166-2.

Un filtre langage peut contenir entre 1 et 10 langages à inclure/exclure.

La vérification sera faite avec le champ `device.lang` de la bid-request. La comparaison doit être effectuée de manière insensible à la casse.

`application`

La campagne peut contenir un filtre sur le ou les applications sur laquelle elle peut être diffusés.

Un filtre application peut contenir entre 1 et 100 applications à inclure/exclure.

La vérification sera faite avec le champ `app.name` de la bid-request. La comparaison doit être effectuée sur un critère de type "contient".
Exemple: un filtre "Puzzle" acceptera "Puzzle 2020", "Original Puzzle" ou "Bests Puzzle 2020", mais refusera "puzzle" ou "My Puzz-le".

`ifa`

L'IFA correspond à l'identifiant publicitaire d'un smartphone. Il peut être envoyée ou non en fonction des configurations de confidentialité du terminal.

Un filtre ifa peut contenir entre 1 et 100000 identifiants à inclure/exclure.

La vérification sera faite avec le champ `device.ifa` de la bid-request. La comparaison doit être effectuée de manière insensible à la casse.

### Réception d'une proposition d'emplacement publicitaire (bid-request)

Les bid-requests seront envoyées via une requête HTTP `POST` sur l'endpoint `/rtb` avec un payload JSON.

```
{
    "id": "488d874f-927a-454b-aedc-2288809f6009",
    "device": {
        "os": "android",
        "ip": "12.126.73.143",
        "ifa": "eb2b2dfc-6565-37f4-9341-c76bd6c6c555",
        "make": "Samsung",
        "model": "sm-g973f",
        "lang": "EN",
        "w": 320,
        "h": 250
    },
    "app": {
        "name": "Happy Color™ – Color by Number",
        "bundle": "com.pixel.art.coloring.color.number"
    },
    "bid-floor": "0.01"
}
```

| Champ                        | Champ json     | requis ? | Commentaire                                                  |
| ---------------------------- | -------------- | -------- | ------------------------------------------------------------ |
| Identifiant de l'emplacement | `id`           | oui      | Identifiant de l'emplacement publicitaire (UUID)             |
| Système d'exploitation       | `device.os`    | non      | Type d'OS du terminal                                        |
| Adresse IP                   | `device.ip`    | non      | Adresse IP du terminal                                       |
| IFA                          | `device.ifa`   | non      | Identifiant publicitaire du terminal                         |
| Constructeur                 | `device.make`  | non      | Nom du constructeur du terminal                              |
| Modèle                       | `device.model` | non      | Code du modèle du terminal                                   |
| Langage                      | `device.lag`   | oui      | Langue par défaut du terminal                                |
| Largeur                      | `device.w`     | oui      | Largeur de l'emplacement publicitaire                        |
| Hauteur                      | `device.h`     | oui      | Hauteur de l'emplacement publicitaire                        |
| Nom de l'application         | `app.name`     | non      | Nom de l'application contenant l'emplacement publicitaire    |
| Bundle de l'application      | `app.bundle`   | non      | Bundle de l'application contenant l'emplacement publicitaire |
| Prix d'achat minimum         | `bid-floor`    | oui      | Montant minimum auquel sera vendu l'emplacement publicitaire |

### Règles de sélection

Les requêtes reçues par le système d'enchère seront évaluées pour déterminer les campagnes compatible avec cet emplacement, et dans un second temps
une sélection sera faite parmis ces campagnes compatibles pour sélectionner la campagne qui répondra à l'enchère.

#### Recherche des campagnes compatibles

Une campagne sera considérée comme compatible avec un emplacement publicitaire si :

- La taille de l'emplacement publicitaire est compatible avec la taille de la publicité
- Le budget restant de la campagne est supérieur ou égal au montant de l'enchère (`bidPrice`) à envoyer
- L'emplacement publicitaire est compatible avec les filtres de la campagne

### Sélection de la campagne

Si plusieurs campagnes sont compatible avec un emplacement publicitaire, un tirage aléatoire sera effectué parmis elle.

**Bonus :** Pondérer le tirage aléatoire par un poids correspondant au budget disponible de la campagne (de telle manière qu'une campagne ayant un budget élevé aient plus de chance d'être diffusée).

### Génération de la réponse

Une fois le mécanisme de sélection terminé, si aucune campagne n'a été sélectionnée, le serveur renverra une réponse `No Content`.

Si une campagne a été sélectionnée, le serveur renverra une réponse `Ok` avec les informations suivantes :

```
{
    "auctionId": "488d874f-927a-454b-aedc-2288809f6009",
    "campaignId": "c9c71e5a-4c24-493a-a7a0-436021af8252",
    "url": "http://www.myAdUrl.com/FgTvfvoP",
    "price": "0.12"
}
```

Le montant disponbible sur la campagne doit être décrémenté par le montant de l'enchère en même temps que l'enchère est émise.

# Un dernier mot :

Il est essentiel dans cet exercice d'obtenir un résultat final fonctionnel. Nous préférons voir une application ne répondant pas à tous les pré-requis (filtre manquant, etc), mais qui mise sur la qualité de code/stabilité. A ce titre, concevoir l'application de manière à ce qu'elle soit facilement testable et/ou testée sera grandement apprécié.

De même, utilisez les technologies avec lesquelles vous vous sentez confortable. Nous n'évaluerons pas la maitrise d'un framework particulier, nous porterons notre attention sur la manière dont vous résolvez les problèmes, sur la manière dont vous architecturez votre application ainsi que sur la qualité du code produit.

Evidemment, visez une solution simple et propre, nul besoin de mettre en place une usine à gaz.
