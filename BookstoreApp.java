import java.util.Scanner;

// Custom ArrayList implementation
class MyArrayList<T> {
    private Object[] array;
    private int size;

    public MyArrayList() {
        // Start with capacity of 10
        array = new Object[10];
        size = 0;
    }

    // Add an element to the end
    public void add(T element) {
        // Check if we need to resize
        if (size == array.length) {
            resize();
        }

        array[size] = element;
        size++;
    }

    // Get element at index
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) array[index];
    }

    // Set element at index
    public void set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        array[index] = element;
    }

    // Get size
    public int size() {
        return size;
    }

    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Resize the array
    private void resize() {
        Object[] newArray = new Object[array.length * 2];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
}

// Custom Queue implementation
class MyQueue<T> {
    private Node<T> front;
    private Node<T> rear;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public MyQueue() {
        front = null;
        rear = null;
    }

    // Add to the end of the queue
    public void enqueue(T item) {
        Node<T> newNode = new Node<>(item);

        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

    // Remove from the front of the queue
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Queue is empty");
        }

        T item = front.data;
        front = front.next;

        if (front == null) {
            rear = null;
        }

        return item;
    }

    // Check if queue is empty
    public boolean isEmpty() {
        return front == null;
    }
}

// Custom Stack implementation
class MyStack<T> {
    private Node<T> top;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public MyStack() {
        top = null;
    }

    // Push item onto stack
    public void push(T item) {
        Node<T> newNode = new Node<>(item);
        newNode.next = top;
        top = newNode;
    }

    // Pop item from stack
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }

        T item = top.data;
        top = top.next;
        return item;
    }

    // Check if stack is empty
    public boolean isEmpty() {
        return top == null;
    }
}

// Book class
class Book {
    private final int id;
    private final String title;
    private final String author;
    private final double price;

    public Book(int id, String title, String author, double price) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Title: " + title + " | Author: " + author + " | Price: $" + price;
    }
}

// Order class
class Order {
    private final int id;
    private final String customerName;
    private final String address;
    private final MyArrayList<OrderItem> items;
    private String status;

    public Order(int id, String customerName, String address) {
        this.id = id;
        this.customerName = customerName;
        this.address = address;
        this.items = new MyArrayList<>();
        this.status = "Pending";
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MyArrayList<OrderItem> getItems() {
        return items;
    }

    public void addBook(Book book, int quantity) {
        items.add(new OrderItem(book, quantity));
    }

    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            total += item.getBook().getPrice() * item.getQuantity();
        }
        return total;
    }
}

// OrderItem class
class OrderItem {
    private final Book book;
    private final int quantity;

    public OrderItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }
}


// BookstoreSystem to handle the menu and operations
class BookstoreSystem {
    private final Scanner input = new Scanner(System.in);
    private final MyArrayList<Book> bookList = new MyArrayList<>();
    private final MyQueue<Order> orderQueue = new MyQueue<>();
    private final MyStack<Order> processedOrders = new MyStack<>();

    // Constructor with some sample data
    public BookstoreSystem() {
        // Add some initial books
        bookList.add(new Book(101, "Java Programming", "John Smith", 29.99));
        bookList.add(new Book(102, "Data Structures", "Jane Doe", 34.99));
        bookList.add(new Book(103, "Algorithms", "Alan Turing", 24.99));
        bookList.add(new Book(104, "Database Design", "Oracle Team", 39.99));
        bookList.add(new Book(105, "Web Development", "Tim Lee", 27.99));
    }

