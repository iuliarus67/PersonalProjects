#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/wait.h>
#include "a2_helper.h"
#include <pthread.h>
#include <semaphore.h>

#define maxNrInArea 4
sem_t mutex;
sem_t mutex6;
sem_t mutex43;
sem_t mutex94;
pthread_cond_t t2Begin = PTHREAD_COND_INITIALIZER;
pthread_cond_t t5End = PTHREAD_COND_INITIALIZER;

pthread_mutex_t mutex1 = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t mutex2 = PTHREAD_MUTEX_INITIALIZER;


void * P2Threads(void * arg) {
    
   long th_id = (long)arg;
   
   sem_wait(&mutex);
   
   info(BEGIN, 2, th_id);
   info(END, 2, th_id);
   
   sem_post(&mutex);
   
   return NULL;
}

void * P9Threads(void * arg) {

    long th_id = (long)arg;
    if(th_id == 5) {
        pthread_mutex_lock(&mutex1);
        pthread_cond_wait(&t2Begin, &mutex1);
        info(BEGIN, 9, th_id);
        info(END, 9, th_id);
        pthread_cond_signal(&t5End);
        pthread_mutex_unlock(&mutex1);
    } 
    else {
        if (th_id == 2) {
            info(BEGIN, 9, th_id);
            pthread_cond_signal(&t2Begin);
            pthread_mutex_lock(&mutex2);
            pthread_cond_wait(&t5End, &mutex2);
            info(END, 9, th_id);
            pthread_mutex_unlock(&mutex2);
        } else {
           if(th_id == 4) {
                sem_wait(&mutex43);
                info(BEGIN, 9, th_id);              
                info(END, 9, th_id);
                sem_post(&mutex94);
                
           } else {
                info(BEGIN, 9, th_id);
                info(END, 9, th_id);
           }
        }
    }
    
    return NULL;
}

void * P4Threads(void * arg) {

    long th_id = (long)arg;
    if(th_id == 2) {
        sem_wait(&mutex94);
        info(BEGIN, 4, th_id);
        info(END, 4, th_id);
        return NULL;
    }
    info(BEGIN, 4, th_id);
    info(END, 4, th_id);
    if(th_id == 3) {
        sem_post(&mutex43);
    }
    return NULL;
}
int main(){

    init();

    info(BEGIN, 1, 0);
    sem_init(&mutex,0, maxNrInArea);
    sem_init(&mutex43,0, 1);
    sem_init(&mutex94,0, 1);

    int P2, P3, P4, P5, P6, P7, P8, P9;
    if ((P2 = fork()) < 0) {
        printf("ERROR creating process P2\n");
        goto cleanup_and_exit;
    }
        switch (P2) {
            case 0: 
                info(BEGIN, 2, 0);
                pthread_t threads2[48] ;
                for (long i = 0; i < 48; i++) {
                        long j = i+1;    
                        pthread_create(&threads2[i], NULL,P2Threads,(void *)j);
                    }
                for (int i = 0; i < 48; i++) {
                                        
                    pthread_join(threads2[i], NULL);
                                    
                }
        //***********************P7*************************//
                if((P7 = fork()) < 0) {
                    printf("ERROR creating process P7\n");
                    goto cleanup_and_exit;
                }

                switch(P7) {
                    case 0: 
                        info(BEGIN,7,0);
                        goto cleanup_and_exit;
                        break;

                    default :
                        waitpid(P7, NULL, 0);
                        info(END, 7, 0);
                        break; 
                }
        //***********************P4*********************//
                if((P4 = fork()) < 0) {
                    printf("ERROR creating process P4\n");
                    goto cleanup_and_exit;
                }

                switch(P4) {
                    case 0:
                        info(BEGIN,4,0);
                        pthread_t threads2[6] ;
                        for (long i = 0; i < 6; i++) {
                            long j = i+1;    
                            pthread_create(&threads2[i], NULL,P4Threads,(void *)j);
                        }
                        for (int i = 0; i < 6; i++) {                   
                            pthread_join(threads2[i], NULL);
                        }
                        goto cleanup_and_exit;
                        break;

                    default :
                        waitpid(P4, NULL, 0);
                        info(END, 4, 0);
                        break; 
                }
        //*********************P3*********************//
                if((P3 = fork()) < 0) {
                    printf("ERROR creating process P3\n");
                    goto cleanup_and_exit;
                }

                switch(P3) {
                    case 0:
                        info(BEGIN,3,0);
                //*************************P5*******************//
                        if((P5 = fork()) < 0) {
                            printf("ERROR creating process P5\n");
                            goto cleanup_and_exit;
                        }
                        switch(P5) {
                            case 0: 
                                info(BEGIN,5,0);

                                //*****************P8******************//
                                if((P8 = fork()) < 0) {
                                    printf("ERROR creating process P8\n");
                                    goto cleanup_and_exit;
                                }
                                switch(P8) {
                                    case 0:
                                    info(BEGIN,8,0);
                                    goto cleanup_and_exit;
                                    break;

                                    default: 
                                    waitpid(P8, NULL, 0);
                                    info(END, 8, 0);
                                    break; 
                                }
                                //**********************P9***********************//
                                if((P9 = fork()) < 0) {
                                    printf("ERROR creating  P9\n");
                                    goto cleanup_and_exit;
                                }
                                switch(P9) {
                                    case 0:
                                    info(BEGIN,9,0);
                                    pthread_t threads[6] ;
                                    for (long i = 0; i < 5; i++) {
                                         long j = i+1;    
                                         pthread_create(&threads[i], NULL,P9Threads,(void *)j);
                                    }
                                    for (int i = 0; i < 6; i++) {
                                        
                                        pthread_join(threads[i], NULL);
                                    
                                    }
                                    goto cleanup_and_exit;
                                    break;

                                    default: 
                                    waitpid(P9, NULL, 0);
                                    info(END, 9, 0);
                                    break; 
                                }
                                goto cleanup_and_exit;
                                break;

                            default :
                                waitpid(P5, NULL, 0);
                                info(END, 5, 0);
                                break; 
                        }

                        //*************************P6*******************//
                        if((P6 = fork()) < 0) {
                            printf("ERROR creating process P6\n");
                            goto cleanup_and_exit;
                        }
                        switch(P6) {
                            case 0: 
                                info(BEGIN,6,0);
                                goto cleanup_and_exit;
                                break;

                            default :
                                waitpid(P6, NULL, 0);
                                info(END, 6, 0);
                                break;
                        }
                        goto cleanup_and_exit;
                        break;

                    default :
                        waitpid(P3, NULL, 0);
                        info(END, 3, 0);
                        break; 
                }
                goto cleanup_and_exit;
                break; 

    default: 
        waitpid(P2, NULL, 0);
        info(END, 2, 0);
        break;
    }

    info(END, 1, 0);
    cleanup_and_exit:
    {
        exit(1);
    }
    return 0;
}
