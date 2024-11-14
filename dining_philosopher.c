#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>

#define NUM_PHILOSOPHERS 5

pthread_mutex_t forks[NUM_PHILOSOPHERS];

// Function representing the philosopher's actions
void* philosopher(void* num) {
    int id = *(int*)num;
    int count = 0;
    while (count<3) {
        // Think
        printf("Philosopher %d is thinking.\n", id);
        //usleep(rand() % 1000);

        // Pick up left fork
        pthread_mutex_lock(&forks[id]);
        printf("Philosopher %d picked up left fork.\n", id);

        // Pick up right fork
        pthread_mutex_lock(&forks[(id + 1) % NUM_PHILOSOPHERS]);
        printf("Philosopher %d picked up right fork.\n", id);

        // Eat
        printf("Philosopher %d is eating.\n", id);
        //usleep(rand() % 1000);

        // Put down right fork
        pthread_mutex_unlock(&forks[(id + 1) % NUM_PHILOSOPHERS]);
        printf("Philosopher %d put down right fork.\n", id);

        // Put down left fork
        pthread_mutex_unlock(&forks[id]);
        printf("Philosopher %d put down left fork.\n", id);
        count++;
    }

    return NULL;
}

int main() {
    pthread_t philosophers[NUM_PHILOSOPHERS];
    int ids[NUM_PHILOSOPHERS];

    // Initialize mutexes
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        pthread_mutex_init(&forks[i], NULL);
        ids[i] = i; // assign philosopher id
    }

    // Create philosopher threads
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        pthread_create(&philosophers[i], NULL, philosopher, &ids[i]);
    }

    // Join philosopher threads (this will never happen due to the infinite loop)
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        pthread_join(philosophers[i], NULL);
    }

    // Destroy mutexes (not reached because of infinite loop)
    for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
        pthread_mutex_destroy(&forks[i]);
    }

    return 0;
}
