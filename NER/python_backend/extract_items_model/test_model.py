import spacy
import os

nlp = spacy.load(os.path.join(os.path.dirname(__file__), "model", "model-best"))

# Create doc object
file = os.path.join(os.path.dirname(__file__), "data", "testing_txts", "20229_CSC398H5F_LEC0101.txt")
with open(file, "r", encoding="utf-8") as f:
    CSC420 = f.read()

doc = nlp(CSC420)

for ent in doc.ents:
    # Print the entity text and its label
    print(ent.text, ent.label_)