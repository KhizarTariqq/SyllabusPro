import os
import spacy

from .pdfParsing.processPDF import process_pdf

model_path = os.path.join(os.path.dirname(__file__), "model", "model-best")
nlp = spacy.load(model_path)

def extract_items(file):
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
