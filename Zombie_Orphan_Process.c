#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

int main() {
    int array[] = {1,2,3,4};
    int n = sizeof(array) / sizeof(array[0]);
    pid_t pid;

    pid = fork(); // Create a new process

    if (pid > 0) {
        // Parent process
        int even_sum = 0;

        // Calculate sum of even numbers in parent process
        for (int i = 0; i < n; i++) {
            if (array[i] % 2 == 0) {
                even_sum += array[i];
            }
        }

        printf("Parent process (PID: %d): Sum of even numbers = %d\n", getpid(), even_sum);

        // Creating a zombie process by not calling wait()
        sleep(2); // Give child process time to exit, creating a zombie

        // Wait for child to clean up zombie state after delay
        wait(NULL);
        printf("Parent process: Child has been reaped, no longer a zombie.\n");

    } else if (pid == 0) {
        // Child process
        int odd_sum = 0;

        // Calculate sum of odd numbers in child process
        for (int i = 0; i < n; i++) {
            if (array[i] % 2 != 0) {
                odd_sum += array[i];
            }
        }

        printf("Child process (PID: %d): Sum of odd numbers = %d\n", getpid(), odd_sum);

        // Creating an orphan process by exiting the parent
        sleep(3); // Keep child alive long enough for parent to terminate first
        printf("Child process: Now an orphan process, adopted by init.\n");

        exit(0); // Child process exits here
    } else {
        // Fork failed
        perror("Fork failed");
        exit(1);
    }

    return 0;
}
