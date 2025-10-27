void main() throws FileNotFoundException {
    List<Person> people = new ArrayList<>();
    
    people.add(new Person("Alexandru-Mihail Ionita", LocalDate.of(2002, 1, 10), "Male", 195));
    people.add(new Person("Alexandru-Mihail Ionita", LocalDate.of(2002, 1, 11), "Female", 180));
    people.add(new Person("Alexandru-Mihail Ionita", LocalDate.of(2002, 1, 11), "Female", 195));
    people.add(new Person("Alexandru-Mihail Ionita", LocalDate.of(2002, 1, 11), "Male", 195));
    people.add(new Person("Diana-Alexia Bordea", LocalDate.of(2004, 2, 20), "Female", 171));
    people.add(new Person("Tiberiu-Adrian Petre",  LocalDate.of(2002, 9, 21), "Male", 178));
    people.add(new Person("Mircea-Dan Deaconu",  LocalDate.of(2003, 02, 20), "Male", 150));
    
    people = people.stream().sorted().toList();

    for(Person person : people)
        System.out.println(person.toString() + "\n");

    people.get(0).store("people.txt");
}