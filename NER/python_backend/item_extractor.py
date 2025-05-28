import os
import spacy
from .pdf_parsing.process_PDF import process_pdf

model_path = os.path.join(os.path.dirname(__file__), "extract_items_model", "model", "model-best")
nlp = spacy.load(model_path)

def extract_items_from_file(file):
    """
    Extract all syllabus items from the pdf, add them to a list and return it.
    """
    text = process_pdf(file)

    doc = nlp(text)

    entities = []
    for ent in doc.ents:
        # Add the entity to the list
        entities.append(ent.text)

    return entities

def extract_items_from_text(text):
    """
    Extract all syllabus items from the text, add them to a list and return it.
    """
    doc = nlp(text)

    entities = []
    for ent in doc.ents:
        # Add the entity to the list
        entities.append(ent.text)

    return entities
