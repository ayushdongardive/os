#include <stdio.h>
#include <pthread.h>

#define n 3

int a[n][n], b[n][n], c[n][n];

void* add_matrix(void* args) {
    int thread_id = *(int*)args;

    for (int i = thread_id; i < n; i += 2) {
        for (int j = 0; j < n; j++) {  // Corrected condition
            c[i][j] = a[i][j] + b[i][j];
        }
    }

    return NULL;
}

void* sub_matrix(void* args) {
    int thread_id = *(int*)args;

    for (int i = thread_id; i < n; i += 2) {
        for (int j = 0; j < n; j++) {
            c[i][j] = a[i][j] - b[i][j];
        }
    }
    return NULL;
}

void* mul_matrix(void* args) {
    int thread_id = *(int*)args;
    for (int i = thread_id; i < n; i += 2) {
        for (int j = 0; j < n; j++) {
            c[i][j] = 0;
            for (int k = 0; k < n; k++) {
                c[i][j] += a[i][k] * b[k][j];
            }
        }
    }
    return NULL;
}

void print(int c[n][n]) {
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d ", c[i][j]);  // Added space for readability
        }
        printf("\n");  // Corrected newline
    }
    printf("\n");
}

int main() {
    // Initializing matrices
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            a[i][j] = i + j;
            b[i][j] = i - j;
        }
    }

    // Print matrix A
    printf("Matrix A:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d ", a[i][j]);
        }
        printf("\n");
    }
    printf("\n");

    // Print matrix B
    printf("Matrix B:\n");
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            printf("%d ", b[i][j]);
        }
        printf("\n");
    }
    printf("\n");

    pthread_t threads[2];
    int thread_id[2] = {0, 1};

    // Addition
    for (int i = 0; i < 2; i++) {
        pthread_create(&threads[i], NULL, add_matrix, &thread_id[i]);
    }
    for (int i = 0; i < 2; i++) {
        pthread_join(threads[i], NULL);
    }
    printf("Addition:\n");
    print(c);

    // Subtraction
    for (int i = 0; i < 2; i++) {
        pthread_create(&threads[i], NULL, sub_matrix, &thread_id[i]);
    }
    for (int i = 0; i < 2; i++) {
        pthread_join(threads[i], NULL);
    }
    printf("Subtraction:\n");
    print(c);

    // Multiplication
    for (int i = 0; i < 2; i++) {
        pthread_create(&threads[i], NULL, mul_matrix, &thread_id[i]);
    }
    for (int i = 0; i < 2; i++) {
        pthread_join(threads[i], NULL);
    }
    printf("Multiplication:\n");
    print(c);

    return 0;
}
