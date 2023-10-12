import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

class Tarea {
    private int id;
    private int cputime;
    private int arrivalTime;
    private int remainingTime;

    public Tarea(int id, int cputime, int arrivalTime) {
        this.id = id;
        this.cputime = cputime;
        this.arrivalTime = arrivalTime;
        this.remainingTime = cputime;
    }

    public int getId() {
        return id;
    }

    public int getCputime() {
        return cputime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }
}

public class Algoritmos {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leer el número de tareas que se van a realizar
        System.out.print("Ingrese el número de tareas: ");
        int numTareas = scanner.nextInt();

        List<Tarea> tareas = new ArrayList<>();
        for (int i = 0; i < numTareas; i++) {
            // Leer detalles de cada tarea
            System.out.print("Ingrese el CPUTIME de la tarea " + (i + 1) + ": ");
            int cputime = scanner.nextInt();
            System.out.print("Ingrese el tiempo de llegada de la tarea " + (i + 1) + ": ");
            int arrivalTime = scanner.nextInt();
            tareas.add(new Tarea(i + 1, cputime, arrivalTime));
        }

        System.out.println("Seleccione el algoritmo:");
        System.out.println("1. FCFS");
        System.out.println("2. SJN");
        System.out.println("3. SRT");
        System.out.println("4. Round Robin");
        System.out.print("Elija una opción: ");
        int choice = scanner.nextInt();

        // Llamar al algoritmo según la elección
        switch (choice) {
            case 1:
                fcfs(tareas);
                break;
            case 2:
                sjn(tareas);
                break;
            case 3:
                srt(tareas);
                break;
            case 4:
                System.out.print("Ingrese el quantum para Round Robin: ");
                int quantum = scanner.nextInt();
                roundRobin(tareas, quantum);
                break;
            default:
                System.out.println("Opción no válida.");
        }

