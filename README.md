# Introduction

Cette application a pour objectif d\'optimiser les tournées de livraison
en ville. Un livreur commence sa tournée depuis un entrepôt. Vous lui
fournirez les points de ramassage (pick-up) ainsi que les points de
livraison (delivery).

Notre application est dotée d\'un algorithme très puissant qui permet de
trouver l\'itinéraire le plus optimisé afin d\'accélérer les livraisons.
Elle a été spécialement développée pour les livraisons à vélo, dans le
but de promouvoir des villes plus durables.

Vous pourrez choisir quel livreur effectue la livraison, déterminer les
points de ramassage et de livraison, et planifier ses trajets de manière
optimale. Ensuite, vous pourrez sauvegarder les livraisons associé à un
livreur afin de la récupérer plus tard.

# Navigation de l\'Interface

Ci-dessous, vous pouvez voir l\'interface de la page d\'accueil. C\'est
l\'interface que vous verrez dès que vous serez connecté.

![](media/image3.png){width="6.267716535433071in"
height="3.3333333333333335in"}

Figure 1: L\'écran d\'accueil pour l\'utilisateur

Vous pouvez constater que l\'utilisation de l\'application est facile et
intuitive. Dès le début, vous aurez la possibilité de choisir un des
livreurs déjà présents ou d\'en ajouter un nouveau.

![](media/image6.png){width="6.267716535433071in"
height="3.3333333333333335in"}

Figure 2: Sélection du point de départ et arrivée du tour de livraisons

Une fois que vous avez choisi le livreur, vous passez à l\'étape du
choix de l\'itinéraire qu\'il devra parcourir. Si vous avez déjà
sauvegardé une route pour ce livreur, appuyez sur charger chemin. La
première étape consiste à définir l\'emplacement de votre entrepôt, qui
sera le point de départ et d\'arrivée de votre livreur. Un carré noir
apparaitra pour le représenter.

![](media/image1.png){width="6.267716535433071in"
height="3.3333333333333335in"}

Figure 3: Sélection des points de pick up et delivery pour chaque paquet

Lors de cette étape, vous allez choisir tous les points que le livreur
devra parcourir. Commencez par sélectionner un point de ramassage
(pick-up), suivi par le point où le paquet sera livré, toujours dans cet
ordre. Un texte vous indiquera tout le temps quel est le suivant. Les
points de ramassage et de livraison couplé sont représentés par un carré
et un cercle respectivement de même couleur.

![](media/image4.png){width="6.267716535433071in"
height="3.3333333333333335in"}

Figure 4: L'êntrepot (en noir) et les point de départ et arrivée choisi
pour chaque livrason

Après avoir ajouté quelques points, vous avez la possibilité de générer
un parcours. Il s\'agit de l\'itinéraire le plus optimal pour récupérer
et livrer tous les paquets, partant et arrivant au warehouse.

![](media/image2.png){width="6.267716535433071in"
height="3.3333333333333335in"}

Figure 5: Route à être parcouru pour le livreur

Finalement, vous avez la route tracée, le panneau à droite vous indique
l'ordre des points. Dans cette dernière fenêtre, vous avez la
possibilité de sauvegarder la route pour la revoir plus tard. Vous
pouvez également voir une estimation du temps nécessaire pour aller
d\'un point à l\'autre. Si après générer la route, vous souhaitez
ajouter encore des livraisons, il suffit de continuer à cliquer et
régénérer la route!

![](media/image5.png){width="6.267716535433071in"
height="1.4861111111111112in"}

Figure 6: Route incalculable

Si pour les points choisis il n'existe pas un chemin possible, en raison
de la contrainte de temps ou de l\'accessibilité des points, vous verrez
cette fenêtre. Appuyez sur ok et réessayez.

# Fonctionnalités Principales

**Choisir un livreur** : À partir d\'un bouton sur l\'écran principal,
vous pouvez choisir quel livreur sera responsable de réaliser le travail
de livraison que vous définirez.

**Ajouter un livreur** : En appuyant sur le bouton \"Ajouter un
livreur\", vous pouvez ajouter un livreur de confiance pour ensuite lui
assigner un travail.

**Ajouter l\'entrepôt** : Sur la carte, la première intersection que
vous sélectionnez sera définie comme l\'entrepôt. C\'est le point de
départ et d\'arrivée du livreur.

**Ajouter les points de ramassage et de livraison** : Comme pour
l\'entrepôt, il faut choisir les intersections en cliquant sur la carte.
D\'abord le point de ramassage (pick-up), puis le point de livraison
pour chaque paquet.

**Générer la route** : Une fois tous les points de ramassage, de
livraison et l\'entrepôt sélectionnés, vous pouvez appuyer sur le bouton
\"Générer\" pour voir la route tracée sur la carte.

**Sauvegarder le chemin**: Cette fonctionnalité permet de sauvegarder le
chemin pour plus tard, pour ce livreur en particulier. Il suffit
d\'appuyer sur le bouton \"Sauvegarder chemin\" pour qu'elle soit
persistée

**Charger un chemin**: Cette fonctionnalité permet de charger le chemin
sauvegardé précédemment pour le livreur sélectionné. Appuyez sur
"Charger chemin"

