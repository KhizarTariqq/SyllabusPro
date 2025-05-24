from dataclasses import dataclass
import datetime
from enum import Enum

class ItemType(Enum):
    ASSIGNMENT = 1
    LAB = 2
    PARTICIPATION = 3
    QUIZ = 4
    MIDTERM = 5
    EXAM = 6

@dataclass
class SyllabusItem:
    type: ItemType
    description: str
    weight: float
    due_date: datetime
