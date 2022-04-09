#define _GNU_SOURCE

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <math.h>

void obradi_dogadjaj(int sig);
void obradi_sigterm(int sig);
void obradi_sigint(int sig);

int obrada_broj = 0;
int nije_kraj = 1;
FILE *fileStatus;
FILE *fileObrada;

int main()
{
    struct sigaction act;

    /* 1. maskiranje signala SIGUSR1 */
    act.sa_handler = obradi_dogadjaj;       /* kojom se funkcijom signal obrađuje */
    sigemptyset(&act.sa_mask);
    sigaddset(&act.sa_mask, SIGTERM);       /* blokirati i SIGTERM za vrijeme obrade */
    act.sa_flags = 0;                       /* naprednije mogućnosti preskočene */
    sigaction(SIGUSR1, &act, NULL);         /* maskiranje signala preko sučelja OS-a */

    /* 2. maskiranje signala SIGTERM */
    act.sa_handler = obradi_sigterm;
    sigemptyset(&act.sa_mask);
    sigaction(SIGTERM, &act, NULL);

    /* 3. maskiranje signala SIGINT */
    act.sa_handler = obradi_sigint;
    sigaction(SIGINT, &act, NULL);

    printf(">>> Program s PID=%ld krenuo s radom...\n", (long)getpid());

    char br[20];
    fileObrada = fopen("obrada.txt", "r+");
    fileStatus = fopen("status.txt", "r+");
    fscanf(fileStatus, "%d", &obrada_broj);
    fclose(fileStatus);
    if (obrada_broj == 0) {        
        while (fgets(br, sizeof(br) + 1, fileObrada) != NULL) {
            sscanf(br, "%d", &obrada_broj);
            obrada_broj = sqrt(obrada_broj);
        }
        printf(">>> Prilikom prethodnog izvođenja programa došlo je do prekida, obrada se nastavlja od broja : %d...\n", obrada_broj);
    } 

    fclose(fileObrada);

    /*zapiši 0 u status.txt na početak datoteke, prebrisat ću ono što je bilo -> w+*/
    fileStatus = fopen("status.txt", "w+");
    fprintf(fileStatus, "%d", 0);
    fclose(fileStatus);

    int x = 0;
    fileObrada = fopen("obrada.txt", "a+");
    /*fprintf(fileObrada, "\n");*/
    while (nije_kraj) {
        obrada_broj++;
        x = obrada_broj * obrada_broj;
        printf(">>>%d\n", obrada_broj);
        fprintf(fileObrada, "\n%d", x);       
        fflush(fileObrada);
        sleep(20);
    }

    fclose(fileObrada);
}

void obradi_dogadjaj(int sig)
{
    printf(">>> Trenutni broj u obradi je : %d \n", obrada_broj);
}

void obradi_sigterm(int sig)
{
    fileStatus = fopen("status.txt", "w+");
    fprintf(fileStatus, "%d", obrada_broj);
    fclose(fileStatus);
    printf(">>> Primio signal SIGTERM, pospremam broj | %d | prije izlaska iz programa\n", obrada_broj);
    exit(1);
}

void obradi_sigint(int sig)
{
    printf(">>> Primio signal SIGINT, prekidam rad programa...END\n");
    exit(1);
}