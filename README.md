# Generador de Laberintos
Se trata de un programa que genera un laberinto de forma procedural y permite jugarlo.

Para empezar, el usuario indica el tamaño, así como la verticalidad y horizontalidad que se definen como la tendencia de los caminos del laberinto a ir en vertical u horizontal.

El programa dará la opcion al jugador de guardar el laberinto en un fichero de texto y de elegir la Seed del laberinto, un numero que determina la forma en la que se generara el laberinto.

Una vez establecidos todos estos parametros, el usuario puede elegir si desea ver el proceso de generacion del laberinto paso a paso, automaticamente o ver el laberinto ya terminado.

Paso a paso: el programa esperará a que el usuario pulse una tecla después de cada paso del proceso.
Automático: el programa irá dibujando cada paso cada cierto tiempo.
			El laberinto se generá más rápido mientras más grande sea, de tal forma que sea del tamaño que sea se
			generará más o menos en el mismo tiempo(unos 20s).
Ya terminado: el programa no mostrará el proceso de creación del laberinto, mostrará el laberinto ya terminado.

Por ultimo, el usuario puede jugar a resolver el laberinto con distintos modos de juego:
  Normal: El usuario resuelve el laberinto sin ningun objetivo extra. Al final del juego se le muestra el tiempo en el que lo ha completado.
  Contrarreloj: El usuario debe completar el laberinto en un tiempo inferior al que diga el programa.
  Multijugador: Dos jugadores se turnaran para irse moviendo por el laberinto y llegar a sus respectivos objetivos.
  Contra la maquina: El usuario jugara contra la maquina como si de otro jugador se tratara.

La clase Arquitecto es la encargada de construir el laberinto, que está formado por distintas celdas que guardarán 
la información de los muros Norte, Este, Sur y Oeste, que pueden estar o no estar. Ésto último lo decidirá el Arquitecto
para así ir formando los caminos.
