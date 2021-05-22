# Système de traitement automatisé des excès de vitesse

## Description du programme

Vous devez détecter les véhicules en excès de vitesse en calculant leurs vitesses moyennes entre deux instantanées photos prises sur une route.
Votre programme doit déterminer les véhicules en infraction et retourner une liste de procés verbaux contenant l'immatrimaculation du véhicule, la borne kilométrique à laquelle l'infraction a été détectée, et la vitesse moyenne calculée (arrondie à l'entier supérieur).

Les données d'entrées contiennent tous les enregistrements horodatés pour chaque plaque d'immatriculation détectée. Tous les enregistrements sont groupés par plaque d'immatriculation et les distances des appareils photo sont triées par ordre croissant à l'intérieur de ce groupe.

## Résolution

Créer un programme (exécutable manuellement ou via un jeu de tests automatisés) dans `src/main/scala`.

## Jeux d'essais


**input 1**
```
Vitesse max : 100 km/h
SKRD94,175,1447411532
SKRD94,275,1447415132
ZBZJ42,75,1447418732
ZBZJ42,175,1447422333
ZBZJ42,275,1447425932
```

**output 1**
```
ZBZJ42,275,101
```

**input2**
```
Vitesse max : 60 km/h
RSWJ98,152,1447416000
RSWJ98,199,1447419600
RSWJ98,247,1447423200
RSWJ98,295,1447426800
```

**output 2**
```
```

**input3**
```
Vitesse max : 90 km/h
PAZD54,50,1447413071
PAZD54,150,1447416671
PAZD54,250,1447420211
DJSS87,50,1447408801
DJSS87,150,1447417501
DJSS87,250,1447421101
LSKD97,50,1447417436
LSKD97,150,1447424636
LSKD97,250,1447431836
```

**output3**
```
PAZD54,150,100
PAZD54,250,102
DJSS87,250,100
```
