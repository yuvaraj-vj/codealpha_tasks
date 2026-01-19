import java.util.*;
import java.io.*;

class Room {
    int roomNumber;
    String category;
    boolean isAvailable;

    Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isAvailable = true;
    }
}

class Reservation {
    String customerName;
    int roomNumber;
    String category;

    Reservation(String customerName, int roomNumber, String category) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
    }
}

public class HotelReservationSystem {

    static ArrayList<Room> rooms = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "bookings.txt";

    public static void main(String[] args) {
        initializeRooms();
        loadBookings();

        while (true) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> viewRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewBookings();
                case 5 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    static void viewRooms() {
        System.out.println("\nAvailable Rooms:");
        for (Room r : rooms) {
            if (r.isAvailable) {
                System.out.println("Room " + r.roomNumber + " - " + r.category);
            }
        }
    }

    static void bookRoom() {
        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        viewRooms();
        System.out.print("Enter room number to book: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo && r.isAvailable) {
                r.isAvailable = false;
                simulatePayment();
                saveBooking(new Reservation(name, roomNo, r.category));
                System.out.println("Room booked successfully!");
                return;
            }
        }
        System.out.println("Room not available!");
    }

    static void simulatePayment() {
        System.out.println("Processing payment...");
        try { Thread.sleep(1000); } catch (Exception ignored) {}
        System.out.println("Payment successful!");
    }

    static void cancelBooking() {
        System.out.print("Enter room number to cancel: ");
        int roomNo = sc.nextInt();
        sc.nextLine();

        for (Room r : rooms) {
            if (r.roomNumber == roomNo) {
                r.isAvailable = true;
            }
        }

        removeBooking(roomNo);
        System.out.println("Reservation cancelled.");
    }

    static void saveBooking(Reservation res) {
        try (FileWriter fw = new FileWriter(FILE_NAME, true)) {
            fw.write(res.customerName + "," + res.roomNumber + "," + res.category + "\n");
        } catch (IOException e) {
            System.out.println("Error saving booking.");
        }
    }

    static void loadBookings() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int roomNo = Integer.parseInt(data[1]);
                for (Room r : rooms) {
                    if (r.roomNumber == roomNo) {
                        r.isAvailable = false;
                    }
                }
            }
        } catch (IOException ignored) {}
    }

    static void removeBooking(int roomNo) {
        File input = new File(FILE_NAME);
        File temp = new File("temp.txt");

        try (
            BufferedReader br = new BufferedReader(new FileReader(input));
            FileWriter fw = new FileWriter(temp)
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains("," + roomNo + ",")) {
                    fw.write(line + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating file.");
        }

        input.delete();
        temp.renameTo(input);
    }

    static void viewBookings() {
        System.out.println("\nBooking Details:");
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] d = line.split(",");
                System.out.println("Name: " + d[0] +
                        ", Room: " + d[1] +
                        ", Category: " + d[2]);
            }
        } catch (IOException e) {
            System.out.println("No bookings found.");
        }
    }
}
