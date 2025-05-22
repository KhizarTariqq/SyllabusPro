import spacy
from processPDF import process_pdf
nlp = spacy.load("output/model-best")

def extract_items(file):
    text = process_pdf(file)

    doc = nlp(text)

    entities = []
    for ent in doc.ents:
        # Add the entity to the list
        # print(ent.text, ent.label_)
        entities.append(ent.text)

    return entities
