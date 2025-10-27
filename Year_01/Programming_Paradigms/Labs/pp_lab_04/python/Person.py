from Storable import Storable
from functools import total_ordering

@total_ordering
class Person(Storable):
    def __init__(self, name, birthday, gender, height):
        self.name = name
        self.birthday = birthday
        self.gender = gender
        self.height = height

    def __eq__(self, other):
        if not isinstance(other, Person):
            return NotImplemented
        return (self.name == other.name and
                self.birthday == other.birthday and
                self.gender == other.gender and
                self.height == other.height)

    def __lt__(self, other):
        if not isinstance(other, Person):
            return NotImplemented
        if self.name != other.name:
            return self.name < other.name
        if self.birthday != other.birthday:
            return self.birthday < other.birthday
        if self.gender != other.gender:
            return self.gender < other.gender
        return self.height < other.height
    
    def __str__(self):
        return f"Name: {self.name}, Birthday: {self.birthday}, Gender: {self.gender}, Height: {self.height}"

    def store(self, file: str):
        with open(file, 'w') as file:
            file.write(str(self))
