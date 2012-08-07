#include <stdio.h>

void main () {

 int a,b,x,y;
 a = 4;
 b = 7;
 printf("À vous de jouer\n");
 scanf("%d",&x); 
 scanf("%d",&y);
 if (x == a && y == b) {
  printf("Coulé\n");}
 else {
  if (x == a || y == b) {
   printf("En vue\n");}
  else {
   printf("À l'eau\n");}}}




