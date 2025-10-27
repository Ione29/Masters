from abc import ABC, abstractmethod

class Storable(ABC):
    @abstractmethod
    def store(self, file: str):
        pass