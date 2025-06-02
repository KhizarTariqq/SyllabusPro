from dataclasses import dataclass
import datetime
from enum import Enum
from typing import Optional

@dataclass
class SyllabusItem:
    class ItemType(Enum):
        ASSIGNMENT = 1
        LAB = 2
        PARTICIPATION = 3
        PRE_CLASS_ACTIVITY = 4
        OTHER = 5
        QUIZ = 6
        MIDTERM = 7
        EXAM = 8
        UNKNOWN = 9

        @classmethod
        def from_string(cls, s: str):
            s = s.strip().lower()

            if s == "assignment":
                return cls.ASSIGNMENT
            
            elif s in {"lab", "labs"}:
                return cls.LAB
            
            elif s in {"class participation", "in-class participation", "lecture participation", "participation"}:
                return cls.PARTICIPATION
            
            elif s in {"pre-class", "pre-class quiz"}:
                return cls.PRE_CLASS_ACTIVITY
            
            elif s in {"other", "misc", "miscellaneous", "floating"}:
                return cls.OTHER
            
            elif s == "quiz":
                return cls.QUIZ
            
            elif s in {"term test", "test", "midterm", "midterm test", "midterm exam"}:
                return cls.MIDTERM

            elif s in {"exam", "final exam", "final", "examination"}:
                return cls.EXAM
            
            else:
                return cls.UNKNOWN
        
    type: Optional[str] # Temporarily keep type as a string for testing Optional[ItemType]
    description: Optional[str]
    weight: Optional[float]
    due_date: Optional[datetime.date]

    def __str__(self):
        return f"SyllabusItem: (type = {self.type}, description = {self.description}, weight = {self.weight}, due_date = {self.due_date})"
    
    def to_dict(self):
        return {
            "type": self.type.name if self.type else None,
            "description": self.description,
            "weight": self.weight,
            "due_date": self.due_date.isoformat() if self.due_date else None
        }
