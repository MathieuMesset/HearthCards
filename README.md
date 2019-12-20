# Hearthstone Artbook

## Présentation

Projet de programmation mobile fait dans le cadre de la quatrième année au sein de l'ESIEA.
Celui-ci démontrant l'utilisation d'une architecture MVVM dans une application Android codé en Java.

Cette application affiche une liste des cartes du jeu *Hearthstone* utilisant l'API hearthstonejson.

### Consignes respectées:

 -   Clean Architecture & **MVVM**
 - Appels à une **API REST**
 - **2 Activités** avec **animations** de transition
  - **2 Fragments**
 - Affichage d'une liste dans un **RecyclerView**
 - Affichage du **détail de l'item** lors du clique
 - **GitFlow**
 - Stockage des données dans le **cache**
 - Autres:
	 - **AppBar** logé dans un **CoordinatorLayout**, permettant de la dissimuler lors du *scroll*
	 - **SearchView** filtrant par *nom de carte* 
	 - **TabLayout** permettant de naviguer entre les différents fragments
	 - **NavigationDrawer** offrant la possibilité de filtrer par *classe  d'appartenance*
	 - Retour à la liste depuis le détail d'une carte en **balayant** l'écran vers la droite
	 - Affichage de **GoogleMaps**
	
## Fonctionnalités 

### Ecran d'accueil

 - **Premier écran** contenant la **SearchView**, le **TabLayout** et le **ViewPager** contenant par défaut le premier **fragment : Cards**
	 -  affichant la liste des cartes via un **RecyclerView**, dissimulant lors du scroll la **ToolBar** , pour chaque carte on a :
		 - Son **artwork** 
		 - Son **nom**
		 - L'icone de sa **classe** suivi de son nom ( en Anglais )
		 - Son **texte descriptif**

    -  Le second **fragment : Maps** donne accès à **GoogleMaps** et dissimule l'interface inutile (**ToolBar** est collapsed et **NavigationDrawer** LOCKED_CLOSED)

La transition entre les fragments peut-être fait en cliquant sur le TabLayout ou en swipant de droite à gauche (du bord de l'écran sur le fragment Maps).

![Scrollable](https://media.giphy.com/media/gL3D9hb9mO5Z8xYFbq/giphy.gif)	  ![Transition entre Cards et Maps](https://media.giphy.com/media/eiMZ0DrmVc28xxLu4W/giphy.gif)
          
          
 ### Ecran de détail

 - **Second écran** contenant le détail de la carte, on y retrouve ainsi :
	 - Comme dans la liste son artwork, son nom, sa classe et son texte descriptif
	 - **Le nom de l'artiste** ayant dessiné l'artwork
	 - Puis logé dans un  **NestedScrollView**  la **carte** en elle même ( affichant ainsi ses dégâts d'attaques , ses points de vie, sa rareté, son coût, son effet, etc...)
	 - Une **animation custom** a été fait pour la transition vers l'écran de détail
	 - Une **gestuelle** permet de fermer l'écran de détail ( swipe vers la droite)
   

![](https://media.giphy.com/media/J6V29GDFaptQ1mEMFV/giphy.gif)




### Filtrage de la liste à l'aide d'une SearchView

 - **Filtre** permettant de rechercher une carte contenant des **termes spécifique** puis, couplé au **NavigationDrawer** permet de spécifier **une classe** dont on veut voir les cartes.
 Dans le NavigationDrawer un clique sur une classe non sélectionné la sélectionné, un clique sur une classe déjà sélectionné la désélectionne.

![enter image description here](https://media.giphy.com/media/WQYr5iRaHUkSSGRNE5/giphy.gif) 

La recherche d'une carte peut donc être la suivante :
- Choix d'une *classe* dans le **NavigationDrawer**
- Le **RecyclerView** affiche toutes les cartes de la *classe*
- La recherche dans le **SearchView** permet de filtrer toutes les cartes ne contenant pas les *termes recherchés*
![](https://image.noelshack.com/fichiers/2019/51/5/1576856766-output-onlinepngtools-1.png) ![](https://image.noelshack.com/fichiers/2019/51/5/1576856848-output-onlinepngtools-2.png) ![](https://image.noelshack.com/fichiers/2019/51/5/1576856893-output-onlinepngtools-3.png)
