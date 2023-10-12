# Programa de taller de algoritmos de planificación de tareas en Sistemas Operativos

Este repositorio contiene un programa que permite ingresar una serie de tareas con su tiempo de llegada y su tiempo de ejecución (CPUTIME). El programa ofrece la opción de aplicar diferentes algoritmos de planificación de CPU, como FCFS, SJN, SRT y Round Robin. Calcula el tiempo de finalización (Tend) de las tareas, la latencia de cada una (TturnAround) y el tiempo de espera (Twaiting), y determina cuál algoritmo es más eficiente para la serie de tareas ingresadas.

## Algoritmos de Planificación de CPU

- **FCFS (First-Come, First-Served)**: Este algoritmo programa las tareas según el orden en que llegan, dándole prioridad a la primera que llega.

- **SJN (Shortest Job Next)**: Este algoritmo programa las tareas según su tiempo de ejecución más corto, dándole prioridad a la tarea con el menor tiempo de ejecución restante.

- **SRT (Shortest Remaining Time)**: Similar a SJN, pero cambia la tarea en ejecución si llega una tarea con un tiempo de ejecución aún más corto.

- **Round Robin**: Asigna un pequeño intervalo de tiempo a cada tarea en secuencia, permitiendo que cada tarea se ejecute por un período definido.

## Cómo usar el programa

1. **Clonar el repositorio**: Clona este repositorio en tu máquina local.

2. **Compilar el programa**: Compila el programa utilizando tu entorno de desarrollo preferido.

3. **Ejecutar el programa**: Ejecuta el programa y sigue las instrucciones para ingresar la serie de tareas y elegir el algoritmo de planificación.

4. **Analizar los resultados**: Revisa los resultados que muestra el programa, incluyendo el tiempo de finalización, latencia y tiempo de espera para cada tarea, así como la evaluación del mejor algoritmo para la serie de tareas ingresadas.