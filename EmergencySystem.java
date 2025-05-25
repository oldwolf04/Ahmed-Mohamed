import java.io.Serializable;
import java.util.*;

public class EmergencySystem implements Serializable {

    //الكلاس الخاص بيانات المريض    
    public static class Patient implements Serializable {
         String id;
         String name;
         int age;
         int priority; // lower number means more critical
         String condition;

        
    //الكونستراكتور المسئول عن تسجيل بيانات المريض
        public Patient(String id, String name, int age, String condition, int priority) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.condition = condition;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "ID: " + id + ", Name: " + name + ", Age: " + age + ", Condition: " + condition + ", Priority: " + priority;
        }
    }

    private LinkedList<Patient> patientList = new LinkedList<>();
    private Stack<Patient> undoStack = new Stack<>();
    private PriorityQueue<Patient> criticalQueue = new PriorityQueue<>(Comparator.comparingInt(p -> p.priority));
    private TreeMap<String, LinkedList<Patient>> conditionMap = new TreeMap<>();

    //الميثود المسؤلة عن اضافة المريض لقائمة المرضى
    public void addPatient(Patient p) {
        patientList.add(p);
        criticalQueue.add(p);
        undoStack.clear();
        conditionMap.computeIfAbsent(p.condition, k -> new LinkedList<>()).add(p);
        System.out.println("Patient added.");
    }

    //الميثود المسؤلة عن عرض المرضى الموجودة داخل النظام
    public void viewPatients() {
        if (patientList.isEmpty()) {
            System.out.println("No patients currently.");
            return;
                                    }
        System.out.println("\nPatients List:");
        for (Patient p : patientList) {
            System.out.println(p);
        }
        }

    
    //الميثود المسؤلة عن تحديث بيانات المريض بعد ما بنبحث عنه
    public void updatePatient(String id, String newName, int newAge, String newCondition, int newPriority) {
        Patient p = findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        LinkedList<Patient> oldCondList = conditionMap.get(p.condition);
        if (oldCondList != null) {
            oldCondList.remove(p);
            if (oldCondList.isEmpty()) conditionMap.remove(p.condition);
        }
        criticalQueue.remove(p);

        p.name = newName;
        p.age = newAge;
        p.condition = newCondition;
        p.priority = newPriority;

        criticalQueue.add(p);
        conditionMap.computeIfAbsent(p.condition, k -> new LinkedList<>()).add(p);

        System.out.println("Patient updated.");
    }

    //الميثود المسؤلة عن حذف المريض من النظام بس الاول ندخلة الاستاك عشان حبينا نرجعه
    public void deletePatient(String id) {
        Patient p = findPatientById(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
                       }

        patientList.remove(p);
        criticalQueue.remove(p);
        undoStack.push(p);

        LinkedList<Patient> condList = conditionMap.get(p.condition);
        if (condList != null) {
            condList.remove(p);
            if (condList.isEmpty()) conditionMap.remove(p.condition);
        }

        System.out.println("Patient deleted.");
    }

    //بنرجع اخر مريض اتحذف من السيستم عن طريق الاستاك
    public void undoDelete() {
        if (undoStack.isEmpty()) {
            System.out.println("No delete operations to undo.");
            return;
        }
        Patient p = undoStack.pop();
        patientList.add(p);
        criticalQueue.add(p);
        conditionMap.computeIfAbsent(p.condition, k -> new LinkedList<>()).add(p);
        System.out.println("Undo successful. Patient restored.");
    }


    //الميثود المسؤلة  عن علاج المريض اللى حالته حرجة اكتر
    public void processCriticalPatient() {
        if (criticalQueue.isEmpty()) {
         System.out.println("No critical patients.");  
        return;
        }
        Patient p = criticalQueue.poll();
        patientList.remove(p);

        LinkedList<Patient> condList = conditionMap.get(p.condition);
        if (condList != null) {
            condList.remove(p);
            if (condList.isEmpty()) conditionMap.remove(p.condition);
        }

        System.out.println("Processing critical patient: " + p);
    }

    
    //دالة مساعده بدور على المريض داخل النظام
     private Patient findPatientById(String id) {
        for (Patient p : patientList) {
            if (p.id.equals(id)) return p;
        }
        return null;
        }

    //الدالة المسؤلة عن عرض الحالة الطبية للمرضى
    public void viewPatientsByCondition() {
        if (conditionMap.isEmpty()) {
            System.out.println("No patients grouped by condition.");
            return;
        }
        System.out.println("\nPatients grouped by condition:");
        for (Map.Entry<String, LinkedList<Patient>> entry : conditionMap.entrySet()) {
            System.out.println("Condition: " + entry.getKey());
            for (Patient p : entry.getValue()) {
                System.out.println("  " + p);
            }
         }
         }
         }