    // Main menu method
    public void runMenu() {
        int choice = 0;

        do {
            System.out.println("\n===== ONLINE BOOKSTORE SYSTEM =====");
            System.out.println("1. Display all books");
            System.out.println("2. Add a new book");
            System.out.println("3. Search for a book");
            System.out.println("4. Sort books");
            System.out.println("5. Place an order");
            System.out.println("6. Process next order");
            System.out.println("7. Display order queue");
            System.out.println("8. Display processed orders");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
                continue;
            }

            switch (choice) {
                case 1:
                    displayBooks();
                    break;
                case 2:
                    addNewBook();
                    break;
                case 3:
                    searchBook();
                    break;
                case 4:
                    sortBooks();
                    break;
                case 5:
                    placeOrder();
                    break;
                case 6:
                    processNextOrder();
                    break;
                case 7:
                    displayOrderQueue();
                    break;
                case 8:
                    displayProcessedOrders();
                    break;
                case 9:
                    System.out.println("Thank you for using the Bookstore System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 9);
    }

    // Display all books
    private void displayBooks() {
        System.out.println("\n===== BOOK LIST =====");
        if (bookList.isEmpty()) {
            System.out.println("No books available.");
            return;
        }

        for (int i = 0; i < bookList.size(); i++) {
            System.out.println(bookList.get(i));
        }
    }

    // Add a new book
    private void addNewBook() {
        System.out.println("\n===== ADD NEW BOOK =====");

        System.out.print("Enter book ID: ");
        int id = 0;
        try {
            id = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Operation cancelled.");
            return;
        }

        // Check if ID already exists
        for (int i = 0; i < bookList.size(); i++) {
            if (bookList.get(i).getId() == id) {
                System.out.println("A book with this ID already exists!");
                return;
            }
        }

        System.out.print("Enter title: ");
        String title = input.nextLine();

        System.out.print("Enter author: ");
        String author = input.nextLine();

        System.out.print("Enter price: $");
        double price = 0;
        try {
            price = Double.parseDouble(input.nextLine());
            if (price <= 0) {
                System.out.println("Price must be positive. Operation cancelled.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Operation cancelled.");
            return;
        }

        Book newBook = new Book(id, title, author, price);
        bookList.add(newBook);
        System.out.println("Book added successfully!");
    }

    // Search for a book
    private void searchBook() {
        System.out.println("\n===== SEARCH BOOK =====");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Title");
        System.out.print("Enter your choice: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Search cancelled.");
            return;
        }

        if (choice == 1) {
            System.out.print("Enter book ID: ");
            try {
                int id = Integer.parseInt(input.nextLine());
                boolean found = false;

                for (int i = 0; i < bookList.size(); i++) {
                    if (bookList.get(i).getId() == id) {
                        System.out.println("Book found: " + bookList.get(i));
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("No book found with ID: " + id);
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
            }
        } else if (choice == 2) {
            System.out.print("Enter title (or part of title): ");
            String title = input.nextLine().toLowerCase();
            boolean found = false;

            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getTitle().toLowerCase().contains(title)) {
                    System.out.println("Book found: " + bookList.get(i));
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No books found with title containing: " + title);
            }
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // Sort books
    private void sortBooks() {
        System.out.println("\n===== SORT BOOKS =====");
        System.out.println("1. Sort by ID");
        System.out.println("2. Sort by Title");
        System.out.println("3. Sort by Price");
        System.out.print("Enter your choice: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid choice. Sort cancelled.");
            return;
        }

        if (choice == 1) {
            // Sort by ID
            for (int i = 0; i < bookList.size() - 1; i++) {
                for (int j = 0; j < bookList.size() - i - 1; j++) {
                    if (bookList.get(j).getId() > bookList.get(j + 1).getId()) {
                        // Swap
                        Book temp = bookList.get(j);
                        bookList.set(j, bookList.get(j + 1));
                        bookList.set(j + 1, temp);
                    }
                }
            }
            System.out.println("Books sorted by ID.");
        } else if (choice == 2) {
            // Sort by Title
            for (int i = 0; i < bookList.size() - 1; i++) {
                for (int j = 0; j < bookList.size() - i - 1; j++) {
                    if (bookList.get(j).getTitle().compareTo(bookList.get(j + 1).getTitle()) > 0) {
                        // Swap
                        Book temp = bookList.get(j);
                        bookList.set(j, bookList.get(j + 1));
                        bookList.set(j + 1, temp);
                    }
                }
            }
            System.out.println("Books sorted by Title.");
        } else if (choice == 3) {
            // Sort by Price
            for (int i = 0; i < bookList.size() - 1; i++) {
                for (int j = 0; j < bookList.size() - i - 1; j++) {
                    if (bookList.get(j).getPrice() > bookList.get(j + 1).getPrice()) {
                        // Swap
                        Book temp = bookList.get(j);
                        bookList.set(j, bookList.get(j + 1));
                        bookList.set(j + 1, temp);
                    }
                }
            }
            System.out.println("Books sorted by Price.");
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        displayBooks();
    }

    // Place an order
    private void placeOrder() {
        System.out.println("\n===== PLACE ORDER =====");

        if (bookList.isEmpty()) {
            System.out.println("No books available to order.");
            return;
        }

        System.out.print("Enter customer name: ");
        String customerName = input.nextLine();

        System.out.print("Enter shipping address: ");
        String address = input.nextLine();

        // Create a new order
        int orderId = (int)(Math.random() * 1000) + 1000; // Simple ID generation
        Order order = new Order(orderId, customerName, address);

        boolean addingBooks = true;
        while (addingBooks) {
            displayBooks();

            System.out.print("\nEnter book ID to add to order (0 to finish): ");
            int bookId = 0;
            try {
                bookId = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid ID format.");
                continue;
            }

            if (bookId == 0) {
                addingBooks = false;
                continue;
            }

            // Find the book
            Book selectedBook = null;
            for (int i = 0; i < bookList.size(); i++) {
                if (bookList.get(i).getId() == bookId) {
                    selectedBook = bookList.get(i);
                    break;
                }
            }

            if (selectedBook == null) {
                System.out.println("Book not found!");
                continue;
            }

            System.out.print("Enter quantity: ");
            int quantity = 0;
            try {
                quantity = Integer.parseInt(input.nextLine());
                if (quantity <= 0) {
                    System.out.println("Quantity must be positive.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity format.");
                continue;
            }

            order.addBook(selectedBook, quantity);
            System.out.println(selectedBook.getTitle() + " added to order.");
        }

        if (order.getItems().isEmpty()) {
            System.out.println("Order cancelled - no books selected.");
            return;
        }

        // Add order to queue
        orderQueue.enqueue(order);
        System.out.println("Order placed successfully!");
        System.out.println("Order ID: " + order.getId());
        System.out.println("Items:");

        for (int i = 0; i < order.getItems().size(); i++) {
            OrderItem item = order.getItems().get(i);
            System.out.println("- " + item.getBook().getTitle() +
                    " x" + item.getQuantity() +
                    " ($" + item.getBook().getPrice() * item.getQuantity() + ")");
        }
        System.out.println("Total: $" + order.calculateTotal());
    }

    // Process next order in queue
    private void processNextOrder() {
        System.out.println("\n===== PROCESS NEXT ORDER =====");

        if (orderQueue.isEmpty()) {
            System.out.println("No orders to process.");
            return;
        }

        Order order = orderQueue.dequeue();
        order.setStatus("Processed");

        System.out.println("Processing Order ID: " + order.getId());
        System.out.println("Customer: " + order.getCustomerName());
        System.out.println("Address: " + order.getAddress());
        System.out.println("Items:");

        for (int i = 0; i < order.getItems().size(); i++) {
            OrderItem item = order.getItems().get(i);
            System.out.println("- " + item.getBook().getTitle() +
                    " x" + item.getQuantity() +
                    " ($" + item.getBook().getPrice() * item.getQuantity() + ")");
        }

        System.out.println("Total: $" + order.calculateTotal());
        System.out.println("Order processed successfully!");

        // Add to processed orders stack
        processedOrders.push(order);
    }

    // Display order queue
    private void displayOrderQueue() {
        System.out.println("\n===== ORDER QUEUE =====");

        if (orderQueue.isEmpty()) {
            System.out.println("No orders in queue.");
            return;
        }

        // Create a temporary queue to hold orders while we display them
        MyQueue<Order> tempQueue = new MyQueue<>();
        int count = 1;

        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.dequeue();
            System.out.println("\nOrder #" + count + ":");
            System.out.println("ID: " + order.getId());
            System.out.println("Customer: " + order.getCustomerName());
            System.out.println("Items:");

            for (int i = 0; i < order.getItems().size(); i++) {
                OrderItem item = order.getItems().get(i);
                System.out.println("- " + item.getBook().getTitle() +
                        " x" + item.getQuantity() +
                        " ($" + item.getBook().getPrice() * item.getQuantity() + ")");
            }
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total: $" + order.calculateTotal());

            tempQueue.enqueue(order);
            count++;
        }

        // Restore the original queue
        while (!tempQueue.isEmpty()) {
            orderQueue.enqueue(tempQueue.dequeue());
        }
    }

    // Display processed orders
    private void displayProcessedOrders() {
        System.out.println("\n===== PROCESSED ORDERS =====");

        if (processedOrders.isEmpty()) {
            System.out.println("No processed orders.");
            return;
        }

        // Create a temporary stack to hold orders while we display them
        MyStack<Order> tempStack = new MyStack<>();
        int count = 1;

        while (!processedOrders.isEmpty()) {
            Order order = processedOrders.pop();
            System.out.println("\nOrder #" + count + ":");
            System.out.println("ID: " + order.getId());
            System.out.println("Customer: " + order.getCustomerName());
            System.out.println("Items:");

            for (int i = 0; i < order.getItems().size(); i++) {
                OrderItem item = order.getItems().get(i);
                System.out.println("- " + item.getBook().getTitle() +
                        " x" + item.getQuantity() +
                        " ($" + item.getBook().getPrice() * item.getQuantity() + ")");
            }
            System.out.println("Status: " + order.getStatus());
            System.out.println("Total: $" + order.calculateTotal());

            tempStack.push(order);
            count++;
        }

        // Restore the original stack
        while (!tempStack.isEmpty()) {
            processedOrders.push(tempStack.pop());
        }
    }
}


// Main Application Class
public class BookstoreApp {
    public static void main(String[] args) {
        BookstoreSystem system = new BookstoreSystem();
        system.runMenu();
    }
}