import spacy
from pdfParsing.processPDF import process_pdf
nlp = spacy.load("output/model-best")

def extract_items(file):
    '''
    Extract all syllabus items from the pdf, add them to a list and return it.
    '''
    text = process_pdf(file)

    doc = nlp(text)

    entities = []
    for ent in doc.ents:
        # Add the entity to the list
        entities.append(ent.text)

    return entities
