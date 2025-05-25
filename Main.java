import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        EmergencySystem system = new EmergencySystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Emergency System ---");
            System.out.println("1 - Add Patient");
            System.out.println("2 - View Patients");
            System.out.println("3 - Update Patient");
            System.out.println("4 - Delete Patient");
            System.out.println("5 - Undo Delete");
            System.out.println("6 - Process Critical Patient");
            System.out.println("7 - View Patients by Condition");
            System.out.println("0 - Exit");
            System.out.print("Choice: ");

            int op;
            try {
                op = Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid input.");
                continue;
            }

            switch (op) {
                case 1 -> {
                    System.out.print("ID: ");
                    String id = sc.nextLine();
                    System.out.print("Name: ");
                    String name = sc.nextLine();
                    System.out.print("Age: ");
                    int age = Integer.parseInt(sc.nextLine());
                    System.out.print("Condition: ");
                    String cond = sc.nextLine();
                    System.out.print("Priority (lower number is more critical): ");
                    int pri = Integer.parseInt(sc.nextLine());
                    system.addPatient(new EmergencySystem.Patient(id, name, age, cond, pri));
                }
                case 2 -> system.viewPatients();
                case 3 -> {
                    System.out.print("ID to update: ");
                    String id = sc.nextLine();
                    System.out.print("New Name: ");
                    String name = sc.nextLine();
                    System.out.print("New Age: ");
                    int age = Integer.parseInt(sc.nextLine());
                    System.out.print("New Condition: ");
                    String cond = sc.nextLine();
                    System.out.print("New Priority: ");
                    int pri = Integer.parseInt(sc.nextLine());
                    system.updatePatient(id, name, age, cond, pri);
                }
                case 4 -> {
                    System.out.print("ID to delete: ");
                    String id = sc.nextLine();
                    system.deletePatient(id);
                }
                case 5 -> system.undoDelete();
                case 6 -> system.processCriticalPatient();
                case 7 -> system.viewPatientsByCondition();
                case 0 -> {
                    System.out.println("System terminated.");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}