        scanner.close();
    }

    private static void fcfs(List<Tarea> tareas) {
        // Ordenar tareas por tiempo de llegada
        Collections.sort(tareas, (a, b) -> a.getArrivalTime() - b.getArrivalTime());
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int n = tareas.size();

        for (Tarea tarea : tareas) {
            int completionTime = Math.max(currentTime, tarea.getArrivalTime()) + tarea.getCputime();
            int turnaroundTime = completionTime - tarea.getArrivalTime();
            int waitingTime = turnaroundTime - tarea.getCputime();

            System.out.println("Tarea " + tarea.getId() + ": Tiempo de finalización = " + completionTime
                    + ", Latencia = " + turnaroundTime + ", Tiempo de espera = " + waitingTime);

            totalTurnaroundTime += turnaroundTime;
            totalWaitingTime += waitingTime;
            currentTime = completionTime;
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / n;
        double averageWaitingTime = (double) totalWaitingTime / n;

        System.out.println("Tiempo de finalización promedio: " + averageTurnaroundTime);
        System.out.println("Tiempo de espera promedio: " + averageWaitingTime);
    }

    private static void sjn(List<Tarea> tareas) {
        // Ordenar tareas por tiempo de llegada
        Collections.sort(tareas, (a, b) -> a.getArrivalTime() - b.getArrivalTime());
        int n = tareas.size();
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        List<Tarea> tareaRestantes = new ArrayList<>(tareas);

        while (!tareaRestantes.isEmpty()) {
            Tarea tareaElegida = null;
            int shortestTime = Integer.MAX_VALUE;

            for (Tarea tarea : tareaRestantes) {
                if (tarea.getArrivalTime() <= currentTime && tarea.getCputime() < shortestTime) {
                    tareaElegida = tarea;
                    shortestTime = tarea.getCputime();
                }
            }

            if (tareaElegida == null) {
                currentTime++;
            } else {
                int completionTime = currentTime + tareaElegida.getCputime();
                int turnaroundTime = completionTime - tareaElegida.getArrivalTime();
                int waitingTime = turnaroundTime - tareaElegida.getCputime();

                System.out.println("Tarea " + tareaElegida.getId() + ": Tiempo de finalización = " + completionTime
                        + ", Latencia = " + turnaroundTime + ", Tiempo de espera = " + waitingTime);

                totalTurnaroundTime += turnaroundTime;
                totalWaitingTime += waitingTime;
                currentTime = completionTime;

                tareaRestantes.remove(tareaElegida);
            }
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / n;
        double averageWaitingTime = (double) totalWaitingTime / n;

        System.out.println("Tiempo de finalización promedio: " + averageTurnaroundTime);
        System.out.println("Tiempo de espera promedio: " + averageWaitingTime);
    }

    private static void srt(List<Tarea> tareas) {
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;
        int n = tareas.size();

        List<Tarea> tareaRestantes = new ArrayList<>(tareas);

        while (!tareaRestantes.isEmpty()) {
            Tarea tareaElegida = null;
            int shortestTime = Integer.MAX_VALUE;

            for (Tarea tarea : tareaRestantes) {
                if (tarea.getArrivalTime() <= currentTime && tarea.getRemainingTime() < shortestTime) {
                    tareaElegida = tarea;
                    shortestTime = tarea.getRemainingTime();
                }
            }

            if (tareaElegida == null) {
                currentTime++;
            } else {
                tareaElegida.setRemainingTime(tareaElegida.getRemainingTime() - 1);
                currentTime++;

                if (tareaElegida.getRemainingTime() == 0) {
                    int completionTime = currentTime;
                    int turnaroundTime = completionTime - tareaElegida.getArrivalTime();
                    int waitingTime = turnaroundTime - tareaElegida.getCputime();

                    System.out.println("Tarea " + tareaElegida.getId());
                    System.out.println("Tarea " + tareaElegida.getId() + ": Tiempo de finalización = " + completionTime
                            + ", Latencia = " + turnaroundTime + ", Tiempo de espera = " + waitingTime);

                    totalTurnaroundTime += turnaroundTime;
                    totalWaitingTime += waitingTime;
                    tareaRestantes.remove(tareaElegida);
                }
            }
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / n;
        double averageWaitingTime = (double) totalWaitingTime / n;

        System.out.println("Tiempo de finalización promedio: " + averageTurnaroundTime);
        System.out.println("Tiempo de espera promedio: " + averageWaitingTime);
    }

    private static void roundRobin(List<Tarea> tareas, int quantum) {
        int n = tareas.size();
        int currentTime = 0;
        int totalTurnaroundTime = 0;
        int totalWaitingTime = 0;

        List<Tarea> tareaRestantes = new ArrayList<>(tareas);

        while (!tareaRestantes.isEmpty()) {
            for (Tarea tarea : tareaRestantes) {
                if (tarea.getArrivalTime() <= currentTime) {
                    int remainingTime = tarea.getRemainingTime();
                    if (remainingTime <= quantum) {
                        currentTime += remainingTime;
                        tarea.setRemainingTime(0);
                    } else {
                        currentTime += quantum;
                        tarea.setRemainingTime(remainingTime - quantum);
                    }

                    if (tarea.getRemainingTime() == 0) {
                        int completionTime = currentTime;
                        int turnaroundTime = completionTime - tarea.getArrivalTime();
                        int waitingTime = turnaroundTime - tarea.getCputime();

                        System.out.println("Tarea " + tarea.getId() + ": Tiempo de finalización = " + completionTime
                                + ", Latencia = " + turnaroundTime + ", Tiempo de espera = " + waitingTime);

                        totalTurnaroundTime += turnaroundTime;
                        totalWaitingTime += waitingTime;

                        tareaRestantes.remove(tarea);
                    }
                }
            }
        }

        double averageTurnaroundTime = (double) totalTurnaroundTime / n;
        double averageWaitingTime = (double) totalWaitingTime / n;

        System.out.println("Tiempo de finalización promedio: " + averageTurnaroundTime);
        System.out.println("Tiempo de espera promedio: " + averageWaitingTime);
    }
}

