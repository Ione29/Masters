from datetime import date
from Person import Person

def main():
    people = [
        Person("Alexandru-Mihail Ionita", date(2002, 1, 10), "Male", 195),
        Person("Diana-Alexia Bordea", date(2004, 3, 28), "Female", 171),
        Person("Tiberiu Petre Adrian", date(2002, 2, 1), "Male", 184),
        Person("Mircea-Dan Deaconu", date(2002, 2, 20), "Male", 150)
    ]

    people = sorted(people)

    for person in people:
        print(person)
    
    people[0].store("people.txt")

if __name__ == "__main__":
    main()
